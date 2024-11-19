package com.mmust.demeter.Views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.mmust.demeter.Models.Metric
import com.mmust.demeter.ui.composables.GreenHouseInfo
import com.mmust.demeter.ui.composables.GreenHouseScreenMetrics
import com.mmust.demeter.ui.theme.DemeterTheme


val metrics = listOf(
    Metric(title = "humidity", value = 30.24),
    Metric(title = "temperature", value = 76.22),
    Metric(title = "moisture", value = 52.24),
    Metric(title = "light", value = 22.24),
)
@Composable
fun GreenHouseScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        GreenHouseInfo()
        GreenHouseScreenMetrics(metrics)
    }
}

@Preview(showBackground=true)
@Composable
fun GreenHouseScreenPreview() {
    DemeterTheme{
        GreenHouseScreen()
    }
}
