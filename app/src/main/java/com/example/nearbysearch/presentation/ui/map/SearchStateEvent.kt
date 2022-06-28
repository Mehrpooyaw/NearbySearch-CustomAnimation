package com.example.nearbysearch.presentation.ui.map

sealed class MapStateEvent {
    data class GetSpots(val lon : Double, val lat : Double,val radius : Int) : MapStateEvent()
    data class UpdateFavoriteStatus(val id : String, val isFavorite : Boolean) : MapStateEvent()
}