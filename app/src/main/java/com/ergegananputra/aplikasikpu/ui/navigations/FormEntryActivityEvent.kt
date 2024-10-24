package com.ergegananputra.aplikasikpu.ui.navigations

import com.google.android.gms.maps.model.LatLng

sealed class FormEntryActivityEvent {
    data object OnBack : FormEntryActivityEvent()
    data object GoToMaps : FormEntryActivityEvent()

    data object LaunchCamera : FormEntryActivityEvent()
    data object UploadPhoto : FormEntryActivityEvent()

    data object GetCurrentLocation : FormEntryActivityEvent()

    data class UpdateLocation(
        val latLng : LatLng
    ) : FormEntryActivityEvent()

    data class BackToForm(
        val address: String
    ) : FormEntryActivityEvent()

    companion object {
        const val ADDRESS = "address_key_intent"
    }
}