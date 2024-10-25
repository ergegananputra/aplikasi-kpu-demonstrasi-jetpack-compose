package com.ergegananputra.aplikasikpu.domain.entities.base

interface DataPesertaInterface<ID> {
    var id: ID
    var nik: String?
    var namaLengkap: String?
    var nomorHandphone: String?
    var gender : Int
    var tanggalPendataan: Long
    var alamat: String?
    val imageUrl: String?
}