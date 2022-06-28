package com.example.nearbysearch.presentation.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nearbysearch.repository.NearbySearchRepository
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val repository: NearbySearchRepository
) : ViewModel() {




    fun clearCache(callback : () -> Unit){
        viewModelScope.launch {
            repository.clearCache()
            callback()
        }
    }


}