package com.ergegananputra.aplikasikpu.domain

import android.content.Context
import com.ergegananputra.aplikasikpu.domain.repository.DataPesertaRepository

interface AppContainer {
    val dataPesertaRepository: DataPesertaRepository
    val context : Context
}