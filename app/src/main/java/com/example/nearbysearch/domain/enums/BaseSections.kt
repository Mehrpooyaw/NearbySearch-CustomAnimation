package com.example.nearbysearch.domain.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.nearbysearch.R

enum class BaseSections(
    val str : String,
    val icon: ImageVector? = null,
    val resource : Int? = null,
    val route : String,
) {
    MAP(str = "Map", resource = R.drawable.ic_round_map_24, route =  "base/map"),
    SEARCH(str = "Search", icon = Icons.Rounded.Search, route = "base/search"),
    FAVORITES(str="Favorites", icon = Icons.Filled.Favorite, route = "base/favorites" ),
    SETTINGS(str = "Settings",icon = Icons.Rounded.Settings, route = "base/settings"),
}