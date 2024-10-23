package com.ergegananputra.aplikasikpu.ui.activities

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.ergegananputra.aplikasikpu.ui.navigations.MainActivityEvent
import com.ergegananputra.aplikasikpu.ui.presentations.dashboard.DashboardScreen
import com.ergegananputra.aplikasikpu.ui.presentations.dashboard.DashboardViewModel
import com.ergegananputra.aplikasikpu.ui.theme.AplikasiKPUTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ergegananputra.aplikasikpu.utils.PackageManagerUtils

class MainActivity : ComponentActivity() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            dashboardViewModel = viewModel{
                DashboardViewModel().also {
                    it.setAppVersion(
                        appVersion = PackageManagerUtils.getAppVersion(context = this@MainActivity)
                    )
                }
            }

            AplikasiKPUTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DashboardScreen(
                        mainEvent = ::onEvent,
                        viewModel = dashboardViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    private fun onEvent(event: MainActivityEvent) {
        when (event) {
            MainActivityEvent.LogOut -> {
                finish()
            }

            MainActivityEvent.GoToFormEntry -> {
                // TODO
            }
            MainActivityEvent.GoToInformasi -> {
                // TODO
            }
            MainActivityEvent.GoToLihatData -> {
                // TODO
            }
        }
    }


}

