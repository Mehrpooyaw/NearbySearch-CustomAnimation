package com.example.nearbysearch.network.model.search_result


import com.example.nearbysearch.domain.models.SearchResult
import com.example.nearbysearch.domain.models.Spot
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResultDto(
    @SerialName("results")
    val results: List<SpotDto>? = listOf(),
    @SerialName("summary")
    val summary: Summary? = Summary()
)

fun SpotDto.fromDto() : Spot {
    return Spot(
        address = address,
        dist = dist,
        entryPoints = entryPoints,
        id = id,
        info = info,
        poi = poi,
        position = position,
        score = score,
        type = type,
        viewport = viewport
    )

}

fun List<SpotDto>?.fromDtoList() : List<Spot>?{
    return this?.map {
        it.fromDto()
    }
}

fun SearchResultDto.fromDto() : SearchResult {
    return SearchResult(
        results = results.fromDtoList(),
        summary = summary
    )
}