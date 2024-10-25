package com.ergegananputra.aplikasikpu.ui.presentations.lihatdata

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ergegananputra.aplikasikpu.domain.AppContainer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DataPemilihViewModel(
    private val appContainer: AppContainer
) : ViewModel() {

    private val _dataPesertaList = appContainer
        .dataPesertaRepository
        .getDataPeserta()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(DataPemilihState())

    val state = combine(_dataPesertaList, _state) { dataPesertaList, state ->
        state.copy(
            dataPesertaList = dataPesertaList
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), DataPemilihState())


    fun fetchDataPeserta() {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    appContainer.dataPesertaRepository.downloadDataPeserta()
                }
            } catch (thr: Throwable) {
                Log.e("DataPemilihViewModel", "fetchDataPeserta: $thr")
                displayErrorMessage(thr.message ?: "Sistem tidak dapat mengambil data peserta")
            } catch (e: Exception) {
                Log.e("DataPemilihViewModel", "fetchDataPeserta: $e")
                displayErrorMessage(e.message ?: "Sistem tidak dapat mengambil data peserta")
            }
        }
    }


    private fun displayErrorMessage(errorMessage: String) {
        _state.update {
            it.copy(
                errorMessage = errorMessage
            )
        }
    }

}