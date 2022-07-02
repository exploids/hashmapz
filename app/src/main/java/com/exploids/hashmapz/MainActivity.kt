package com.exploids.hashmapz

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.exploids.hashmapz.command.Command
import com.exploids.hashmapz.command.GoToIndexCommand
import com.exploids.hashmapz.controller.CommandController
import com.exploids.hashmapz.model.CurrenStateViewModel
import com.exploids.hashmapz.model.CurrentState
import com.exploids.hashmapz.ui.theme.HashmapzTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayDeque


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val viewModel : CurrenStateViewModel = CurrenStateViewModel()
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

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavController, commandController: CommandController, currenStateViewModel:  CurrenStateViewModel = viewModel()) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val (selectedBottomSheet, setSelectedBottomSheet) = remember {
        mutableStateOf(0)
    }
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            when (selectedBottomSheet) {
                0 -> InsertBottomSheet(currenStateViewModel, scope, sheetState)
                1 -> LookupBottomSheet(currenStateViewModel, scope, sheetState)
                2 -> RemoveBottomSheet(currenStateViewModel, scope, sheetState)
                3 -> RenewBottomSheet(currenStateViewModel, scope, sheetState)
            }
        }
    ) {
        Scaffold(
            bottomBar = {
                HomeBottomBar(navController, scope, sheetState, setSelectedBottomSheet)
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
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
                        Text("Checking whether the current index is empty.")
                        Row(
                            modifier = Modifier.align(Alignment.End),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = {
                                    commandController.prevCommand()
                                    currenStateViewModel.update()
                                          },
                                enabled = !currenStateViewModel.isPrevDisabled

                            ) {
                                Text(text = "Back")
                            }
                            Button(
                                onClick = {
                                    commandController.nextCommand()
                                    currenStateViewModel.update()
                                          },
                                enabled = !currenStateViewModel.isNextDisabled
                                ) {
                                Text(text = "Next")
                            }
                        }
                    }
                }
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(0.dp, 0.dp, 0.dp, 16.dp)
                ) {
                    items(16) { index ->
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Row(
                                modifier = Modifier
                                    .height(80.dp)
                                    .width(40.dp),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = index.toString(),
                                    style = MaterialTheme.typography.headlineSmall,
                                )
                            }
                            Card {
                                Box(
                                    Modifier
                                        .height(80.dp)
                                        .fillMaxWidth()
                                )
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
    currenStateViewModel:  CurrenStateViewModel = viewModel(),
    scope: CoroutineScope,
    sheetState: ModalBottomSheetState,
    onClick: () -> Unit
) {
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


@ExperimentalMaterialApi
@Composable
fun InsertBottomSheet(currenStateViewModel:  CurrenStateViewModel = viewModel(), scope: CoroutineScope, sheetState: ModalBottomSheetState) {
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

    }, currenStateViewModel, scope, sheetState ){
        currenStateViewModel.getCommandController().add(key, value)
        currenStateViewModel.update()
        scope.launch {
            sheetState.hide()
        }
    }
}


@ExperimentalMaterialApi
@Composable
fun LookupBottomSheet(currenStateViewModel:  CurrenStateViewModel = viewModel(), scope: CoroutineScope, sheetState: ModalBottomSheetState) {
    var key by remember { mutableStateOf("Banana") }
    BottomSheetContent(title = "Lookup an entry", label = "Lookup", {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = key,
            onValueChange = { newText ->
                key = newText
            },
            label = { Text(text = "Key") })
    }, currenStateViewModel, scope, sheetState){
        currenStateViewModel.getCommandController().search(key)
        currenStateViewModel.update()
        scope.launch {
            sheetState.hide()
        }
    }
}


@ExperimentalMaterialApi
@Composable
fun RemoveBottomSheet(currenStateViewModel:  CurrenStateViewModel = viewModel(), scope: CoroutineScope, sheetState: ModalBottomSheetState) {
    var key by remember { mutableStateOf("Banana") }
    BottomSheetContent(title = "Remove an entry", label = "Remove", {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = key,
            onValueChange = { newText ->
                key = newText
            },
            label = { Text(text = "Key") })
    }, currenStateViewModel, scope, sheetState){
        currenStateViewModel.getCommandController().delete(key)
        currenStateViewModel.update()
        scope.launch {
            sheetState.hide()
        }
    }
}


@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@Composable
fun RenewBottomSheet(currenStateViewModel:  CurrenStateViewModel = viewModel(), scope: CoroutineScope, sheetState: ModalBottomSheetState) {
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
    }, currenStateViewModel, scope, sheetState){

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

        val viewModel : CurrenStateViewModel = CurrenStateViewModel()
        val commandController = viewModel.getCommandController()
        Home(rememberNavController(), commandController, viewModel )
    }
}
