package com.ergegananputra.aplikasikpu.data.remote

import com.ergegananputra.aplikasikpu.domain.entities.remote.responses.GetPesertaResponse
import com.ergegananputra.aplikasikpu.domain.entities.remote.responses.PostPesertaResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface BackendApi {
    @GET("peserta")
    fun getPeserta() : Call<GetPesertaResponse>

    @Multipart
    @POST("peserta")
    fun postPeserta(
        @Part("nik") nik: RequestBody,
        @Part("namaLengkap") namaLengkap: RequestBody,
        @Part("nomorHandphone") nomorHandphone: RequestBody,
        @Part("gender") gender : RequestBody,
        @Part("tanggalPendataan") tanggalPendataan: RequestBody,
        @Part("alamat") alamat: RequestBody,
        @Part image: MultipartBody.Part
    ) : Call<PostPesertaResponse>

}