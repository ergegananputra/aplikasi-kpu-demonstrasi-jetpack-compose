package com.ergegananputra.aplikasikpu.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ergegananputra.aplikasikpu.KPUApplication
import com.ergegananputra.aplikasikpu.ui.presentations.auth.AuthViewModel
import com.ergegananputra.aplikasikpu.ui.presentations.auth.LoginScreen
import com.ergegananputra.aplikasikpu.ui.theme.AplikasiKPUTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthActivity : ComponentActivity() {

    private val appContainer by lazy {
        (application as KPUApplication).appContainer
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch(Dispatchers.IO) {
            val isLoggedIn = appContainer.authRepository.isUserLoggedIn()
            if (isLoggedIn) {
                loginSuccess()
            }
        }

        enableEdgeToEdge()
        setContent {
            val viewModel = viewModel {
                AuthViewModel(
                    appContainer = (application as KPUApplication).appContainer
                )
            }

            AplikasiKPUTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginScreen(
                        viewModel = viewModel,
                        onLoginSuccess = ::loginSuccess,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    private fun loginSuccess() {
        val intentToMain = Intent(this, MainActivity::class.java)
        startActivity(intentToMain)

        finish()
    }
}
