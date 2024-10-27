package com.ergegananputra.aplikasikpu.ui.presentations.formentry

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ergegananputra.aplikasikpu.di.AppContainer
import com.ergegananputra.aplikasikpu.domain.entities.room.DataPeserta
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class FormEntryViewModel(
    private val appContainer: AppContainer
) : ViewModel() {
    private val _state = MutableStateFlow(FormEntryState())
    private val _capturedPhoto = MutableStateFlow<Uri?>(null)

    val state = combine(_state, _capturedPhoto) { state, capturedPhoto ->
        state.copy(capturedPhoto = capturedPhoto)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(2000L), FormEntryState())



    fun updateCapturedPhoto(uri: Uri) {
        _capturedPhoto.value = uri
    }

    fun afterNikChanged(nik: String) {
        if (nik.length > 16) {
            return
        }

        _state.update {
            it.copy(
                nik = nik
                    .replace(" ", "")
                    .replace("-", "")
                    .replace(".", "")
                    .replace(",", "")
            )
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
        _state.update {
            it.copy(isLoading = true)
        }

        validation {
            viewModelScope.launch(Dispatchers.IO) {
                postPeserta {
                    fetchData {
                        _state.update {
                            it.copy(
                                isDone = true
                            )
                        }
                    }
                }
            }
        }

        _state.update {
            it.copy(isLoading = false)
        }
    }

    private fun fetchData(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                appContainer.dataPesertaRepository.downloadDataPeserta()
            }
            when (result) {
                is com.ergegananputra.aplikasikpu.utils.Result.Error -> {
                    displayErrorMessage(result.message ?: "Gagal mengunduh data peserta")
                }
                is com.ergegananputra.aplikasikpu.utils.Result.Success<*> -> {
                    onSuccess()
                }
            }
        }
    }

    fun displayErrorMessage(message: String) {
        _state.update {
            it.copy(errorMessage = message)
        }
    }

    fun clearErrorMessage() {
        _state.update {
            it.copy(errorMessage = null)
        }
    }

    private fun validation(
        onSuccess: () -> Unit
    ) {
        val uriImage = _capturedPhoto.value
        var state = _state.value

        if (state.capturedPhoto != uriImage) {
            state = state.copy(capturedPhoto = uriImage)
        }

        val capturedPhoto = state.capturedPhoto.validated("Foto tidak boleh kosong") ?: return
        val nik = state.nik.validated("NIK tidak boleh kosong") ?: return
        val namaLengkap = state.namaLengkap.validated("Nama lengkap tidak boleh kosong") ?: return
        val nomorHandphone = state.nomorHandphone.validated("Nomor handphone tidak boleh kosong") ?: return
        val gender = state.gender.validated("Jenis kelamin tidak boleh kosong") ?: return
        val tanggalPendataan = state.tanggalPendataan.validated("Tanggal pendataan tidak boleh kosong") ?: return
        val alamat = state.alamat.validated("Alamat tidak boleh kosong") ?: return

        _state.update {
            it.copy(
                nik = nik,
                namaLengkap = namaLengkap,
                nomorHandphone = nomorHandphone,
                gender = gender,
                tanggalPendataan = tanggalPendataan,
                alamat = alamat,
                capturedPhoto = capturedPhoto
            )
        }

        onSuccess()
    }

    private fun Uri?.validated(errMsg: String): Uri? {
        Log.d("FormEntryViewModel", "Uri: $this")
        return this ?: run {
            displayErrorMessage(errMsg)
            null
        }
    }

    private fun Int.validated(errMsg: String): Int? {
        if (this == 0) {
            displayErrorMessage(errMsg)
            return null
        } else {
            return this
        }
    }

    private fun Long.validated(errMsg: String): Long? {
        if (this <= 0L) {
            displayErrorMessage(errMsg)
            return null
        } else {
            return this
        }
    }

    private fun String.validated(errMsg: String): String? {
        return this.trim().ifEmpty {
            displayErrorMessage(errMsg)
            null
        }
    }


    private suspend fun postPeserta(
        onSuccess: () -> Unit
    ) {
        val state = _state.value
        val nik = state.nik
        val namaLengkap = state.namaLengkap
        val nomorHandphone = state.nomorHandphone
        val gender = state.gender
        val tanggalPendataan = state.tanggalPendataan
        val alamat = state.alamat

        val capturedPhoto = state.capturedPhoto

        val imageUri = _capturedPhoto.value

        val fileUri = if (capturedPhoto != imageUri) {
            imageUri
        } else {
            capturedPhoto
        }

        if (fileUri == null) {
            displayErrorMessage("Foto tidak boleh kosong")
            return
        }

        val dataPeserta = DataPeserta(
            nik = nik,
            namaLengkap = namaLengkap,
            nomorHandphone = nomorHandphone,
            gender = gender,
            tanggalPendataan = tanggalPendataan,
            alamat = alamat,
            imageUrl = null
        )

        val file = uriToFile(appContainer.context, fileUri)

        val result = try {
            appContainer.dataPesertaRepository.uploadDataPeserta(
                dataPeserta = dataPeserta,
                imageFile = file
            )
        } catch (e: Exception) {
            Log.e("FormEntryViewModel", "Error: ${e.message}")
            displayErrorMessage("Gagal mengunggah data peserta")
        }


        when (result) {
            is com.ergegananputra.aplikasikpu.utils.Result.Error -> {
                displayErrorMessage(result.message ?: "Gagal mengunggah data peserta")
            }
            is com.ergegananputra.aplikasikpu.utils.Result.Success<*> -> {
                displayErrorMessage("Berhasil mengunggah data peserta")
                onSuccess()
            }
        }
    }

    private fun uriToFile(context: Context, uri: Uri): File {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val file = File(context.cacheDir, "temp_image")
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
        return file
    }


}