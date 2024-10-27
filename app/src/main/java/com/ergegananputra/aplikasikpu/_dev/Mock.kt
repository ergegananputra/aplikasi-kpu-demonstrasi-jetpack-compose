package com.ergegananputra.aplikasikpu._dev

import android.content.Context
import com.ergegananputra.aplikasikpu.di.AppContainer
import com.ergegananputra.aplikasikpu.domain.repository.AuthRepository
import com.ergegananputra.aplikasikpu.domain.repository.DataPesertaRepository

class Mock {
    companion object {
        val appContainer = object : AppContainer {
            override val authRepository: AuthRepository
                get() = TODO("Not yet implemented")
            override val dataPesertaRepository: DataPesertaRepository
                get() = TODO("Not yet implemented")
            override val context: Context
                get() = TODO("Not yet implemented")

        }
    }
}