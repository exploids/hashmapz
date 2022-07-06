package com.exploids.hashmapz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.relocation.BringIntoViewRequester
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
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.exploids.hashmapz.controller.CommandController
import com.exploids.hashmapz.model.CurrentState
import com.exploids.hashmapz.model.CurrentStateViewModel
import com.exploids.hashmapz.ui.keys
import com.exploids.hashmapz.ui.theme.HashmapzTheme
import com.exploids.hashmapz.ui.theme.Typography
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.random.Random


@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = CurrentStateViewModel()
        val commandController = viewModel.getCommandController()
        setContent {
            val navController = rememberNavController()
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
fun Home(
    navController: NavController,
    commandController: CommandController,
    currentStateViewModel: CurrentStateViewModel
) {

    var isFirstTime by remember {
        mutableStateOf(true)
    }
    if (isFirstTime) {
        isFirstTime = false
    }


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
                            val text = stringResource(
                                id = currentStateViewModel.currentDescription,
                                currentStateViewModel.state.usedKey ?: "(?)",
                                currentStateViewModel.state.usedHashcode ?: "(?)",
                                currentStateViewModel.state.mapSize,
                                currentStateViewModel.state.usedIndex ?: "(?)",
                                currentStateViewModel.state.steps,
                                currentStateViewModel.state.foundValue ?: "(?)",
                            )
                            val styled = buildAnnotatedString {
                                var position = 0
                                var emphasized = false
                                while (position < text.length) {
                                    val next = text.indexOf('*', position)
                                    if (next < 0) {
                                        append(text.substring(position, text.length))
                                        position = text.length
                                    } else {
                                        append(text.substring(position, next))
                                        if (emphasized) {
                                            pop()
                                        } else {
                                            pushStyle(SpanStyle(fontStyle = FontStyle.Italic))
                                        }
                                        emphasized = !emphasized
                                        position = next + 1
                                    }
                                }
                                toAnnotatedString()
                            }
                            Crossfade(targetState = styled) {
                                Text(it)
                            }
                        }
                        Row(
                            modifier = Modifier.align(Alignment.End),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedButton(
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
                    if (currentStateViewModel.currentIndex != null) {
                        scope.launch {
                            listState.animateScrollToItem(currentStateViewModel.currentIndex!!, 0)
                        }
                    }
                    items(currentStateViewModel.mapSize) { index ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier
                                .height(IntrinsicSize.Min)
                                .animateItemPlacement(animationSpec = tween())
                        ) {
                            val color1 =
                                if (currentStateViewModel.currentIndex == index) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.background
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(40.dp)
                                    .clip(MaterialTheme.shapes.small)
                                    .background(color1),
                                contentAlignment = Alignment.Center
                            ) {
                                val color =
                                    if (currentStateViewModel.currentIndex == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                Text(
                                    text = index.toString(),
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = color
                                )
                            }
                            Card {
                                Box(
                                    modifier = Modifier
                                        .animateContentSize(animationSpec = tween())
                                ) {
                                    val entry =
                                        if (currentStateViewModel.listKey[index] == null) null else listOf(
                                            currentStateViewModel.listKey[index].toString(),
                                            currentStateViewModel.hashList[index].toString(),
                                            currentStateViewModel.valueList[index].toString()
                                        )
                                    Crossfade(
                                        entry,
                                        animationSpec = tween()
                                    ) {
                                        if (it == null) {
                                            Box(
                                                Modifier
                                                    .height(56.dp)
                                                    .fillMaxWidth()
                                            )
                                        } else {
                                            HashEntry(it[0], it[1], it[2])
                                        }
                                    }
                                }
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
    currentStateViewModel: CurrentStateViewModel = viewModel(),
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
fun InsertBottomSheet(
    currentStateViewModel: CurrentStateViewModel = viewModel(),
    scope: CoroutineScope,
    sheetState: ModalBottomSheetState
) {
    var key by remember { mutableStateOf("") }
    var keyDefault by remember { mutableStateOf(keys[Random.nextInt(keys.size)]) }
    var value by remember { mutableStateOf("") }
    var valueDefault by remember { mutableStateOf(Random.nextInt(100).toString()) }
    BottomSheetContent(title = "Insert or update an entry", label = "Set", {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = key,
            onValueChange = { newText ->
                key = newText
            },
            singleLine = true,
            placeholder = { Text(text = keyDefault) },
            label = { Text(text = "Key") })

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = { newText ->
                value = newText
            },
            singleLine = true,
            placeholder = { Text(text = valueDefault) },
            label = { Text(text = "Value") })

    }, currentStateViewModel, scope, sheetState) {
        currentStateViewModel.getCommandController().add(
            if (key.isBlank()) keyDefault else key,
            if (value.isBlank()) valueDefault else value
        )
        currentStateViewModel.update()
        key = ""
        keyDefault = keys[Random.nextInt(keys.size)]
        value = ""
        valueDefault = Random.nextInt(100).toString()
        scope.launch {
            sheetState.hide()
        }
    }
}


@ExperimentalMaterialApi
@Composable
fun LookupBottomSheet(
    currentStateViewModel: CurrentStateViewModel = viewModel(),
    scope: CoroutineScope,
    sheetState: ModalBottomSheetState
) {
    var key by remember { mutableStateOf("") }
    BottomSheetContent(title = "Lookup an entry", label = "Lookup", {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = key,
            onValueChange = { newText ->
                key = newText
            },
            singleLine = true,
            label = { Text(text = "Key") })
    }, currentStateViewModel, scope, sheetState) {
        currentStateViewModel.getCommandController().search(key)
        currentStateViewModel.update()
        scope.launch {
            sheetState.hide()
        }
    }
}


@ExperimentalMaterialApi
@Composable
fun RemoveBottomSheet(
    currentStateViewModel: CurrentStateViewModel = viewModel(),
    scope: CoroutineScope,
    sheetState: ModalBottomSheetState
) {
    var key by remember { mutableStateOf("") }
    BottomSheetContent(title = "Remove an entry", label = "Remove", {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = key,
            onValueChange = { newText ->
                key = newText
            },
            singleLine = true,
            label = { Text(text = "Key") })
    }, currentStateViewModel, scope, sheetState) {
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
fun RenewBottomSheet(
    currentStateViewModel: CurrentStateViewModel = viewModel(),
    scope: CoroutineScope,
    sheetState: ModalBottomSheetState
) {
    var selectedProbingMode by remember { mutableStateOf("Linear Probing") }
    var loadfactor by remember { mutableStateOf("0.75") }
    BottomSheetContent(title = "Renew Hashmap", label = "Renew", {
        var expanded by remember { mutableStateOf(false) }
        val availableProbingModes = listOf("Linear Probing", "Quadratic Probing", "Double Hashing")
        var textFieldSize by remember { mutableStateOf(Size.Zero) }

        ExposedDropdownMenuBox(
            expanded = expanded, onExpandedChange = { expanded = !expanded },
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        textFieldSize = coordinates.size.toSize()
                    },
                value = selectedProbingMode,
                onValueChange = { selectedProbingMode = it },
                label = { Text(text = "Probing Modes") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                readOnly = true
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }) {
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
            singleLine = true,
            label = { Text(text = "Load factor") })
    }, currentStateViewModel, scope, sheetState) {
        currentStateViewModel.getCommandController()
            .renewMap(selectedProbingMode, loadfactor.toFloat())
        currentStateViewModel.update()
        scope.launch {
            sheetState.hide()
        }

    }

}

@ExperimentalMaterial3Api
@Composable
@Preview
fun HashEntry(
    key: String = "Banana",
    hashCode: String = "123456789",
    value: String = "32 pieces"
) {
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .weight(0.67f)
                .padding(12.dp),
        ) {
            Text(text = "Key", style = Typography.labelSmall)
            Text(text = key)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Hash code", style = Typography.labelSmall)
            Text(text = hashCode)
        }
        Box(
            modifier = Modifier
                .weight(0.33f)
                .fillMaxHeight()
                .background(color = MaterialTheme.colorScheme.primaryContainer)
                .padding(12.dp)
        ) {
            Column {
                Text(text = "Value", style = Typography.labelSmall)
                Text(text = value)
            }
        }
    }
}


fun createStringList(): LinkedList<String?> {
    val list = LinkedList<String?>()
    list.addAll(listOf(null, null, null, null, null, null, null, null))
    return list
}

fun createIntList(): LinkedList<Int?> {
    val list = LinkedList<Int?>()
    list.addAll(listOf(null, null, null, null, null, null, null, null))
    return list
}

@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HashmapzTheme {
        val currentState by remember {
            mutableStateOf(
                CurrentState(
                    mapSize = 16,
                    steps = 1,
                    keyList = createStringList(),
                    valueList = createStringList(),
                    hashcodeList = createIntList()
                )
            )
        }

        val viewModel = CurrentStateViewModel()
        val commandController = viewModel.getCommandController()
        Home(rememberNavController(), commandController, viewModel)
    }
}
