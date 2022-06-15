package com.exploids.hashmapz.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.exploids.hashmapz.ui.theme.HashmapzIcons
import com.exploids.hashmapz.ui.theme.HashmapzTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NestedScaffold(
    navController: NavController,
    title: String,
    content: @Composable (it: PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            SmallTopAppBar(title = {
                Text(text = title)
            }, navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(HashmapzIcons.ArrowBack, "Localized description")
                }
            })
        }
    ) {
        content(it)
    }
}

@Preview(showBackground = true)
@Composable
fun NestedScaffoldPreview() {
    HashmapzTheme {
        NestedScaffold(rememberNavController(), "Title") {}
    }
}
