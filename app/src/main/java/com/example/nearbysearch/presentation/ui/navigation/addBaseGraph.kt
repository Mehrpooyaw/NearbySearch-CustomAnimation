package com.example.nearbysearch.presentation.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.nearbysearch.domain.enums.BaseSections
import com.example.nearbysearch.presentation.ui.favorites.FavoritesView
import com.example.nearbysearch.presentation.ui.search.SearchView
import com.example.nearbysearch.presentation.ui.map.MapScreenView
import com.example.nearbysearch.presentation.ui.settings.SettingsView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun NavGraphBuilder.addBaseGraph(
    modifier : Modifier,
    longitude : MutableState<Double?>, latitude : MutableState<Double?>,
    exitApp : () -> Unit,
    isGpsProvided : MutableState<Boolean>,
    turnOnGPS : () -> Unit,
    navigateToMap : () -> Unit,
){
    composable(BaseSections.MAP.route) {
        if (isGpsProvided.value) {
            MapScreenView(
                modifier = modifier,
                longitude = longitude,
                latitude = latitude,
                exitApp = navigateToMap
            )
        }else {
            var isLoadingFinished by remember { mutableStateOf(false)}
            val coroutineScope = rememberCoroutineScope()
            Box(
                modifier
                    .fillMaxSize()
                    .padding(10.dp), contentAlignment = Alignment.Center) {
                if (!isLoadingFinished) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Getting your location...")
                        Spacer(modifier = modifier.size(5.dp))
                        CircularProgressIndicator()
                    }
                    coroutineScope.launch {
                        delay(2500)
                        isLoadingFinished = true
                    }
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("This feature needs your gps to be turned on to work properly. :)")
                        Button(onClick = {
                            turnOnGPS()
                        }) {
                            Text("Turn on")
                        }
                    }
                }
            }
        }
    }
    composable(BaseSections.SEARCH.route) {
        if (isGpsProvided.value) {
            SearchView(
                modifier = modifier,
                longitude = longitude,
                latitude = latitude,
                exitApp = exitApp
            )
        } else {
            var isLoadingFinished by remember { mutableStateOf(false) }
            val coroutineScope = rememberCoroutineScope()
            Box(
                modifier
                    .fillMaxSize()
                    .padding(10.dp), contentAlignment = Alignment.Center
            ) {
                if (!isLoadingFinished) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Getting your location...")
                        Spacer(modifier = modifier.size(5.dp))
                        CircularProgressIndicator()
                    }
                    coroutineScope.launch {
                        delay(2500)
                        isLoadingFinished = true
                    }
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("This feature needs your gps to be turned on to work properly. :)")
                        Button(onClick = {
                            turnOnGPS()
                        }) {
                            Text("Turn on")
                        }
                    }
                }
            }
        }
    }
    composable(BaseSections.FAVORITES.route) {
        FavoritesView(modifier = Modifier, navigateToMapScreen = navigateToMap)

    }

    composable(BaseSections.SETTINGS.route) {
        SettingsView(modifier = modifier)
    }

}
