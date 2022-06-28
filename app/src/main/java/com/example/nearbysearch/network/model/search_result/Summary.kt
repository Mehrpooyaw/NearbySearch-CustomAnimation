package com.example.nearbysearch.network.model.search_result


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Summary(
    @SerialName("fuzzyLevel")
    val fuzzyLevel: Int? = 0,
    @SerialName("geoBias")
    val geoBias: GeoBias? = GeoBias(),
    @SerialName("numResults")
    val numResults: Int? = 0,
    @SerialName("offset")
    val offset: Int? = 0,
    @SerialName("queryTime")
    val queryTime: Int? = 0,
    @SerialName("queryType")
    val queryType: String? = "",
    @SerialName("totalResults")
    val totalResults: Int? = 0
)