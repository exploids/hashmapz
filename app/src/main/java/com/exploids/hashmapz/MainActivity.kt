package com.exploids.hashmapz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.exploids.hashmapz.controller.CommandController
import com.exploids.hashmapz.model.CurrentStateViewModel
import com.exploids.hashmapz.model.CurrentState
import com.exploids.hashmapz.ui.theme.HashmapzTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*


@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val viewModel : CurrentStateViewModel = CurrentStateViewModel()
            val commandController = viewModel.getCommandController()
            HashmapzTheme {
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        Home(navController, commandController, viewModel)
                    }
                    composable("tutorial") {
                        Tutorial(navController)
                    }
                    composable("wiki") {
                        Wiki(navController)
                    }
                    composable("about") {
                        About(navController)
                    }
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavController, commandController: CommandController, currentStateViewModel:  CurrentStateViewModel = viewModel()) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val bringIntoViewRequester = remember {
        BringIntoViewRequester()
    }
    val (selectedBottomSheet, setSelectedBottomSheet) = remember {
        mutableStateOf(0)
    }
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            when (selectedBottomSheet) {
                0 -> InsertBottomSheet(currentStateViewModel, scope, sheetState)
                1 -> LookupBottomSheet(currentStateViewModel, scope, sheetState)
                2 -> RemoveBottomSheet(currentStateViewModel, scope, sheetState)
                3 -> RenewBottomSheet(currentStateViewModel, scope, sheetState)
            }
        }
    ) {
        Scaffold(
            bottomBar = {
                HomeBottomBar(navController, scope, sheetState, setSelectedBottomSheet)
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp, 16.dp, 16.dp, 0.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Card {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Box(modifier = Modifier.animateContentSize()) {
                            Crossfade(targetState = currentStateViewModel.currentDescription) {
                                Text(stringResource(id = it))
                            }
                        }
                        Row(
                            modifier = Modifier.align(Alignment.End),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = {
                                    commandController.prevCommand()
                                    currentStateViewModel.update()
                                          },
                                enabled = !currentStateViewModel.isPrevDisabled

                            ) {
                                Text(text = "Back")
                            }
                            Button(
                                onClick = {
                                    commandController.nextCommand()
                                    currentStateViewModel.update()
                                          },
                                enabled = !currentStateViewModel.isNextDisabled
                                ) {
                                Text(text = "Next")
                            }
                        }
                    }
                }
                val listState = rememberLazyListState()
                LazyColumn(
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(0.dp, 0.dp, 0.dp, 16.dp)
                ) {
                    if (currentStateViewModel.currentIndex != null){
                        scope.launch { listState.scrollToItem(currentStateViewModel.currentIndex!!, 0) }
                    }
                    items(currentStateViewModel.mapSize) { index ->
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Row(
                                modifier = Modifier
                                    .height(80.dp)
                                    .width(40.dp),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                val color = if (currentStateViewModel.currentIndex != null && currentStateViewModel.currentIndex == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                Text(
                                    text = index.toString(),
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = color
                                )
                            }
                            if (currentStateViewModel.listKey[index] == null) {
                                Card {
                                    Box(
                                        Modifier
                                            .height(80.dp)
                                            .fillMaxWidth()

                                    )
                                }
                            } else {
                                HashEntry(index = index, currentStateViewModel)
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeBottomBar(
    navController: NavController,
    scope: CoroutineScope,
    sheetState: ModalBottomSheetState,
    setSelectedBottomSheet: (Int) -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }
    BottomAppBar(
        icons = {
            IconButton(onClick = { showMenu = !showMenu }) {
                Icon(
                    Icons.Filled.MoreVert,
                    contentDescription = "Localized description",
                )
            }
            IconButton(onClick = {
                setSelectedBottomSheet(2)
                scope.launch {
                    sheetState.show()
                }
            }) {
                Icon(
                    Icons.Filled.Delete,
                    contentDescription = "Localized description",
                )
            }
            IconButton(onClick = {
                setSelectedBottomSheet(1)
                scope.launch {
                    sheetState.show()
                }
            }) {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = "Localized description",
                )
            }
            IconButton(onClick = {
                setSelectedBottomSheet(3)
                scope.launch {
                    sheetState.show()
                }
            }) {
                Icon(
                    Icons.Filled.Refresh,
                    contentDescription = "Localized description",
                )
            }
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {
                DropdownMenuItem(text = { Text("Wiki") }, onClick = {
                    navController.navigate("wiki")
                })
                DropdownMenuItem(text = { Text("Tutorial") }, onClick = {
                    navController.navigate("tutorial")
                })
                DropdownMenuItem(text = { Text("About") }, onClick = {
                    navController.navigate("about")
                })
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    setSelectedBottomSheet(0)
                    scope.launch {
                        sheetState.show()
                    }
                },
                elevation = BottomAppBarDefaults.floatingActionButtonElevation()
            ) {
                Icon(Icons.Filled.Create, "Localized description")
            }
        }
    )
}

@ExperimentalMaterialApi
@Composable
fun BottomSheetContent(
    title: String,
    label: String,
    content: @Composable (() -> Unit),
    currentStateViewModel:  CurrentStateViewModel = viewModel(),
    scope: CoroutineScope,
    sheetState: ModalBottomSheetState,
    onClick: () -> Unit
) {
    Surface {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(text = title, style = MaterialTheme.typography.titleLarge)
            content()
            Button(modifier = Modifier.align(Alignment.End), onClick = onClick) {
                Text(text = label)
            }
        }
    }
}


@ExperimentalMaterialApi
@Composable
fun InsertBottomSheet(currentStateViewModel:  CurrentStateViewModel = viewModel(), scope: CoroutineScope, sheetState: ModalBottomSheetState) {
    var key by remember { mutableStateOf("Banana") }
    var value by remember { mutableStateOf("32 pieces") }
    BottomSheetContent(title = "Insert or update an entry", label = "Set", {


            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = key,
                onValueChange = { newText ->
                    key = newText
                },
                label = { Text(text = "Key") })

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = value,
                onValueChange = { newText ->
                    value = newText
                },
                label = { Text(text = "Value") })

    }, currentStateViewModel, scope, sheetState ){
        currentStateViewModel.getCommandController().add(key, value)
        currentStateViewModel.update()
        scope.launch {
            sheetState.hide()
        }
    }
}


@ExperimentalMaterialApi
@Composable
fun LookupBottomSheet(currentStateViewModel:  CurrentStateViewModel = viewModel(), scope: CoroutineScope, sheetState: ModalBottomSheetState) {
    var key by remember { mutableStateOf("Banana") }
    BottomSheetContent(title = "Lookup an entry", label = "Lookup", {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = key,
            onValueChange = { newText ->
                key = newText
            },
            label = { Text(text = "Key") })
    }, currentStateViewModel, scope, sheetState){
        currentStateViewModel.getCommandController().search(key)
        currentStateViewModel.update()
        scope.launch {
            sheetState.hide()
        }
    }
}


@ExperimentalMaterialApi
@Composable
fun RemoveBottomSheet(currentStateViewModel:  CurrentStateViewModel = viewModel(), scope: CoroutineScope, sheetState: ModalBottomSheetState) {
    var key by remember { mutableStateOf("Banana") }
    BottomSheetContent(title = "Remove an entry", label = "Remove", {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = key,
            onValueChange = { newText ->
                key = newText
            },
            label = { Text(text = "Key") })
    }, currentStateViewModel, scope, sheetState){
        currentStateViewModel.getCommandController().delete(key)
        currentStateViewModel.update()
        scope.launch {
            sheetState.hide()
        }
    }
}


@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@Composable
fun RenewBottomSheet(currentStateViewModel:  CurrentStateViewModel = viewModel(), scope: CoroutineScope, sheetState: ModalBottomSheetState) {
    var selectedProbingMode by remember { mutableStateOf("") }
    var loadfactor by remember { mutableStateOf("0.75") }
    BottomSheetContent(title = "Renew Hashmap", label = "Renew", {
        var expanded by remember { mutableStateOf(false) }
        val availableProbingModes = listOf("Linear Probing", "Quadratic Probing", "Double Hashing")
        var textFieldSize by remember { mutableStateOf(Size.Zero)}
        
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = {expanded = !expanded},
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        textFieldSize = coordinates.size.toSize()
                    },
                value = selectedProbingMode,
                onValueChange = { selectedProbingMode = it},
                label = { Text(text = "Probing Modes") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                readOnly = true
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false}) {
                availableProbingModes.forEach { selectionOption ->
                    DropdownMenuItem(text = { Text(text = selectionOption) },
                        onClick = {
                            selectedProbingMode = selectionOption
                            expanded = false
                        })

                }
            }

        }

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = loadfactor,
            onValueChange = { newText ->
                loadfactor = newText
            },
            label = { Text(text = "Load factor") })
    }, currentStateViewModel, scope, sheetState){
        println(loadfactor.toFloat())
        currentStateViewModel.getCommandController().renewMap(selectedProbingMode, loadfactor.toFloat())
        currentStateViewModel.update()
        scope.launch {
            sheetState.hide()
        }

        }

}


// Function für HashEntry
// Objekt von Entry Klasse wird übergeben
// enthält somit Key und Hashcode

@Composable
fun HashEntry(index: Int, currentStateViewModel: CurrentStateViewModel = viewModel() ) {
    Row(
        modifier = Modifier
            .padding(all = 4.dp)
    ) {
        // Platz zwischen Rand und Textbeginn
        Spacer(modifier = Modifier.width(11.dp))
        // Texte in unterschiedlichen Zeilen
        Column {
            // Text für Key Überschrift
            androidx.compose.material.Text(
                "Key", fontSize = 13.sp, modifier = Modifier.width(1500.dp),
                color = Color(
                    133,
                    133,
                    133,
                    255
                )
            )
            // Add a vertical space between the author and message texts
            Spacer(
                modifier = Modifier.height(1.dp)
            )
            println(index)
            println(Arrays.toString(currentStateViewModel.listKey.toArray()))
            androidx.compose.material.Text(
                text = currentStateViewModel.listKey[index].toString(),
                // Farbe des Textes
                color = Color(0, 0, 0, 255),
                // Typografie des Textes
                // subztitle2 = Größenveränderung
                style = androidx.compose.material.MaterialTheme.typography.subtitle1
            )
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            // Text für HashCode Überschrift
            androidx.compose.material.Text(
                "HashCode", fontSize = 13.sp, modifier = Modifier.width(150.dp),
                color = Color(
                    133,
                    133,
                    133,
                    255
                )
            )
            Spacer(
                modifier = Modifier.height(1.dp)
            )
            // Text für HashCode
            androidx.compose.material.Text(
                text = currentStateViewModel.hashList[index].toString(),
                // Farbe des Textes
                color = Color(0, 0, 0, 255),
                // Typografie des Textes
                // subztitle2 = Größenveränderung
                style = androidx.compose.material.MaterialTheme.typography.subtitle1
            )

        } // End Column
    } // End Row
    Row(modifier = Modifier
        .padding(2.dp)
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
    ) {
        Box(
            Modifier
                .size(100.dp)
                .background(color = Color(228, 200, 243, 255))
        )
        {
            Column() {
                androidx.compose.material.Text(
                    "", fontSize = 15.sp, modifier = Modifier.width(150.dp),
                    color = Color(
                        133,
                        133,
                        133,
                        255
                    )
                )
                androidx.compose.material.Text(
                    "Value", fontSize = 15.sp, modifier = Modifier.width(150.dp),
                    color = Color(
                        133,
                        133,
                        133,
                        255
                    ), textAlign = TextAlign.Center
                )
                Spacer(
                    modifier = Modifier.height(7.dp)
                )
                androidx.compose.material.Text(
                    currentStateViewModel.valueList[index].toString(), fontSize = 15.sp, modifier = Modifier.width(150.dp),
                    color = Color(
                        133,
                        133,
                        133,
                        255
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

fun createStringList(): LinkedList<String?>{
    val list = LinkedList<String?>()
    list.addAll(listOf(null, null, null, null, null,null,null,null,null,null,null,null,null,null,null,null))
    return list
}

fun createIntList(): LinkedList<Int?>{
    val list = LinkedList<Int?>()
    list.addAll(listOf(null, null, null, null, null,null,null,null,null,null,null,null,null,null,null,null))
    return list
}

@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HashmapzTheme {
        val currentState by remember {
            mutableStateOf(CurrentState(
                mapSize = 16,
                steps = 1,
                keyList = createStringList(),
                valueList = createStringList(),
                hashcodeList = createIntList()
            ))
        }

        val viewModel : CurrentStateViewModel = CurrentStateViewModel()
        val commandController = viewModel.getCommandController()
        Home(rememberNavController(), commandController, viewModel )
    }
}
