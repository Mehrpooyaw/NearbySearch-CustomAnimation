package com.example.nearbysearch.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.nearbysearch.domain.enums.BaseSections

object MainDestinations {
    const val BASE_ROUTE = "base"
}

@Composable
fun AppNavGraph(
    modifier : Modifier,
    navController: NavHostController = rememberNavController(),
    longitude : MutableState<Double?>, latitude : MutableState<Double?>,
    startDestination: String = MainDestinations.BASE_ROUTE,
    isGpsProvided : MutableState<Boolean>,
    turnOnGPS : () -> Unit,
    exitApp : () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = startDestination ){
        navigation(
            route = MainDestinations.BASE_ROUTE,
            startDestination = BaseSections.MAP.route
        ){
            addBaseGraph(modifier,longitude,latitude,exitApp,isGpsProvided,turnOnGPS) {
                navController.navigate(BaseSections.MAP.route)
            }
        }
    }
}