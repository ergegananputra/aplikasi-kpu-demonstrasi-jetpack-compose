package com.ergegananputra.aplikasikpu.di

import android.content.Context
import com.ergegananputra.aplikasikpu.data.database.AppDatabase
import com.ergegananputra.aplikasikpu.data.remote.BackendApi
import com.ergegananputra.aplikasikpu.data.repository.AuthRepositoryImpl
import com.ergegananputra.aplikasikpu.data.repository.DataPesertaRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainerImpl(
    override val context: Context
) : AppContainer {

    private fun provideOKHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    private val backendApi by lazy {
        Retrofit
            .Builder()
            .baseUrl("https://masmoendigital.store/api/v0/")
            .client(provideOKHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BackendApi::class.java)
    }

    private val database by lazy {
        AppDatabase.getDatbase(context)
    }

    override val authRepository by lazy {
        AuthRepositoryImpl()
    }

    override val dataPesertaRepository by lazy {
        DataPesertaRepositoryImpl(
            database = database,
            api = backendApi,
            auth = authRepository
        )
    }

}