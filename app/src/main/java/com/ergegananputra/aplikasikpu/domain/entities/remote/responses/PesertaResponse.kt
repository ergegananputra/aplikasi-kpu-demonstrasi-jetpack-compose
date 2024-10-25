package com.ergegananputra.aplikasikpu.domain.entities.remote.responses

import com.ergegananputra.aplikasikpu.domain.entities.base.DataPesertaInterface
import com.ergegananputra.aplikasikpu.utils.toTimestamp
import com.google.gson.annotations.SerializedName
data class PesertaResponse(
    @SerializedName("id")
    override var id: Int = 0,

    @SerializedName("nik")
    override var nik: String? = null,

    @SerializedName("namaLengkap")
    override var namaLengkap: String? = null,

    @SerializedName("nomorHandphone")
    override var nomorHandphone: String? = null,

    @SerializedName("gender")
    override var gender: Int = 0,

    @SerializedName("tanggalPendataan")
    var tanggalPendataanString : String = "",

    override var tanggalPendataan: Long = tanggalPendataanString.toTimestamp(),

    @SerializedName("alamat")
    override var alamat: String? = null,

    @SerializedName("imageUrl")
    override val imageUrl: String? = null,

    @SerializedName("created_at")
    val created_at: String = "",

    @SerializedName("updated_at")
    val updated_at: String = ""
) : DataPesertaInterface<Int>


