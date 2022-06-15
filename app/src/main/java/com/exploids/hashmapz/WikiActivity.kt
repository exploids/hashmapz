package com.exploids.hashmapz

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.exploids.hashmapz.ui.components.NestedScaffold
import com.exploids.hashmapz.ui.theme.HashmapzTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Wiki(navController: NavController) {
    NestedScaffold(navController = navController, title = "Wiki") {
        Column(modifier = Modifier.padding(it)) {
            Text(text = "HashmapZ", style = MaterialTheme.typography.titleLarge)
            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Nam at lectus urna duis convallis convallis tellus id interdum. Mauris ultrices eros in cursus turpis massa tincidunt. Odio euismod lacinia at quis risus sed vulputate odio. Gravida rutrum quisque non tellus. Purus faucibus ornare suspendisse sed nisi lacus sed viverra. Sed lectus vestibulum mattis ullamcorper velit sed. Semper feugiat nibh sed pulvinar. Mi eget mauris pharetra et. Nunc lobortis mattis aliquam faucibus. Aliquam etiam erat velit scelerisque in. Eu mi bibendum neque egestas congue quisque egestas. Cursus vitae congue mauris rhoncus aenean vel. Mauris cursus mattis molestie a iaculis at. Bibendum est ultricies integer quis auctor elit sed vulputate. At imperdiet dui accumsan sit.",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    HashmapzTheme {
        Wiki(rememberNavController())
    }
}
