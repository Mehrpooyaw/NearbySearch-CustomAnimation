package com.example.nearbysearch.presentation.ui.favorites

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nearbysearch.domain.models.Spot
import com.example.nearbysearch.repository.NearbySearchRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class FavoritesViewModel(
    private val repository: NearbySearchRepository
) : ViewModel() {
    var loading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    val spots = mutableStateListOf<Spot>()


    fun onTriggerEvent(event: FavoritesStateEvent){
        when (event){
            is FavoritesStateEvent.GetFavorites -> {
                getFavorites()
            }
        }
    }

    private fun getFavorites(){
        repository.getFavorites().onEach {
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

