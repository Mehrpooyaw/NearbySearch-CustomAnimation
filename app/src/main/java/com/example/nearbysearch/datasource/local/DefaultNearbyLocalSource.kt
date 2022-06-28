package com.example.nearbysearch.datasource.local

import com.example.nearbysearch.Database
import com.example.nearbysearch.domain.models.Spot
import com.example.nearbysearch.domain.models.fromDaoList
import com.example.nearbysearch.domain.models.toDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultNearbyLocalSource(
    database : Database
) : NearbyLocalSource {
    private val dao = database.spotQueries
    override suspend fun insert(spot: Spot) = withContext(Dispatchers.IO) {
            dao.insert(spot.toDao())
    }

    override suspend fun insert(spots: List<Spot>) = withContext(Dispatchers.IO) {
        spots.forEach{
            dao.insert(it.toDao())
        }
    }

    override suspend fun delete(id: String)  = withContext(Dispatchers.IO) {
        dao.deleteById(id)
    }

    override suspend fun deleteAll() = withContext(Dispatchers.IO) {
        dao.deleteAll()
    }

    override suspend fun select(id: String): Spot = withContext(Dispatchers.IO) {
        TODO()
    }

    override suspend fun selectByLonLat(longitude: Double, latitude: Double, radius : Int) = withContext(Dispatchers.IO) {
        dao.selectByLonLat(
            radius = radius / 500000.0, lon = longitude, lat = latitude
        ).executeAsList().fromDaoList()

    }

    override suspend fun selectFavorites() = withContext(Dispatchers.IO) {
        dao.selectFavorites().executeAsList().fromDaoList()
    }

    override suspend fun updateFavoriteStatus(id: String, isFavorite: Boolean) = withContext(Dispatchers.IO) {
        dao.updateFavoriteStatus(isFavorite,id)
    }
}