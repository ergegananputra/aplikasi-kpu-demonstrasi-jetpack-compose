package com.ergegananputra.aplikasikpu.ui.presentations.lihatdata.home

import com.ergegananputra.aplikasikpu.domain.entities.room.DataPeserta

data class DataPemilihState(
    val dataPesertaList: List<DataPeserta> = emptyList(),
    val keyword : String = "",

    val isLoading: Boolean = false,
    val errorMessage : String? = null
)
