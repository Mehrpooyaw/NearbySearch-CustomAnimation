package com.example.nearbysearch.network.model.search_result


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Name(
    @SerialName("name")
    val name: String? = "",
    @SerialName("nameLocale")
    val nameLocale: String? = ""
)