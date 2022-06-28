package com.example.nearbysearch.presentation.ui.map

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nearbysearch.domain.models.Spot
import com.example.nearbysearch.presentation.ui.search.SearchStateEvent
import com.example.nearbysearch.repository.NearbySearchRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MapViewModel(
    private val repository: NearbySearchRepository
) : ViewModel() {
    var loading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    val spots = mutableStateListOf<Spot>()
    var longitude = mutableStateOf<Double?>(0.0)
    var latitude = mutableStateOf<Double?>(0.0)
    init {
        snapshotFlow { longitude.value }.onEach {
            if (it != 0.0){
                onTriggerEvent(
                    MapStateEvent.GetSpots(
                    lon = longitude.value!!, lat = latitude.value!!, radius = 50000)
                )}
        }.launchIn(CoroutineScope(Dispatchers.Main))
    }

    fun onTriggerEvent(event: MapStateEvent){
        when (event){
            is MapStateEvent.GetSpots -> {
                getSpots(event.lon,event.lat,event.radius)
            }
            is MapStateEvent.UpdateFavoriteStatus -> {
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