package com.ergegananputra.aplikasikpu.di

import android.content.Context
import com.ergegananputra.aplikasikpu.domain.repository.AuthRepository
import com.ergegananputra.aplikasikpu.domain.repository.DataPesertaRepository

interface AppContainer {
    val authRepository: AuthRepository
    val dataPesertaRepository: DataPesertaRepository
    val context : Context
}