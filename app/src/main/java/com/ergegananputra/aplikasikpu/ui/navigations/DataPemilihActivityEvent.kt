package com.ergegananputra.aplikasikpu.ui.navigations

sealed class DataPemilihActivityEvent {
    data object OnBack : DataPemilihActivityEvent()
}