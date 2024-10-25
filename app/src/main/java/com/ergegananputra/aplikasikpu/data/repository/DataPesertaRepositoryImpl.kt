package com.ergegananputra.aplikasikpu.data.repository

import android.util.Log
import com.ergegananputra.aplikasikpu.data.database.AppDatabase
import com.ergegananputra.aplikasikpu.data.remote.BackendApi
import com.ergegananputra.aplikasikpu.domain.entities.remote.responses.GetPesertaResponse
import com.ergegananputra.aplikasikpu.domain.entities.room.DataPeserta
import com.ergegananputra.aplikasikpu.domain.repository.DataPesertaRepository
import com.ergegananputra.aplikasikpu.utils.Result
import com.ergegananputra.aplikasikpu.utils.toTimestamp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.time.Instant
import java.util.Date
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

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

    override fun getDataPeserta() : Flow<List<DataPeserta>> {
        return db.getAllDataPesertaOrderedByTimestampDesc()
    }

    override fun getDataPesertaFiltered(keyword: String) : Flow<List<DataPeserta>> {
        return db.getDataPesertaFiltered(keyword)
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
        return suspendCoroutine { continuation ->
            api.getPeserta().enqueue( object : Callback<GetPesertaResponse> {
                override fun onResponse(
                    call: Call<GetPesertaResponse>,
                    response: Response<GetPesertaResponse>,
                ) {

                    if (response.isSuccessful.not()) {
                        Log.e("DataPesertaRepositoryImpl", "onResponse - Error: ${response.errorBody()}")
                        val result = Result.Error(Exception("Download data peserta failed"), "Gagal mengunduh data peserta")
                        continuation.resume(result)
                        return
                    }

                    val dataPeserta = response.body()?.data ?: emptyList()

                    try {
                        CoroutineScope(continuation.context).launch {
                            deleteAllDataPeserta()

                            dataPeserta.forEach {
                                db.upsert(
                                    DataPeserta(
                                        id = it.id,
                                        nik = it.nik,
                                        namaLengkap = it.namaLengkap,
                                        nomorHandphone = it.nomorHandphone,
                                        gender = it.gender,
                                        tanggalPendataan = it.tanggalPendataan.toTimestamp(),
                                        alamat = it.alamat,
                                        imageUrl = it.imageUrl
                                    )
                                )
                            }
                        }
                        val result = Result.Success(Unit)
                        continuation.resume(result)
                    } catch (e: Exception) {
                        Log.e("DataPesertaRepositoryImpl", "(DownloadDataPeserta) Error: $e")
                        val result = Result.Error(e)
                        continuation.resume(result)
                    }
                }

                override fun onFailure(call: Call<GetPesertaResponse>, e: Throwable) {
                    Log.e("DataPesertaRepositoryImpl", "onFailure - Error: $e")
                    val result = Result.Error(Exception("Download data peserta failed"), e.message ?: "Gagal mengunduh data peserta")
                    continuation.resume(result)
                }
            })
        }
    }

    private suspend fun deleteAllDataPeserta() {
        db.deleteAll()
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