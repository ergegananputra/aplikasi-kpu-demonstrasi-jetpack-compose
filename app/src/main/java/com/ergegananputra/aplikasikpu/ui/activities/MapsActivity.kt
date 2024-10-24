package com.ergegananputra.aplikasikpu.ui.activities

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ergegananputra.aplikasikpu.ui.navigations.FormEntryActivityEvent
import com.ergegananputra.aplikasikpu.ui.navigations.FormEntryActivityEvent.Companion.ADDRESS
import com.ergegananputra.aplikasikpu.ui.presentations.maps.MapsScreen
import com.ergegananputra.aplikasikpu.ui.presentations.maps.MapsViewModel
import com.ergegananputra.aplikasikpu.ui.theme.AplikasiKPUTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng


class MapsActivity : ComponentActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var mapsViewModel: MapsViewModel

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        enableEdgeToEdge()
        setContent {
            mapsViewModel = viewModel(MapsViewModel::class)
            val locationPermissionState = rememberMultiplePermissionsState(
                permissions = listOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )

            LaunchedEffect(Unit) {
                locationPermissionState.launchMultiplePermissionRequest()
            }

            if (locationPermissionState.allPermissionsGranted) {
                getCurrentLocation()
            }


            AplikasiKPUTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MapsScreen(
                        mainEvent = ::onEvent,
                        viewModel = mapsViewModel,
                        modifier = Modifier.padding(innerPadding),
                    )

                }
            }
        }
    }

    private fun onEvent(event: FormEntryActivityEvent) {
        when (event) {
            is FormEntryActivityEvent.BackToForm -> {
                activitySuccess(event.address)
            }
            FormEntryActivityEvent.GetCurrentLocation -> {
                getCurrentLocation()
            }
            is FormEntryActivityEvent.UpdateLocation -> {
                updateLocation(event.latLng)
            }
            else -> {}
        }
    }

    private fun activitySuccess(address: String) {
        setResult(RESULT_OK, intent.putExtra(ADDRESS, address))
        finish()
    }

    private fun getCurrentLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                Log.d("MapsActivity", "getCurrentLocation: $location")
                if (location != null) {
                    mapsViewModel.updateCurrentLocation(
                        latitude = location.latitude,
                        longitude = location.longitude,
                        context = this@MapsActivity,
                    )
                }
            }
    }

    private fun updateLocation(latLng: LatLng) {
        mapsViewModel.updateCurrentLocation(
            latitude = latLng.latitude,
            longitude = latLng.longitude,
            context = this@MapsActivity
        )
    }
}



