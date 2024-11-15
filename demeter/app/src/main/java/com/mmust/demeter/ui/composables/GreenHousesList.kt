package com.mmust.demeter.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mmust.demeter.ViewModels.GreenHouseViewModel
import com.mmust.demeter.ui.theme.DemeterTheme

@Composable
fun GreenHousesList (viewModel: GreenHouseViewModel = GreenHouseViewModel()) {
    val greenHouses by viewModel.greenHouses.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState(0)

    Column(
        modifier = Modifier.padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Green Houses:",
            style = MaterialTheme.typography.titleLarge
        )
        if (isLoading) {
            CircularProgressIndicator()
        } else if (error != null) {
            Text("Error: $error")
        } else {
            greenHouses.forEach { greenHouse ->
                GreenHouseCard(greenHouse.name, greenHouse.imageUrl, greenHouse.metrics)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreenHousesListPreview () {
    DemeterTheme {
        GreenHousesList()
    }
}