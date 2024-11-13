package com.mmust.demeter.Views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mmust.demeter.Models.Temperature
import com.mmust.demeter.ViewModels.HomeViewModel

@Composable
fun HomeScreen (viewModel: HomeViewModel = HomeViewModel()) {
    val temperatureData by viewModel.temperatureData.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else if (error != null) {
           Text("Error: $error")
        } else {
            TemperatureList(temperatureData)
        }
    }
}

@Composable
fun TemperatureList(temperatures: List<Temperature>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(temperatures) { temperature ->
            TemperatureItem(temperature)
        }
    }
}

@Composable
fun TemperatureItem(temperature: Temperature) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("ID: ${temperature.id}")
            Text("Value: ${temperature.value}°C")
            Text("Time: ${temperature.time}s")
        }
    }
}