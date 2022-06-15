package com.exploids.hashmapz

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.exploids.hashmapz.ui.components.NestedScaffold
import com.exploids.hashmapz.ui.theme.HashmapzTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun About(navController: NavController) {
    NestedScaffold(navController = navController, title = "About") {
        Column(modifier = Modifier.padding(it)) {
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
