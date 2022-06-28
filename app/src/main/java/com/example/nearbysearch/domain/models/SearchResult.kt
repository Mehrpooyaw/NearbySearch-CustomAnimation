package com.example.nearbysearch.domain.models

import com.example.nearbysearch.network.model.search_result.Summary

data class SearchResult(
    val results: List<Spot>? = listOf(),
    val summary: Summary? = Summary()
)
