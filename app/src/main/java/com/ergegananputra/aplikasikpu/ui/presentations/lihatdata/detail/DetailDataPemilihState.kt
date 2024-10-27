package com.ergegananputra.aplikasikpu.ui.presentations.lihatdata.detail

import com.ergegananputra.aplikasikpu.domain.entities.room.DataPeserta

data class DetailDataPemilihState(
    val id : Int,
    val data : DataPeserta? = null,
) {
    val gender : String
        get() = data?.gender.let {
            when(it) {
                1 -> "Laki-laki"
                2 -> "Perempuan"
                else -> "Tidak diketahui"
            }
        }


}
