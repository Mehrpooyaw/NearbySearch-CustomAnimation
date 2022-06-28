package com.example.nearbysearch.network

import com.example.nearbysearch.network.model.search_result.SearchResultDto

interface NetworkService {
    suspend fun getNearbySpots(lon: Double, lat: Double, radius: Int) : SearchResultDto?
}