package com.example.nearbysearch.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.nearbysearch.presentation.ui.components.CustomScaffold
import com.example.nearbysearch.presentation.ui.navigation.AppNavGraph
import com.example.nearbysearch.presentation.ui.theme.NearbySearchTheme

@Composable
fun NearbySearchApp(
    modifier : Modifier, longitude : MutableState<Double?>,
    latitude : MutableState<Double?>,exitApp : () -> Unit,isGpsProvided : MutableState<Boolean>,
    turnOnGPS : () -> Unit

    ) {
    val navController = rememberNavController()
    NearbySearchTheme() {
        CustomScaffold(modifier = modifier, navController = navController) {
            AppNavGraph(modifier = modifier, navController = navController,longitude = longitude, latitude = latitude,
                exitApp = exitApp,isGpsProvided = isGpsProvided, turnOnGPS = turnOnGPS)
        }

    }
}