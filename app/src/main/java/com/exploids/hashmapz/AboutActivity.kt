package com.exploids.hashmapz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.exploids.hashmapz.ui.components.NestedScaffold
import com.exploids.hashmapz.ui.theme.HashmapzTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun About(navController: NavController) {
    NestedScaffold(navController = navController, title = "About") {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        ) {
            Text(text = "HashmapZ 1.0.0", style = MaterialTheme.typography.titleLarge)
            Text(
                text = "HashmapZ was developed with ❤️ by Niklas Rubel, Luca Selinski, Natalie Turek and Klara Diedrichs, students of the University of Applied Sciences Düsseldorf.",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AboutPreview() {
    HashmapzTheme {
        About(rememberNavController())
    }
}
