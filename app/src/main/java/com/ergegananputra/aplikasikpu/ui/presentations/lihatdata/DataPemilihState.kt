package com.ergegananputra.aplikasikpu.ui.presentations.lihatdata

import com.ergegananputra.aplikasikpu.domain.entities.room.DataPeserta

data class DataPemilihState(
    val dataPesertaList: List<DataPeserta> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage : String? = null
)
