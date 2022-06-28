package com.example.nearbysearch.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.nearbysearch.domain.models.Spot
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapView(
    modifier : Modifier,
    currentLon : Double,currentLat : Double,
    spots : List<Spot>,
    onSpotInfoClicked : (Spot) -> Unit
){
    val currentLocation = LatLng(currentLat,currentLon)
    val cameraPositionState = rememberCameraPositionState{
        position = CameraPosition.fromLatLngZoom(currentLocation, 11f)
    }
    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(currentLocation),
            title = "You",
            icon = BitmapDescriptorFactory.defaultMarker(122f)
        )
        spots.forEach {
            if (it.position?.lon!= null && it.position.lat != null) {
                Marker(
                    state = MarkerState(LatLng(it.position.lat, it.position.lon)),
                    title = it.poi?.name,
                    snippet = it.poi?.categories?.get(0),
                    onInfoWindowClick = { marker ->
                        onSpotInfoClicked(it)
                    }
                )
            }
        }

    }
}
