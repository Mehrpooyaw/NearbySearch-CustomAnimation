package com.example.nearbysearch.presentation.ui.settings

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.getViewModel

@Composable
fun SettingsView(modifier : Modifier){
    val vm = getViewModel<SettingsViewModel>()
    val context = LocalContext.current
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 80.dp)
            .padding(horizontal = 20.dp), contentAlignment = Alignment.BottomCenter
    ){
        Button(onClick = {
            vm.clearCache(){
                Toast.makeText(context,"Done!",Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Clear cache")
        }
    }
}