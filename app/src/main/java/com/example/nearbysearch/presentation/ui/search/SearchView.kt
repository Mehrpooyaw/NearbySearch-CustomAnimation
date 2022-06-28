package com.example.nearbysearch.presentation.ui.search

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.nearbysearch.domain.models.Spot
import com.example.nearbysearch.presentation.ui.map.MapStateEvent
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import org.koin.androidx.compose.getViewModel


@Composable
fun SearchView(
    modifier : Modifier,
    longitude : MutableState<Double?>,
    latitude : MutableState<Double?>,
    exitApp : () -> Unit
) {
    val vm = getViewModel<SearchViewModel>()
    var radius by remember {
        mutableStateOf(20000f)
    }
    var showMoreInfo by remember { mutableStateOf(false) }
    vm.longitude = longitude
    vm.latitude = latitude
    vm.radius.value = radius
    var spot by remember { mutableStateOf<Spot?>(null)}
    BackHandler() {
        if (showMoreInfo){
            showMoreInfo = false
        }else{
            exitApp()

        }
    }

    Box(
        modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        LazyColumn(modifier = modifier.fillMaxSize(), contentPadding = PaddingValues(bottom = 100.dp)){
            item {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Box(modifier = modifier
                        .weight(1f)
                        .padding(20.dp)){
                        CustomLatLonCard(modifier = modifier, key ="Longitude" , value ="%.5f".format(longitude.value))
                    }
                    Box(modifier = modifier
                        .weight(1f)
                        .padding(20.dp)){
                        CustomLatLonCard(modifier = modifier, key ="Latitude" , value ="%.5f".format(latitude.value))
                    }


                }
            }
            item {
                Column(
                    modifier = modifier.padding(5.dp)
                ) {
                    Slider(value = radius, onValueChange ={
                        radius = it
                    }, steps = 100, valueRange = 100f..20000f, onValueChangeFinished = {
                        vm.onTriggerEvent(SearchStateEvent.GetSpots(longitude.value!!,latitude.value!!,radius.toInt()))
                    }
                    )
                    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("100")
                        Text("Radius: $radius")
                        Text("20000")
                    }
                }
            }
            item {
                Divider(thickness = 2.dp)
            }
            if (vm.spots.isNotEmpty()){
                items(vm.spots){
                     SpotView(modifier = modifier, spot = it, onMoreClicked = {
                         showMoreInfo = true
                         spot = it
                     } ) { bool ->
                         vm.onTriggerEvent(SearchStateEvent.UpdateFavoriteStatus(
                             id = it.id?:"", isFavorite = bool
                         ))

                     }


                }
            }else{
                item {
                    LinearProgressIndicator(
                        modifier = modifier.fillMaxWidth()
                    )
                }
            }


        }
        AnimatedVisibility(visible = showMoreInfo, enter = fadeIn(), exit = fadeOut()) {
            spot?.let {
                SpotDetailView(modifier = modifier, spot = it, onFavoriteChanged ={ bool ->
                    spot?.id?.let { id ->
                        SearchStateEvent.UpdateFavoriteStatus(
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

@Composable
fun SpotView(
    modifier : Modifier,
    spot : Spot,
    onMoreClicked : () -> Unit,
    updateFavoriteStatus : (Boolean) -> Unit,
){
    var isFavorite by rememberSaveable {
        mutableStateOf(spot.isFavorite)
    }
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
                spot.poi?.name?.let { Text(it,modifier = modifier.fillMaxWidth(0.7f)) }
                IconButton(onClick = {
                    isFavorite = !isFavorite
                    updateFavoriteStatus(isFavorite)
                }) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                        contentDescription = null,
                        tint = Color.Red
                    )
                }
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
            TextButton(onClick = { onMoreClicked() }) {
                Text("More")
            }
        }
    }
}


@Composable
fun SpotDetailView(
    modifier : Modifier,
    spot : Spot,
    onFavoriteChanged : (Boolean) -> Unit,
    onCloseClicked : () -> Unit
){
    var isFavorite by remember {
        mutableStateOf(spot.isFavorite)
    }
    Card(
        modifier = modifier
            .padding(20.dp)
            .fillMaxSize(),
        elevation = 10.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier
                .padding(10.dp)
                .verticalScroll(rememberScrollState())) {
            Row(
                modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = {
                    onCloseClicked()
                }) {
                    Text("Close")
                }
                IconButton(onClick = {
                    isFavorite = !isFavorite
                    onFavoriteChanged(isFavorite)
                }) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                        contentDescription = null,
                        tint = Color.Red
                    )
                }

            }
                spot.poi?.name?.let { Text(it, modifier.fillMaxWidth(0.8f)) }

                if (spot.address != null) {
                    spot.address.country?.let { Text(it) }
                    spot.address.countrySubdivisionName?.let { Text(it) }
                    spot.address.countrySecondarySubdivision?.let { Text(it) }
                    spot.address.municipality?.let { Text(it) }
                    spot.address.municipalitySubdivision?.let { Text(it) }
                    spot.address.postalCode?.let {
                    Row() {
                        Text("Postal Code: ")
                        Text(it) }
                    }
                    Text("Free form address")
                    spot.address.freeformAddress?.let { Text(it) }
                    spot.info?.let { Text(it) }
                    spot.poi?.phone?.let { Text(it) }
                    spot.poi?.url?.let { Text(it) }
                }


        }
    }


}


@Composable
fun CustomLatLonCard(
    modifier : Modifier,
    key : String,
    value : String
){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = 10.dp,
        shape = RoundedCornerShape(10.dp)
    ){
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ){
            Text(value, style = MaterialTheme.typography.h6)
        }
    }
        Spacer(modifier = modifier.height(10.dp))
        Text(key, style = MaterialTheme.typography.subtitle1)
    }
}