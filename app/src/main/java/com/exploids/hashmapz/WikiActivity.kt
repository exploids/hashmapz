package com.exploids.hashmapz

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.exploids.hashmapz.ui.components.NestedScaffold
import com.exploids.hashmapz.ui.theme.HashmapzTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Wiki(navController: NavController) {
    NestedScaffold(navController = navController, title = "Wiki") {
        Column(modifier = Modifier.padding(it).padding(16.dp).verticalScroll(ScrollState(0)),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "What are hash maps?", style = MaterialTheme.typography.headlineMedium)
            Text(text = "A hash map maps a key to a value and stores this value pair in a hash table. The key is hashed and the hash code is used as an index to reference where the value is stored in the table. Also, key and value do not have to be of the same data type, e.g. the key can be of the data type string and the value of the data type integer.",
                style = MaterialTheme.typography.bodyMedium)
            Text(text = "What is hashing?", style = MaterialTheme.typography.headlineMedium)
            Text(text = "Hashing is a cryptographic function, the so-called hash function, which works only in one direction. In our case, the value of the key is converted into a hash value by mathematical function. The Hash value cannot be converted into the original value afterward. Hash values will always be the same length, no matter how big the value of the key was before. In addition, different values of the key should always result in a different Hashvalue and equal values to the same Hashvalue.",
                style = MaterialTheme.typography.bodyMedium)
            Text(text = "Open Addressing",
                style = MaterialTheme.typography.headlineMedium)
            Text(text = "Open addressing describes the procedure in the event of a collision where the entries are shifted to free slots."
                , style = MaterialTheme.typography.bodyMedium)
            Text(text = "Linear Probing",
                style = MaterialTheme.typography.headlineMedium)
            Text(text = "In case the location that we indexed is already occupied, we check for the next slot in sequential manner. Lookups are performed in the same way, by searching the table sequentially starting at the index given by the hash function, until finding an entry with a matching key or an empty slot."
                , style = MaterialTheme.typography.bodyMedium)
            Text(text = "Challenges in Linear Probing",
                style = MaterialTheme.typography.headlineMedium)
            Text(text = "1.Primary Clustering: One of the problems with linear probing is Primary clustering, many consecutive elements form groups and it starts taking time to find a free slot or to search for an element.\n 2.Secondary Clustering: Secondary clustering is less severe, two records only have the same collision chain (Probe Sequence) if their initial position is the same."
                , style = MaterialTheme.typography.bodyMedium)
            Text(text = "Quadratic Probing",
                style = MaterialTheme.typography.headlineMedium)
            Text(text = "In case of a collision, we move to the next slot by squaring the number of collisions we had and check if it is unoccupied.  \n Quadratic probing is a method with the help of which we can solve the problem of clustering that was discussed above."
                , style = MaterialTheme.typography.bodyMedium)
            Text(text = "What happens when the table is full?", style = MaterialTheme.typography.headlineMedium)
            Text(text = "The table of the HashMap cannot become completely full unless you change the load factor. Because the load factor indicates how full the table can become before the table is extended automatically. Then the hash table is hashed again, i.e. the internal data structure is formed anew. The rebuilding should approximately double the table.",
                style = MaterialTheme.typography.bodyMedium)
            Text(text = "Deleting an entry sometimes restructures the hash map, why?",
                style = MaterialTheme.typography.headlineMedium)
            Text(text = "For all records in a cluster, there must be no vacant slots between their natural hash position and their current position otherwise lookups will terminate before finding the record. Therefore the cluster will be restructured to fill up the gap."
                , style = MaterialTheme.typography.bodyMedium)


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
