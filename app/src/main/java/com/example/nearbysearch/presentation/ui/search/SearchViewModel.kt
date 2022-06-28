package com.example.nearbysearch.presentation.ui.search

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nearbysearch.domain.models.Spot
import com.example.nearbysearch.repository.NearbySearchRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: NearbySearchRepository
) :ViewModel() {
    var loading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    val spots = mutableStateListOf<Spot>()
    var longitude = mutableStateOf<Double?>(0.0)
    var latitude = mutableStateOf<Double?>(0.0)
    val radius = mutableStateOf(100f)
    init {
        snapshotFlow { longitude.value }.onEach {
            if (it != 0.0){
        onTriggerEvent(SearchStateEvent.GetSpots(
            lon = longitude.value!!, lat = latitude.value!!, radius = radius.value.toInt())
        )}
        }.launchIn(CoroutineScope(Dispatchers.Main))
    }

    fun onTriggerEvent(event: SearchStateEvent){
        when (event){
            is SearchStateEvent.GetSpots -> {
                getSpots(event.lon,event.lat,event.radius)
            }
            is SearchStateEvent.UpdateFavoriteStatus -> {
                    updateFavoriteStatus(event.id,event.isFavorite)
            }
        }
    }

    private fun updateFavoriteStatus(id : String, isFavorite : Boolean){
        viewModelScope.launch {
            repository.updateFavoriteStatus(id, isFavorite)
        }
    }

    private fun getSpots(lon : Double, lat : Double, radius : Int){
            repository.getSpots(lon,lat,radius).onEach {
                loading = it.loading
                it.data?.let { list ->
                    spots.clear()
                    spots.addAll(list)
                }
                it.error?.let { error ->
                    errorMessage = error
                }
            }.launchIn(viewModelScope)
    }
}