package com.mmust.demeter.Models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.graphics.vector.ImageVector

data class Metric(
    val title: String,
    val icon: ImageVector = Icons.Default.Place,
    val value: Double
)

fun getMetricIcon(title: String): ImageVector{
    return when (title) {
        "temperature" -> Icons.Default.Place
        "humidity" -> Icons.Default.Build
        "moisture" -> Icons.Default.Add
        "light" -> Icons.Default.MailOutline
        else -> Icons.Default.CheckCircle
    }
}