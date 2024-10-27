package com.ergegananputra.aplikasikpu.domain.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.ergegananputra.aplikasikpu.domain.entities.room.DataPeserta
import kotlinx.coroutines.flow.Flow

@Dao
interface DataPesertaDao {
    @Upsert
    suspend fun upsert(dataPeserta: DataPeserta)

    @Delete
    suspend fun delete(dataPeserta: DataPeserta)

    @Query("SELECT * FROM data_peserta ORDER BY tanggalPendataan DESC")
    fun getAllDataPesertaOrderedByTimestampDesc(): Flow<List<DataPeserta>>

    @Query("SELECT * FROM data_peserta WHERE nik LIKE '%' || :keyword || '%' OR namaLengkap LIKE '%' || :keyword || '%'")
    fun getDataPesertaFiltered(keyword: String): Flow<List<DataPeserta>>

    @Query("SELECT * FROM data_peserta WHERE id = :id")
    fun getDataPesertaById(id: Int): Flow<DataPeserta>

    @Query("DELETE FROM data_peserta WHERE id = :id")
    suspend fun deleteDataPesertaById(id: Int)

    @Query("DELETE FROM data_peserta")
    suspend fun deleteAll()
}