package com.example.nearbysearch.presentation.ui.favorites

sealed class FavoritesStateEvent {
   data class GetFavorites(val none : Nothing? = null) : FavoritesStateEvent()
}