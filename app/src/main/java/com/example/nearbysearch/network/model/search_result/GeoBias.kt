package com.example.nearbysearch.network.model.search_result


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeoBias(
    @SerialName("lat")
    val lat: Double? = 0.0,
    @SerialName("lon")
    val lon: Double? = 0.0
)