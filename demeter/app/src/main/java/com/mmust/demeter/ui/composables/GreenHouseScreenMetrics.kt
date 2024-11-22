package com.mmust.demeter.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mmust.demeter.Models.Metric
import com.mmust.demeter.Models.getMetricIcon
import com.mmust.demeter.ui.theme.DemeterTheme

val metrics = listOf(
    Metric(title = "humidity", value = 30.24),
    Metric(title = "temperature", value = 76.22),
    Metric(title = "moisture", value = 52.24),
    Metric(title = "light", value = 22.24),
    )
@Composable
fun GreenHouseScreenMetrics(metrics: List<Metric>){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        metrics.forEach { metric ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = getMetricIcon(metric.title),
                    contentDescription = null,
                    modifier = Modifier.size(36.dp),
                    tint = MaterialTheme.colorScheme.primaryContainer,
                    )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = metric.title.toString().capitalize(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray

                )
                Text(
                    text = metric.value.toString(),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreenHouseStatsPreview() {
    DemeterTheme{
       GreenHouseScreenMetrics(metrics)
    }
}