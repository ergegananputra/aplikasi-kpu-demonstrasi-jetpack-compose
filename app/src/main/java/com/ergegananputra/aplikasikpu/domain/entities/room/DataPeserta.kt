package com.ergegananputra.aplikasikpu.domain.entities.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ergegananputra.aplikasikpu.domain.entities.base.DataPesertaInterface

@Entity(tableName = "data_peserta")
data class DataPeserta(
    @PrimaryKey(autoGenerate = true)
    override var id: Int = 0,
    override var nik: String?,
    override var namaLengkap: String?,
    override var nomorHandphone: String?,
    override var gender: Int,
    override var tanggalPendataan: Long,
    override var alamat: String?,
    override val imageUrl: String?
) : DataPesertaInterface<Int>
