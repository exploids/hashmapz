package com.exploids.hashmapz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.exploids.hashmapz.ui.theme.HashmapzTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HashmapzTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Home()
                }
            }
        }
    }
}

@Composable
fun Home() {
    Scaffold(
        bottomBar = {
            BottomAppBar(
                icons = {
                    IconButton(onClick = { /* doSomething() */ }) {
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
                            Icons.Filled.Create,
                            contentDescription = "Localized description",
                        )
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { /* do something */ },
                        elevation = BottomAppBarDefaults.floatingActionButtonElevation()
                    ) {
                        Icon(Icons.Filled.Add, "Localized description")
                    }
                }
            )
        }
    ) {
        Text(text = "HashmapZ", modifier = Modifier.fillMaxHeight())
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HashmapzTheme {
        Home()
    }
}