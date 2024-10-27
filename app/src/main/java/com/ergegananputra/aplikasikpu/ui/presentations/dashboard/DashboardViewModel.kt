package com.ergegananputra.aplikasikpu.ui.presentations.dashboard

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DashboardViewModel : ViewModel() {
    private val _state = MutableStateFlow(DashboardState())
    val state = _state.asStateFlow()

    fun setAppVersion(appVersion: String) {
        _state.update {
            it.copy(appVersion = appVersion)
        }
    }

    private fun showDialog() {
        _state.update {
            it.copy(isDialogOpen = true)
        }
    }

    fun onButtonLogoutClick() {
        showDialog()
    }

    fun onDismissDialog() {
        _state.update {
            it.copy(isDialogOpen = false)
        }
    }

    fun displayErrorMessage(message: String) {
        _state.update {
            it.copy(
                errorMessage = message
            )
        }
    }

    fun clearErrorMessage() {
        _state.update {
            it.copy(
                errorMessage = null
            )
        }
    }


}