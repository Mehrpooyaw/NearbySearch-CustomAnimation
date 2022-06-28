package com.example.nearbysearch.presentation.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import com.example.nearbysearch.domain.enums.BaseSections

@Composable
fun CustomScaffold(
    modifier : Modifier,
    navController: NavHostController,
    content : @Composable (PaddingValues) -> Unit,

    ){
    Scaffold(
        bottomBar = {
            BottomNavBar(modifier = modifier,tabs = BaseSections.values(), navController = navController)
        },

    ) {

        content(it)

    }

}
