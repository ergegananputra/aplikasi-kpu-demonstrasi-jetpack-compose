package com.ergegananputra.aplikasikpu.ui.presentations.maps

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class MapsViewModel : ViewModel() {

    private val _state = MutableStateFlow(MapsState())
    val state = _state.asStateFlow()


    fun updateCurrentLocation(latitude: Double, longitude: Double, context: Context) {
        viewModelScope.launch {
            val address = getAddressFromLatLng(context, LatLng(latitude, longitude))
            _state.update {
                it.copy(
                    address = address,
                    latitude = latitude,
                    longitude = longitude
                )
            }

        }

    }

    private suspend inline fun getAddressFromLatLng(context: Context, latLng: LatLng): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = withContext(Dispatchers.IO) {
            geocoder.getFromLocation(
                latLng.latitude,
                latLng.longitude,
                1
            )
        }

        Log.d("MapsViewModel", "getAddressFromLatLng: $addresses")

        if (addresses != null) {
            return if (addresses.isNotEmpty()) {
                addresses[0].getAddressLine(0)
            } else {
                "Belum ada alamat"
            }
        }

        return "Belum ada alamat"
    }
}