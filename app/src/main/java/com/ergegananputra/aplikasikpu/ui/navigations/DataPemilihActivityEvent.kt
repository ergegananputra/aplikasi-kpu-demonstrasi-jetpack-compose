package com.ergegananputra.aplikasikpu.ui.navigations

sealed class DataPemilihActivityEvent {
    data object OnBack : DataPemilihActivityEvent()

    data class OnDetail(val id: Int) : DataPemilihActivityEvent()

    enum class Keys {
        DETAIL_ID
    }
}