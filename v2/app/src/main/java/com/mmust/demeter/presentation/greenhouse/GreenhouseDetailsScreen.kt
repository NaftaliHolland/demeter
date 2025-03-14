package com.mmust.demeter.presentation.greenhouse

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.mmust.demeter.R
import com.mmust.demeter.domain.model.Device
import com.mmust.demeter.domain.model.Greenhouse
import com.mmust.demeter.ui.theme.DemeterTheme


data class Metric(
    val icon: Int,
    val iconTint: Color,
    val title: String,
    val value: String,
    val unit: String
)

fun getIconTint(title: String): Color {
    return when (title.toLowerCase()) {
        "temperature" -> Color(0xFFFBD38D)
        "humidity" -> Color(0xFF90CDF4)
        "leaf temperature" -> Color(0xFF9AE6B4)
        "light" -> Color(0xFFFBD38D)
        "soil humidity" -> Color(0xFFCBD5E0)
        else -> Color.Gray
    }
}

fun getMetricUnit(title: String): String {
    return when (title.lowercase()) {
        "temperature" -> "°C"
        "leaf temperature" -> "°C"
        "humidity" -> "%"
        "soil humidity" -> "%"
        "light" -> "%"
        // Default unit if none matches
        else -> ""
    }
}

val metrics = listOf(
    Metric(
        icon = R.drawable.ic_leaf,
        iconTint = getIconTint("Leaf Temperature"),
        title = "Leaf Temperature",
        value = "23",
        unit = getMetricUnit("Leaf Temperature"),
    ),
    Metric(
        icon = R.drawable.ic_soil,
        iconTint = getIconTint("Soil Humidity"),
        title = "Soil humidity",
        value = "74",
        unit = getMetricUnit("Soil humidity"),
    ),
    Metric(
        icon = R.drawable.ic_humidity,
        iconTint = getIconTint("Humidity"),
        title = "Humidity",
        value = "75",
        unit = getMetricUnit("Humidity"),
    ),
    Metric(
        icon = R.drawable.ic_temperature,
        iconTint = getIconTint("Temperature"),
        title = "Temperature",
        value = "22",
        unit = getMetricUnit("Temperature"),
    ),
)
@Composable
fun GreenhouseDetailsScreen(
    viewModel: GreenhouseDetailsViewModel,
    greenhouseId: String
) {
    val devicesState by viewModel.devicesState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.refreshDevices(greenhouseId)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.greenhouse1),
                contentDescription = "Tomato Greenhouse",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
            Text(
                text = "Antalya Tomato\nGreenhouse",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 16.dp, top = 80.dp)
                    .align(Alignment.BottomStart)
            )
        }
        GreenhouseDetails(devicesState.devices)
    }
}


@Composable
fun GreenhouseDetails(devices: List<Device>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Details header with title and list button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Greenhouse Details",
                fontSize = 18.sp,
                color = Color.DarkGray
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
           verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(metrics) {item ->
                GreenhouseMetricCard(
                    icon = item.icon,
                    iconTint = item.iconTint,
                    title = item.title,
                    value = item.value,
                )
            }
            items(devices) { device ->
                Text(device.id)
            }
        }
    }
}

@Composable
fun GreenhouseMetricCard(
    icon: Int,
    iconTint: Color,
    title: String,
    value: String,
    //status: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon background
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(iconTint.copy(0.3F)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = title,
                    tint = iconTint,
                    modifier = Modifier.size(24.dp)
                )
            }

            // Text content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 16.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    color = Color.DarkGray
                )
                /*Text(
                    text = status,
                    fontSize = 12.sp,
                    color = Color.Gray
                )*/
            }

            // Value
            Text(
                text = value,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2D3748),
                modifier = Modifier.padding(end = 24.dp)
            )
        }
    }
}
