package com.example.nearbysearch.network.model.search_result


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpotDto(
    @SerialName("address")
    val address: Address? = Address(),
    @SerialName("dist")
    val dist: Double? = 0.0,
    @SerialName("entryPoints")
    val entryPoints: List<EntryPoint>? = listOf(),
    @SerialName("id")
    val id: String? = "",
    @SerialName("info")
    val info: String? = "",
    @SerialName("poi")
    val poi: Poi? = Poi(),
    @SerialName("position")
    val position: PositionX? = PositionX(),
    @SerialName("score")
    val score: Double? = 0.0,
    @SerialName("type")
    val type: String? = "",
    @SerialName("viewport")
    val viewport: Viewport? = Viewport()
)