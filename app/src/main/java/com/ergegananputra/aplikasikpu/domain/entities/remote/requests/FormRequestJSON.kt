package com.ergegananputra.aplikasikpu.domain.entities.remote.requests

import android.net.Uri

data class FormRequestJSON(
    var nik: String?,
    var namaLengkap: String?,
    var nomorHandphone: String?,
    var gender : Int,
    var tanggalPendataan: Long,
    var alamat: String?,
    var image: Uri?
)