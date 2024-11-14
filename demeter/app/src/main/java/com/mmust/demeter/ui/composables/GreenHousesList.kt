package com.mmust.demeter.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mmust.demeter.Models.GreenHouse
import com.mmust.demeter.Models.Metric
import com.mmust.demeter.ui.theme.DemeterTheme

val greenHouses = listOf(
    GreenHouse(
        name = "Green house 1",
        metrics = listOf(
            Metric(icon = Icons.Filled.Favorite, value = 23.23),
            Metric(icon = Icons.Filled.Favorite, value = 23.23),
            Metric(icon = Icons.Filled.Favorite, value = 23.23),
            Metric(icon = Icons.Filled.Favorite, value = 23.23)
        )),
    GreenHouse(
        name = "Green house 2",
        metrics = listOf(
            Metric(icon = Icons.Filled.Favorite, value = 23.23),
            Metric(icon = Icons.Filled.Favorite, value = 43.2),
            Metric(icon = Icons.Filled.Favorite, value = 90.22),
            Metric(icon = Icons.Filled.Favorite, value = 10.43)
        )),
    GreenHouse(
        name = "Green house 3",
        metrics = listOf(
            Metric(icon = Icons.Filled.Favorite, value = 25.23),
            Metric(icon = Icons.Filled.Favorite, value = 11.23),
            Metric(icon = Icons.Filled.Favorite, value = 50.23),
            Metric(icon = Icons.Filled.Favorite, value = 25.23)
        )
    ),
)

@Composable
fun GreenHousesList () {
    Column(
        modifier = Modifier.padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        greenHouses.forEach { greenHouse ->
            GreenHouseCard(greenHouse.name, greenHouse.metrics)
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