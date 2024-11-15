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
import com.mmust.demeter.Models.GreenHouse
import com.mmust.demeter.Models.Metric
import com.mmust.demeter.Models.getMetricIcon
import com.mmust.demeter.ViewModels.GreenHouseViewModel
import com.mmust.demeter.ui.theme.DemeterTheme

val greenHouses = listOf(
    GreenHouse(
        name = "Green house 1",
        imageUrl = "https://images.unsplash.com/photo-1524486361537-8ad15938e1a3?q=80&w=869&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        metrics = listOf(
            Metric(title = "temperature", icon = getMetricIcon("temperature"), value = 23.23),
            Metric(title = "humidity", icon = getMetricIcon("humidity"), value=23.5),
            Metric(title = "moisture", icon = getMetricIcon("moisture"), value = 23.23),
            Metric(title = "light", icon = getMetricIcon("light"), value = 23.23)
        )),
    GreenHouse(
        name = "Green house 2",
        imageUrl = "https://plus.unsplash.com/premium_photo-1680322474521-60fcadf0a416?q=80&w=387&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        metrics = listOf(
            Metric(title = "temperature", icon = getMetricIcon("temperature"), value = 23.23),
            Metric(title = "humidity", icon = getMetricIcon("humidity"), value=23.5),
            Metric(title = "moisture", icon = getMetricIcon("moisture"), value = 23.23),
            Metric(title = "light", icon = getMetricIcon("light"), value = 23.23)
        )),
    GreenHouse(
        name = "Green house 3",
        imageUrl = "https://images.unsplash.com/photo-1591754060004-f91c95f5cf05?q=80&w=870&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        metrics = listOf(
            Metric(title = "temperature", icon = getMetricIcon("temperature"), value = 23.23),
            Metric(title = "humidity", icon = getMetricIcon("humidity"), value=23.5),
            Metric(title = "moisture", icon = getMetricIcon("moisture"), value = 23.23),
            Metric(title = "light", icon = getMetricIcon("light"), value = 23.23)
        )
    ),
)

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