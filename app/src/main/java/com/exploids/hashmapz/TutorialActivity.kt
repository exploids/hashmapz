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

class TutorialActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HashmapzTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Tutorial()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Tutorial() {
    Scaffold(
        topBar = {
            SmallTopAppBar(title = {
                Text(text = "Tutorial")
            })
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    HashmapzTheme {
        Tutorial()
    }
}