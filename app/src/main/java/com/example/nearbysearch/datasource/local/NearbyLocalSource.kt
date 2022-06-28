package com.example.nearbysearch.datasource.local

import com.example.nearbysearch.domain.models.Spot

interface NearbyLocalSource {
    suspend fun insert(spot: Spot)

    suspend fun insert(spots : List<Spot>)

    suspend fun delete(id : String)

    suspend fun  deleteAll()

    suspend fun select(id : String) : Spot

    suspend fun selectByLonLat(longitude : Double, latitude : Double, radius : Int) : List<Spot>

    suspend fun selectFavorites() : List<Spot>

    suspend fun updateFavoriteStatus(id : String, isFavorite : Boolean)
}