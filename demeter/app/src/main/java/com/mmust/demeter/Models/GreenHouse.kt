package com.mmust.demeter.Models

data class GreenHouse(
    val name: String,
    val metrics: List<Metric>,
    val imageUrl: String,
    val createdAt: String? = null,
)
