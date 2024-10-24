package com.ergegananputra.aplikasikpu.ui.presentations.maps

import com.google.android.gms.maps.model.LatLng

data class MapsState(
    val address : String = "Belum ada alamat",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
) {
    val currentLocation: LatLng
        get() = LatLng(latitude, longitude)
}
