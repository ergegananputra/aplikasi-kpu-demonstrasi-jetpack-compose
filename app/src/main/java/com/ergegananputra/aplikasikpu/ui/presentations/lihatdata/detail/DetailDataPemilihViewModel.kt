package com.ergegananputra.aplikasikpu.ui.presentations.lihatdata.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ergegananputra.aplikasikpu.di.AppContainer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailDataPemilihViewModel(
    private val appContainer: AppContainer,
    id: Int
) : ViewModel() {

    private val _state = MutableStateFlow(DetailDataPemilihState(id))

    fun fetchDetailData(id: Int) {
        viewModelScope.launch {
            val dataPeserta = appContainer.dataPesertaRepository.getDataPesertaById(id)

            dataPeserta.firstOrNull()?.let { lastDataPeserta ->
                _state.update {
                    it.copy(
                        data = lastDataPeserta
                    )
                }
            }


        }
    }


    val state = _state.asStateFlow()

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

    fun deleteData(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = appContainer.dataPesertaRepository.deleteDataPesertaById(id)

            when(result) {

                is com.ergegananputra.aplikasikpu.utils.Result.Error -> {
                    displayErrorMessage(result.message ?: "Terjadi kesalahan dalam menghapus data")
                    _state.update {
                        it.copy(
                            isDeleteConfirmationOpen = false
                        )
                    }
                }
                is com.ergegananputra.aplikasikpu.utils.Result.Success<*> -> {
                    _state.update {
                        it.copy(
                            isDone = true,
                            isDeleteConfirmationOpen = false
                        )
                    }
                }
            }

        }

    }

    fun showDeleteConfirmation() {
        _state.update {
            it.copy(
                isDeleteConfirmationOpen = true
            )
        }
    }

    fun hideDeleteConfirmation() {
        _state.update {
            it.copy(
                isDeleteConfirmationOpen = false
            )
        }
    }
}