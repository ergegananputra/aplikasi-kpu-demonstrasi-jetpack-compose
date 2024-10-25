package com.ergegananputra.aplikasikpu.domain.repository


import com.ergegananputra.aplikasikpu.domain.entities.room.DataPeserta
import com.ergegananputra.aplikasikpu.utils.Result
import java.io.File

interface DataPesertaRepository {
    suspend fun storeDataPeserta(dataPeserta: DataPeserta) : Result
    suspend fun getDataPeserta() : Result

    suspend fun uploadDataPeserta(dataPeserta: DataPeserta, imageFile: File) : Result
    suspend fun downloadDataPeserta() : Result
}