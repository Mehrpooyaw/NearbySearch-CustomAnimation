package com.example.nearbysearch.repository

import android.util.Log
import com.example.nearbysearch.datasource.local.NearbyLocalSource
import com.example.nearbysearch.datasource.remote.NearbyRemoteSource
import com.example.nearbysearch.domain.data.DataState
import com.example.nearbysearch.domain.models.Spot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class NearbySearchRepository : KoinComponent {
    private val remoteSource : NearbyRemoteSource = get(null)
    private val localSource : NearbyLocalSource = get(null)


    fun getSpots(lon : Double, lat : Double, radius : Int) : Flow<DataState<List<Spot>>>  = flow {
        emit(DataState.loading())
        try {
            try {
                val network = remoteSource.searchNearbySpots(lon, lat, radius)
                if (network != null) {
                    network.results?.let { localSource.insert(it) }
                }
            }catch (e : Exception) {
            }

            emit(DataState.success(getSpotsFromDb(lon = lon, lat = lat, radius = radius)))

        }catch (e : Exception){
            Log.e("AppDebug", "getSpots: ${e.message}", )

        }
    }

    suspend fun clearCache() {
        localSource.deleteAll()
    }

    suspend fun updateFavoriteStatus(id : String, isFavorite : Boolean){
        localSource.updateFavoriteStatus(id,isFavorite)
    }

    private suspend fun getSpotsFromDb(lon: Double, lat: Double, radius: Int) : List<Spot> {
            return localSource.selectByLonLat(lon,lat,radius)
    }
    fun getFavorites() : Flow<DataState<List<Spot>>> = flow{
        emit(DataState.loading())
        try {
            emit(DataState.success(localSource.selectFavorites()))

        }catch (e : Exception){
            Log.e("AppDebug", "getSpots: ${e.message}", )

        }
    }
}