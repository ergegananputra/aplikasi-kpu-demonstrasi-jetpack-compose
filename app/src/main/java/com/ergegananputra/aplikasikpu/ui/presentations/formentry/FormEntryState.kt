package com.ergegananputra.aplikasikpu.ui.presentations.formentry

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class FormEntryState(
    val nik: String = "",
    val namaLengkap: String = "",
    val nomorHandphone: String = "",
    val gender : Int = 0,
    val tanggalPendataan: Long = System.currentTimeMillis(),
    val alamat: String = "",
    val imageFilename : String = "",

    // Fungsionalitas
    val isModalDatePickerShow : Boolean = false,
) {
    val tanggalPendataanReadable : String
        get() = if (tanggalPendataan == 0L) {
            "Pilih Tanggal"
        } else {
            SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(Date(tanggalPendataan))
        }
}
