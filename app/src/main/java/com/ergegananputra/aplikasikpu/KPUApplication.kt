package com.ergegananputra.aplikasikpu

import android.app.Application
import com.ergegananputra.aplikasikpu.data.AppContainerImpl

class KPUApplication : Application() {

    val appContainer by lazy {
        AppContainerImpl(this)
    }

    override fun onCreate() {
        super.onCreate()
    }
}