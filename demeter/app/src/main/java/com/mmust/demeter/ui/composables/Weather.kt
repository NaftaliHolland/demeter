package com.mmust.demeter.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mmust.demeter.ui.theme.DemeterTheme
import com.mmust.demeter.Models.Weather
import com.mmust.demeter.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

val weatherItems = listOf(
    Weather("Humidity", "54", "%"),
    Weather("Temperature", "26", "C"),
    Weather("Precipitation", "0.5", "cm")
)
@Composable
fun WeatherCard () {
    val currentDate = LocalDate.now()
    val dateFormatter = DateTimeFormatter.ofPattern("MMM d, EEEE", Locale.ENGLISH)
    val formatedDate = currentDate.format(dateFormatter)

   Column(
       modifier = Modifier
           .fillMaxWidth()
           .padding(24.dp)
   ) {
       WeatherOverview(date = formatedDate, currentWeather = "Cloudy")
       Spacer(modifier = Modifier.height(20.dp))
       WeatherItemsList(weatherItems)
   }
}

@Composable
fun WeatherItem (value: String, unit: String, title: String) {
    Column (
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row() {
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = unit,
                style = MaterialTheme.typography.titleLarge
            )
        }
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )
    }
}

@Composable
fun WeatherItemsList (weatherItems: List<Weather>) {
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        weatherItems.forEach { weatherItem ->
            WeatherItem(weatherItem.value, weatherItem.unit, weatherItem.title)
        }
    }

}

@Composable
fun WeatherOverview(date: String, currentWeather: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.baseline_cloud_24),
            contentDescription = null,
            modifier = Modifier.size(72.dp),
        )
        Column(verticalArrangement = Arrangement.Center) {
            Text(
                text = date,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
            Text(
                text = currentWeather,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherCardPreview () {
    DemeterTheme {
        WeatherCard()
    }
}
