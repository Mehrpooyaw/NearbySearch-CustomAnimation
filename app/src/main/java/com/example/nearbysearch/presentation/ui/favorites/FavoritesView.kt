package com.example.nearbysearch.presentation.ui.favorites

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nearbysearch.domain.models.Spot
import org.koin.androidx.compose.getViewModel

@Composable
fun FavoritesView(
    modifier : Modifier,
    navigateToMapScreen : () -> Unit
) {
    val vm = getViewModel<FavoritesViewModel>()
    BackHandler() {
        navigateToMapScreen()
    }
    LaunchedEffect(key1 = "get_favorites",  ){
        vm.onTriggerEvent(FavoritesStateEvent.GetFavorites())
    }
    LazyColumn(contentPadding = PaddingValues(bottom = 100.dp)){
        items(vm.spots){
            FavoriteSpotView(modifier = modifier, spot = it )


        }
    }
}

@Composable
fun FavoriteSpotView(
    modifier : Modifier,
    spot : Spot,
){

    Card(
        modifier = modifier
            .padding(7.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        elevation = 5.dp
    ){
        Column(modifier= modifier
            .fillMaxWidth()
            .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically

            ) {
                spot.poi?.name?.let { Text(it,modifier = modifier.fillMaxWidth(1f)) }
            }
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                spot.poi?.categories?.get(0)?.let { Text(it,modifier.weight(1f)) }
                spot.address?.localName?.let { Text(it,modifier.weight(1f)) }
                spot.address?.country?.let { Text(it,modifier.weight(1f)) }
            }

        }
    }
}