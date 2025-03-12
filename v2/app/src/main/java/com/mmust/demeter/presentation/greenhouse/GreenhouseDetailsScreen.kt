package com.mmust.demeter.presentation.greenhouse

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mmust.demeter.domain.model.Greenhouse

@Composable
fun GreenhouseDetailsScreen(
    greenhouseDetailsViewModel: GreenhouseDetailsViewModel,
    greenhouseId: String
    ) {
    val uiState by greenhouseDetailsViewModel.uiState.collectAsState()

    LaunchedEffect(greenhouseId) {
        greenhouseDetailsViewModel.fetchGreenhouse(greenhouseId)
    }

    DisposableEffect(Unit) {
        onDispose {
            greenhouseDetailsViewModel.clearState()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
      when {
          uiState.isLoading -> CircularProgressIndicator()
          uiState.greenhouse != null -> Text("Greenhouse name: ${uiState.greenhouse}")
          uiState.error != null -> Text("Error: ${uiState.error}")
      }

    }
}