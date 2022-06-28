package com.example.nearbysearch.presentation

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import com.example.nearbysearch.presentation.ui.NearbySearchApp
import com.example.nearbysearch.presentation.ui.theme.NearbySearchTheme
import com.google.android.gms.location.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentLongitude = mutableStateOf<Double?>(0.0)
        val currentLatitude = mutableStateOf<Double?>(0.0)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest.create().apply {
            interval = TimeUnit.SECONDS.toMillis(1)
            fastestInterval = TimeUnit.SECONDS.toMillis(1)
            maxWaitTime = TimeUnit.SECONDS.toMillis(5)
            priority = Priority.PRIORITY_HIGH_ACCURACY
        }
        isPermissionsGranted(this)
        turnOnGps()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                locationResult.lastLocation.let {
                    if (it != null) {
                        currentLongitude.value = it.longitude
                        currentLatitude.value = it.latitude
                    }
                }
            }
        }
        val isGpsProvided = mutableStateOf(false)
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())

        setContent {
            currentLatitude.value?.let {
                if (currentLatitude.value != 0.0) {
                    isGpsProvided.value = true
                }
            }
            NearbySearchTheme {
                NearbySearchApp(
                    modifier = Modifier,
                    longitude = currentLongitude,
                    latitude = currentLatitude,
                    exitApp = {
                        onBackPressed()
                    },
                    isGpsProvided = isGpsProvided,
                    turnOnGPS = { turnOnGps() }
                )
            }
        }
    }
    private fun turnOnGps() {
        val lm = getSystemService(LOCATION_SERVICE) as LocationManager
        var gpsEnabled = false
        var networkEnabled = false
        try {
            gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (!gpsEnabled && !networkEnabled) {
            AlertDialog.Builder(this@MainActivity)
                .setMessage("Turn on your GPS to continue...")
                .setPositiveButton("Turn on",
                    DialogInterface.OnClickListener { paramDialogInterface, paramInt ->
                        startActivity(
                            Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS
                            )
                        )
                    })
                .setNegativeButton("Cancel", null)
                .show()
        }
    }
}


fun isPermissionsGranted(activity : MainActivity)  {
    val locationPermissionRequest = activity.registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(android.Manifest.permission.ACCESS_FINE_LOCATION, false) -> {


            }
            permissions.getOrDefault(android.Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
            }
            else -> {
                Log.d("AppDebug", "isPermissionsGranted: NO LOCATION!")
            }
        }
    }
    locationPermissionRequest.launch(arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION))
}

