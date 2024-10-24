package com.ergegananputra.aplikasikpu.ui.navigations

sealed class FormEntryActivityEvent {
    data object OnBack : FormEntryActivityEvent()
    data object GoToMaps : FormEntryActivityEvent()
}