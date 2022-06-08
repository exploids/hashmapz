package com.exploids.hashmapz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.exploids.hashmapz.ui.theme.HashmapzTheme

class AboutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HashmapzTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    About()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun About() {
    Scaffold(
        topBar = {
            SmallTopAppBar(title = {
                Text(text = "About")
            })
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview4() {
    HashmapzTheme {
        About()
    }
}