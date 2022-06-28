package com.example.nearbysearch.network.model.search_result


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EntryPoint(
    @SerialName("position")
    val position: PositionX? = PositionX(),
    @SerialName("type")
    val type: String? = ""
)