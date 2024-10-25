package com.ergegananputra.aplikasikpu.domain.repository


import com.ergegananputra.aplikasikpu.domain.entities.room.DataPeserta
import com.ergegananputra.aplikasikpu.utils.Result
import kotlinx.coroutines.flow.Flow
import java.io.File

interface DataPesertaRepository {
    suspend fun storeDataPeserta(dataPeserta: DataPeserta) : Result
    fun getDataPeserta() : Flow<List<DataPeserta>>

    suspend fun uploadDataPeserta(dataPeserta: DataPeserta, imageFile: File) : Result
    suspend fun downloadDataPeserta() : Result
}