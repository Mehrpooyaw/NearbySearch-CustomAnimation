package com.example.nearbysearch.datasource.remote

import com.example.nearbysearch.domain.models.SearchResult

interface NearbyRemoteSource {
    suspend fun searchNearbySpots(lon : Double, lat : Double, radius : Int) : SearchResult?
}