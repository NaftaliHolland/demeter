package com.mmust.demeter.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.mmust.demeter.ui.theme.DemeterTheme
import com.mmust.demeter.Models.Metric
import com.mmust.demeter.Models.getMetricIcon
import com.mmust.demeter.navigation.Routes
import kotlin.reflect.typeOf

@Composable
fun GreenHouseCard (name: String, imageUrl: String, metrics: List<Metric>, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .border(.5.dp, Color.Gray, shape = RoundedCornerShape(16.dp))
            .clickable { navController.navigate(Routes.GREEN_HOUSE)},
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
    ) {
        Column() {
            Box (
                modifier = Modifier
                    .weight(2f)
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Green house image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Surface(
                    color = Color(0xFF0F8A4F),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .padding(12.dp)
                        .align(Alignment.TopStart)
                ) {
                    Text(
                        text = name,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.bodySmall
                    )
                }

            }
            MetricsList(metrics)
        }
    }
}

@Composable
fun MetricsList(metrics: List<Metric>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        metrics.forEach { metric ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(getMetricIcon(metric.title), contentDescription = null)
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = metric.value.toString()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreenHouseCardPreview () {
    DemeterTheme{
    }
}