package com.example.nearbysearch.domain.models

import com.example.nearbysearch.network.model.search_result.*
import com.example.sqldelight.nearbysearch.SpotDao

data class Spot(
    val address: Address? = Address(),
    val dist: Double? = 0.0,
    val entryPoints: List<EntryPoint>? = listOf(),
    val id: String? = "",
    val info: String? = "",
    val poi: Poi? = Poi(),
    val position: PositionX? = PositionX(),
    val score: Double? = 0.0,
    val type: String? = "",
    val viewport: Viewport? = Viewport(),
    var isFavorite : Boolean = false
)

fun SpotDao.fromDao() : Spot {
    return Spot(
        address = address,
        dist = dist,
        entryPoints =entryPoints ,
        id = id,
        info = info,
        position = PositionX(lat = latitude, lon = longitude),
        score = score,
        type = type,
        viewport = viewport,
        isFavorite = isFavorite,
        poi = poi

    )
}

fun List<SpotDao>.fromDaoList() : List<Spot> {
    return map {
        it.fromDao()
    }
}


fun List<Spot>.toDao() : List<SpotDao>{
    return map {
        it.toDao()
    }
}


fun Spot.toDao() : SpotDao {
    return SpotDao(
        address = address,
        dist = dist,
        entryPoints =entryPoints ,
        id = id ?: "",
        info = info,
        longitude = position?.lon,
        latitude = position?.lat,
        score = score,
        type = type,
        viewport = viewport,
        isFavorite = isFavorite,
        poi = poi
    )
}


