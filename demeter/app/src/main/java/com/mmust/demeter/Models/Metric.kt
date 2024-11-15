package com.mmust.demeter.Models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import com.mmust.demeter.R

data class Metric(
    val title: String,
    val value: Double
)

@Composable
fun getMetricIcon(title: String): ImageVector{
    return when (title) {
        "temperature" -> ImageVector.vectorResource(R.drawable.baseline_thermostat_24)
        "humidity" -> ImageVector.vectorResource(R.drawable.baseline_water_drop_24)
        "moisture" -> ImageVector.vectorResource(R.drawable.baseline_water_24)
        "light" -> ImageVector.vectorResource(R.drawable.baseline_light_mode_24)
        else -> Icons.Default.CheckCircle
    }
}