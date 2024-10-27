package com.ergegananputra.aplikasikpu.ui.presentations.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ergegananputra.aplikasikpu.di.AppContainer
import com.ergegananputra.aplikasikpu.utils.trimmedNotEmptyOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch

class AuthViewModel(
    private val appContainer: AppContainer
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())

    val state = _state.asStateFlow()

    fun onEmailChange(email: String) {
        _state.update {
            it.copy(
                email = email,
                emailErrMsg = null
            )
        }
    }

    fun onPasswordChange(password: String) {
        _state.update {
            it.copy(
                password = password,
                passwordErrMsg = null
            )
        }
    }

    private fun displayErrorMessage(errorMessage: String) {
        _state.update {
            it.copy(errorMessage = errorMessage)
        }
    }

    fun clearErrorMessage() {
        _state.update {
            it.copy(errorMessage = null)
        }
    }

    fun attemptLogin() {
        val state = _state.updateAndGet { currentState ->
            currentState.copy(isLoading = true)
        }

        state.validated { email, password ->
            viewModelScope.launch(Dispatchers.IO) {
                when(val result = appContainer.authRepository.login(email, password)) {
                    is com.ergegananputra.aplikasikpu.utils.Result.Error -> {
                        displayErrorMessage(result.message ?: "Gagal login")
                        _state.update {
                            it.copy(isLoading = false)
                        }
                    }
                    is com.ergegananputra.aplikasikpu.utils.Result.Success<*> -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isDone = true
                            )
                        }
                    }
                }
            }
        }

        _state.update {
            it.copy(isLoading = false)
        }

    }

    private fun LoginState.validated(
        onSuccess : (email: String, password : String) -> Unit
    )  {
        val email = this.email.trimmedNotEmptyOrNull()?.validatedEmail() ?: run {
            _state.update {
                it.copy(emailErrMsg = "Email tidak valid")
            }
            return
        }

        val password = this.password.trimmedNotEmptyOrNull() ?: run {
            _state.update {
                it.copy(passwordErrMsg = "Password tidak boleh kosong")
            }
            return
        }

        onSuccess(email, password)
    }

    private fun String.validatedEmail(): String? {
        val isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
        return if (isValid) this else  null
    }

}