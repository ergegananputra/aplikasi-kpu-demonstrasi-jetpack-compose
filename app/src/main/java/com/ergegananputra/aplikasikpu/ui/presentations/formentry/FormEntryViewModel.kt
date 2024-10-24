package com.ergegananputra.aplikasikpu.ui.presentations.formentry

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FormEntryViewModel : ViewModel() {
    private val _state = MutableStateFlow(FormEntryState())
    val state = _state.asStateFlow()

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

    fun afterImageFilenameChanged(imageFilename: String) {
        _state.update {
            it.copy(imageFilename = imageFilename)
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


}