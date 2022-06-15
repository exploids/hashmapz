package com.exploids.hashmapz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.exploids.hashmapz.ui.theme.HashmapzTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            HashmapzTheme {
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        Home(navController)
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
fun Home(navController: NavController) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            InsertBottomSheet()
        }
    ) {
        Scaffold(
            bottomBar = {
                HomeBottomBar(navController, scope, sheetState)
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
                            Button(onClick = { /*TODO*/ }) {
                                Text(text = "Back")
                            }
                            Button(onClick = { /*TODO*/ }) {
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
    sheetState: ModalBottomSheetState
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
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(
                    Icons.Filled.Delete,
                    contentDescription = "Localized description",
                )
            }
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = "Localized description",
                )
            }
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(
                    Icons.Filled.Clear,
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

@Composable
fun BottomSheetContent(title: String, label: String, content: @Composable (() -> Unit)) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(text = title, style = MaterialTheme.typography.titleLarge)
        content()
        Button(modifier = Modifier.align(Alignment.End), onClick = { /*TODO*/ }) {
            Text(text = label)
        }
    }
}

@Preview
@Composable
fun InsertBottomSheet() {
    BottomSheetContent(title = "Insert or update an entry", label = "Set") {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = "Banana",
            onValueChange = {},
            label = { Text(text = "Key") })
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = "32 pieces",
            onValueChange = {},
            label = { Text(text = "Value") })
    }
}

@Preview
@Composable
fun LookupBottomSheet() {
    BottomSheetContent(title = "Lookup an entry", label = "Lookup") {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = "Banana",
            onValueChange = {},
            label = { Text(text = "Key") })
    }
}

@Preview
@Composable
fun RemoveBottomSheet() {
    BottomSheetContent(title = "Remove an entry", label = "Remove") {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = "Banana",
            onValueChange = {},
            label = { Text(text = "Key") })
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HashmapzTheme {
        Home(rememberNavController())
    }
}
