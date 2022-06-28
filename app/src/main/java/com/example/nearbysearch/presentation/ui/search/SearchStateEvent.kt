package com.example.nearbysearch.presentation.ui.search

sealed class SearchStateEvent {
    data class GetSpots(val lon : Double, val lat : Double,val radius : Int) : SearchStateEvent()
    data class UpdateFavoriteStatus(val id : String, val isFavorite : Boolean) : SearchStateEvent()
}