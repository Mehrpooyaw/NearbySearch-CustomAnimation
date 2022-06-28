package com.example.nearbysearch.presentation.ui.map

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nearbysearch.domain.models.Spot
import com.example.nearbysearch.presentation.ui.components.MapView
import com.example.nearbysearch.presentation.ui.search.SpotDetailView
import org.koin.androidx.compose.getViewModel

@Composable
fun MapScreenView(
    modifier : Modifier,
    longitude : MutableState<Double?>,
    latitude : MutableState<Double?>,
    exitApp : () -> Unit
) {
    val vm = getViewModel<MapViewModel>()
    var showMoreInfo by remember { mutableStateOf(false) }
    var spot by remember {mutableStateOf<Spot?>(null)}
    vm.longitude = longitude
    vm.latitude = latitude
    BackHandler() {
        if (showMoreInfo){
            showMoreInfo = false
        }else{
            exitApp()
        }
    }
    Box(modifier.fillMaxSize(), Alignment.Center) {
        if (latitude.value != 0.0) {
            MapView(
                modifier = modifier,
                currentLon = longitude.value!!,
                currentLat = latitude.value!!,
                spots = vm.spots,
                onSpotInfoClicked = {
                    spot = it
                    showMoreInfo = true
                }
            )
        }else{
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
                Spacer(modifier = modifier.size(10.dp))
                Text("Getting your location...")
            }
        }
        AnimatedVisibility(visible = showMoreInfo, enter = fadeIn(), exit = fadeOut()) {
            spot?.let {
                SpotDetailView(modifier = modifier, spot = it, onFavoriteChanged ={ bool ->
                    spot?.id?.let { id ->
                        MapStateEvent.UpdateFavoriteStatus(
                            id = id, isFavorite = bool
                        )
                    }?.let { vm.onTriggerEvent(it) }
                } ) {
                    showMoreInfo = false
                }
            }

        }
    }

}