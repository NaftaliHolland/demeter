package com.mmust.demeter.presentation.onboarding

import androidx.annotation.DrawableRes
import com.mmust.demeter.R

data class Page(
    val title: String,
    val description: String,
    @DrawableRes val image: Int
)

val pages = listOf(
    Page(
        title = "Monitor you green house in real time",
        description = "Stay connected to your greenhouse from anywhere. Our smart sensors track temperature, humidity, and soil moisture, giving you real-time insights into plant health",
        image = R.drawable.image_1
    ),
    Page(
        title = "Automate Watering & Climate Control",
        description = "Let technology do the work. Automate irrigation and climate adjustments based on live sensor data, ensuring optimal conditions for your crops.",
        image = R.drawable.image_1
    ),
    Page(
        title = "Get Smart Alerts & Insights",
        description = "Receive AI-powered recommendations and alerts when your plants need attention. Optimize growth with data-driven decisions.",
        image = R.drawable.image_1
    ),
)