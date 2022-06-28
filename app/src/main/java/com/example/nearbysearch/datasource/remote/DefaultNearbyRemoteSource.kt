package com.example.nearbysearch.datasource.remote

import com.example.nearbysearch.network.NetworkService
import com.example.nearbysearch.network.model.search_result.fromDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultNearbyRemoteSource(
    private val networkService: NetworkService
    ) : NearbyRemoteSource {
    override suspend fun searchNearbySpots(lon : Double, lat : Double,radius : Int) = withContext(Dispatchers.IO) {
            networkService.getNearbySpots(lon,lat,50000)?.fromDto()
    }
}