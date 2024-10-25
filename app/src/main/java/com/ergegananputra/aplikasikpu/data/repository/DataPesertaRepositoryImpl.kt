package com.ergegananputra.aplikasikpu.data.repository

import android.util.Log
import com.ergegananputra.aplikasikpu.data.database.AppDatabase
import com.ergegananputra.aplikasikpu.data.remote.BackendApi
import com.ergegananputra.aplikasikpu.domain.entities.room.DataPeserta
import com.ergegananputra.aplikasikpu.domain.repository.DataPesertaRepository
import com.ergegananputra.aplikasikpu.utils.Result
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.time.Instant
import java.util.Date

class DataPesertaRepositoryImpl(
    database: AppDatabase,
    private val api: BackendApi
) : DataPesertaRepository {
    private val db =database.formDao()

    override suspend fun storeDataPeserta(dataPeserta: DataPeserta) : Result {
        try {
            db.upsert(dataPeserta)
        } catch (e: Exception) {
            return Result.Error(e)
        }

        return Result.Success(Unit)
    }

    override suspend fun getDataPeserta() : Result {
        try {
            val dataPeserta = db.getAllDataPesertaOrderedByTimestampDesc()
            return Result.Success(dataPeserta)
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }

    override suspend fun uploadDataPeserta(
        dataPeserta: DataPeserta,
        imageFile: File
    ) : Result {
        val nik = dataPeserta.nik.toRequestBody()
        val namaLengkap = dataPeserta.namaLengkap.toRequestBody()
        val nomorHandphone = dataPeserta.nomorHandphone.toRequestBody()
        val gender =dataPeserta.gender.toRequestBody()
        val tanggalPendataan = dataPeserta.tanggalPendataan.toRequestBody()
        val alamat = dataPeserta.alamat.toRequestBody()

        val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), imageFile)
        val image = MultipartBody.Part.createFormData("image", imageFile.name, requestFile)

        val result = try {
            api.postPeserta(
                    nik,
                    namaLengkap,
                    nomorHandphone,
                    gender,
                    tanggalPendataan,
                    alamat,
                    image
                ).execute()
        } catch (e: Exception) {
            Log.e("DataPesertaRepositoryImpl", "Error: ${e.message}")
            return Result.Error(e)
        }

        if (result.isSuccessful.not()) {
            return Result.Error(Exception("Upload data peserta failed"), "Gagal mengunggah data peserta")
        }

        return Result.Success(Unit)
    }

    override suspend fun downloadDataPeserta() : Result {
        val result = api.getPeserta().execute()

        if (result.isSuccessful.not()) {
            return Result.Error(Exception("Download data peserta failed"), "Gagal mengunduh data peserta")
        }

        val dataPeserta = result.body()?.data ?: return Result.Error(Exception("Download data peserta failed"), "Gagal mengunduh data peserta")

        try {
            dataPeserta.forEach {
                db.upsert(
                    DataPeserta(
                        id = it.id,
                        nik = it.nik,
                        namaLengkap = it.namaLengkap,
                        nomorHandphone = it.nomorHandphone,
                        gender = it.gender,
                        tanggalPendataan = it.tanggalPendataan,
                        alamat = it.alamat,
                        imageUrl = it.imageUrl
                    )
                )
            }
        } catch (e: Exception) {
            return Result.Error(e)
        }

        return Result.Success(Unit)
    }

    private fun String?.toRequestBody(): RequestBody {
        val content = this ?: ""
        return RequestBody.create("text/plain".toMediaTypeOrNull(), content)
    }

    private fun Int.toRequestBody(): RequestBody {
        val content = this.toString()
        return RequestBody.create("text/plain".toMediaTypeOrNull(), content)
    }

    private fun Long.toRequestBody(): RequestBody {
        // content to YYYY-MM-DD HH:MM:SS
        val instant = Instant.ofEpochMilli(this)
        val content = Date.from(instant).toString()
        return RequestBody.create("text/plain".toMediaTypeOrNull(), content)
    }
}