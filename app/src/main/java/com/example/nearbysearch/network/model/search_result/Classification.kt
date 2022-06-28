package com.example.nearbysearch.network.model.search_result


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Classification(
    @SerialName("code")
    val code: String? = "",
    @SerialName("names")
    val names: List<Name?>? = listOf()
)