package com.ergegananputra.aplikasikpu.ui.presentations.formentry

import android.net.Uri
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class FormEntryState(
    val nik: String = "",
    val nikErrMsg : String? = null,

    val namaLengkap: String = "",
    val namaLengkapErrMsg : String? = null,

    val nomorHandphone: String = "",
    val nomorHandphoneErrMsg : String? = null,

    val gender : Int = 0,

    val tanggalPendataan: Long = System.currentTimeMillis(),

    val alamat: String = "",
    val alamatErrMsg : String? = null,

    val capturedPhoto : Uri? = null,

    // Fungsionalitas
    val isModalDatePickerShow : Boolean = false,
    val isLoading : Boolean = false,
    val isDone : Boolean = false,
    val errorMessage : String? = null
) {
    val tanggalPendataanReadable : String
        get() = if (tanggalPendataan == 0L) {
            "Pilih Tanggal"
        } else {
            SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(Date(tanggalPendataan))
        }
}
