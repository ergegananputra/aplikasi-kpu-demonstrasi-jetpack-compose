package com.ergegananputra.aplikasikpu.data

import android.content.Context
import com.ergegananputra.aplikasikpu.data.database.AppDatabase
import com.ergegananputra.aplikasikpu.data.remote.BackendApi
import com.ergegananputra.aplikasikpu.data.repository.DataPesertaRepositoryImpl
import com.ergegananputra.aplikasikpu.domain.AppContainer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainerImpl(
    override val context: Context
) : AppContainer {

    private fun provideOKHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    private val backendApi = Retrofit
        .Builder()
        .baseUrl("https://masmoendigital.store/api/v0/")
        .client(provideOKHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(BackendApi::class.java)

    private val database = AppDatabase.getDatbase(context)

    override val dataPesertaRepository =
        DataPesertaRepositoryImpl(
            database = database,
            api = backendApi
        )
}