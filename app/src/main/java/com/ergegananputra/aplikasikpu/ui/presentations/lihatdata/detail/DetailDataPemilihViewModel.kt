package com.ergegananputra.aplikasikpu.ui.presentations.lihatdata.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ergegananputra.aplikasikpu.di.AppContainer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

class DetailDataPemilihViewModel(
    private val appContainer: AppContainer,
    id: Int
) : ViewModel() {

    private val _state = MutableStateFlow(DetailDataPemilihState(id))

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _dataPeserta = _state.flatMapLatest { state ->
        appContainer
            .dataPesertaRepository
            .getDataPesertaById(state.id)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)
    }

    val state = combine(_dataPeserta, _state) { dataPeserta, state ->
        state.copy(
            data = dataPeserta
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), DetailDataPemilihState(id))


}