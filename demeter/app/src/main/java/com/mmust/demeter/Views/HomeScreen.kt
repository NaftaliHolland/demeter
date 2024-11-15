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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mmust.demeter.Models.Temperature
import com.mmust.demeter.ViewModels.HomeViewModel
import com.mmust.demeter.ui.composables.BottomBar
import com.mmust.demeter.ui.composables.GreenHousesList
import com.mmust.demeter.ui.composables.WeatherCard
import com.mmust.demeter.ui.theme.DemeterTheme

@Composable
fun HomeScreen () {
    Scaffold(bottomBar = {BottomBar()}) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
                item { WeatherCard() }
                item { GreenHousesList() }
            }
        }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    DemeterTheme {
        HomeScreen()
    }
}