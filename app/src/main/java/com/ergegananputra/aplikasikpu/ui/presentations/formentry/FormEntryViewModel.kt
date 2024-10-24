package com.ergegananputra.aplikasikpu.ui.presentations.formentry

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FormEntryViewModel : ViewModel() {
    private val _state = MutableStateFlow(FormEntryState())
    private val _capturedPhoto = MutableStateFlow<Uri?>(null)

    val state = combine(_state, _capturedPhoto) { state, capturedPhoto ->
        state.copy(capturedPhoto = capturedPhoto)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(2000L), FormEntryState())



    fun updateCapturedPhoto(uri: Uri) {
        _capturedPhoto.value = uri
    }

    fun afterNikChanged(nik: String) {
        _state.update {
            it.copy(nik = nik)
        }
    }

    fun afterNamaLengkapChanged(namaLengkap: String) {
        _state.update {
            it.copy(namaLengkap = namaLengkap)
        }
    }

    fun afterNomorHandphoneChanged(nomorHandphone: String) {
        _state.update {
            it.copy(nomorHandphone = nomorHandphone)
        }
    }

    fun onGenderChanged(value: Int) {
        _state.update {
            it.copy(gender = value)
        }
    }

    fun changeTanggalPendataanChanged(tanggalPendataan: Long) {
        _state.update {
            it.copy(tanggalPendataan = tanggalPendataan)
        }
    }

    fun afterAlamatChanged(alamat: String) {
        _state.update {
            it.copy(alamat = alamat)
        }
    }


    fun showDatePicker() {
        _state.update {
            it.copy(isModalDatePickerShow = true)
        }
    }

    fun dismissDatePicker() {
        _state.update {
            it.copy(isModalDatePickerShow = false)
        }
    }

    fun updateAddress(address: String?) {
        _state.update {
            it.copy(alamat = address ?: "")
        }
    }

    fun saveForm() {
        displayErrorMessage("Belum diimplementasikan")
        _state.update {
            it.copy(isDone = true)
        }
    }

    private fun displayErrorMessage(message: String) {
        _state.update {
            it.copy(errorMessage = message)
        }
    }

    fun clearErrorMessage() {
        _state.update {
            it.copy(errorMessage = null)
        }
    }



}