package com.example.nearbysearch.network.model.search_result


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategorySet(
    @SerialName("id")
    val id: Int? = 0
)