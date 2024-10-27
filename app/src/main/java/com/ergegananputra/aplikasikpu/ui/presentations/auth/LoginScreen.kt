package com.ergegananputra.aplikasikpu.ui.presentations.auth

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ergegananputra.aplikasikpu.R
import com.ergegananputra.aplikasikpu.di.AppContainer
import com.ergegananputra.aplikasikpu.domain.repository.AuthRepository
import com.ergegananputra.aplikasikpu.domain.repository.DataPesertaRepository
import com.ergegananputra.aplikasikpu.ui.presentations.components.KPUOutlineTextField
import com.ergegananputra.aplikasikpu.ui.presentations.components.KpuButton
import com.ergegananputra.aplikasikpu.ui.theme.AplikasiKPUTheme

@Preview(
    name = "Light Mode",
    showSystemUi = true,
    showBackground = true,
)
@Composable
private fun LoginScreenDeveloperPreview() {
    AplikasiKPUTheme {
        val repository = object : AppContainer {
            override val authRepository: AuthRepository
                get() = TODO("Not yet implemented")
            override val dataPesertaRepository: DataPesertaRepository
                get() = TODO("Not yet implemented")
            override val context: Context
                get() = TODO("Not yet implemented")
        }
        val viewModel = AuthViewModel(repository)
        LoginScreen(viewModel)
    }
}


@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onLoginSuccess : () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(state.isDone) {
        if (state.isDone) {
            onLoginSuccess()
        }
    }

    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let {
            Toast.makeText(
                context,
                state.errorMessage,
                Toast.LENGTH_SHORT
            ).show()

            viewModel.clearErrorMessage()
        }
    }

    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Image(
                painter = painterResource(R.drawable.logo_kpu_sample),
                contentDescription = "Logo KPU",
                modifier = Modifier
                    .aspectRatio(1f)
                    .padding(32.dp)
                    .weight(0.5f)
            )

            Column(
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(
                        vertical = 16.dp,
                    )
                    .weight(0.5f)
            ) {
                Column {
                    Text(
                        text = "Selamat data di Aplikasi KPU!",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Silakan Login dengan akun yang sudah diberikan.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                KPUOutlineTextField(
                    label = "Email",
                    value = state.email,
                    errMsg = state.emailErrMsg,
                    iconResId = R.drawable.ic_outline_person_24,
                    onValueChange = viewModel::onEmailChange,
                    keyboardType = KeyboardType.Email,
                    enabled = !state.isLoading,
                    modifier = Modifier.fillMaxWidth()
                )

                KPUOutlineTextField(
                    label = "Password",
                    value = state.password,
                    errMsg = state.passwordErrMsg,
                    iconResId = R.drawable.ic_baseline_password_24,
                    onValueChange = viewModel::onPasswordChange,
                    keyboardType = KeyboardType.Password,
                    enabled = !state.isLoading,
                    imeAction = ImeAction.Done,
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            KpuButton(
                text = "Login",
                enabled = !state.isLoading,
                textALign = androidx.compose.ui.text.style.TextAlign.Center,
                onClick = viewModel::attemptLogin,
                modifier = Modifier.padding(
                    bottom = 52.dp
                )
            )
        }
    }
}