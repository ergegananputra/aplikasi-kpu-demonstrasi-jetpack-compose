package com.ergegananputra.aplikasikpu.ui.navigations

sealed class MainActivityEvent {
    data object GoToInformasi : MainActivityEvent()
    data object GoToFormEntry : MainActivityEvent()
    data object GoToLihatData : MainActivityEvent()
    data object LogOut : MainActivityEvent()
}