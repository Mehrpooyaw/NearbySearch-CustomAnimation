package com.example.nearbysearch.network.model.search_result


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Viewport(
    @SerialName("btmRightPoint")
    val btmRightPoint: BtmRightPoint? = BtmRightPoint(),
    @SerialName("topLeftPoint")
    val topLeftPoint: TopLeftPoint? = TopLeftPoint()
)