package com.ergegananputra.aplikasikpu.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.ergegananputra.aplikasikpu.ui.navigations.MainActivityEvent
import com.ergegananputra.aplikasikpu.ui.presentations.dashboard.DashboardScreen
import com.ergegananputra.aplikasikpu.ui.presentations.dashboard.DashboardViewModel
import com.ergegananputra.aplikasikpu.ui.theme.AplikasiKPUTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ergegananputra.aplikasikpu.KPUApplication
import com.ergegananputra.aplikasikpu.utils.PackageManagerUtils
import kotlinx.coroutines.launch

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
                logoutAttempt()

            }

            MainActivityEvent.GoToFormEntry -> {
                val intentToFormEntryActivity = Intent(this, FormEntryActivity::class.java)
                launcherToFormEntry.launch(intentToFormEntryActivity)
            }
            MainActivityEvent.GoToInformasi -> {
                val intentToInformasiActivity = Intent(this, InformasiActivity::class.java)
                launcherToInformasi.launch(intentToInformasiActivity)
            }
            MainActivityEvent.GoToLihatData -> {
                val intentToLihatDataActivity = Intent(this, DataPemilihActivity::class.java)
                launcherToLihatData.launch(intentToLihatDataActivity)
            }
        }
    }

    private fun logoutAttempt() {
        lifecycleScope.launch {
            val appContainer = (application as KPUApplication).appContainer

            val result = appContainer.authRepository.logout()

            when (result) {
                is com.ergegananputra.aplikasikpu.utils.Result.Error -> {
                    dashboardViewModel.displayErrorMessage(result.exception.message ?: "Terjadi kesalahan dalam logout")
                }
                is com.ergegananputra.aplikasikpu.utils.Result.Success<*> -> {
                    finish()
                }
            }
        }
    }

    private val launcherToInformasi = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            // TODO
        } else {
            // TODO
        }
    }

    private val launcherToLihatData = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            // TODO
        } else {
            // TODO
        }
    }

    private val launcherToFormEntry = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            // TODO
        } else {
            // TODO
        }
    }


}

