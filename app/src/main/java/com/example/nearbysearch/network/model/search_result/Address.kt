package com.example.nearbysearch.network.model.search_result


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Address(
    @SerialName("country")
    val country: String? = "",
    @SerialName("countryCode")
    val countryCode: String? = "",
    @SerialName("countryCodeISO3")
    val countryCodeISO3: String? = "",
    @SerialName("countrySecondarySubdivision")
    val countrySecondarySubdivision: String? = "",
    @SerialName("countrySubdivision")
    val countrySubdivision: String? = "",
    @SerialName("countrySubdivisionName")
    val countrySubdivisionName: String? = "",
    @SerialName("extendedPostalCode")
    val extendedPostalCode: String? = "",
    @SerialName("freeformAddress")
    val freeformAddress: String? = "",
    @SerialName("localName")
    val localName: String? = "",
    @SerialName("municipality")
    val municipality: String? = "",
    @SerialName("municipalitySubdivision")
    val municipalitySubdivision: String? = "",
    @SerialName("postalCode")
    val postalCode: String? = "",
    @SerialName("streetName")
    val streetName: String? = "",
    @SerialName("streetNumber")
    val streetNumber: String? = ""
)