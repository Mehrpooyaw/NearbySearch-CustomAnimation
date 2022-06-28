package com.example.nearbysearch.network.model.search_result


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Poi(
    @SerialName("categories")
    val categories: List<String?>? = listOf(),
    @SerialName("categorySet")
    val categorySet: List<CategorySet?>? = listOf(),
    @SerialName("classifications")
    val classifications: List<Classification?>? = listOf(),
    @SerialName("name")
    val name: String? = "",
    @SerialName("phone")
    val phone: String? = "",
    @SerialName("url")
    val url: String? = ""
)