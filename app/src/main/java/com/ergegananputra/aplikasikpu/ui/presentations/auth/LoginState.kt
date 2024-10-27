package com.ergegananputra.aplikasikpu.ui.presentations.auth

data class LoginState (
    val email: String = "",
    val emailErrMsg : String? = null,

    val password: String = "",
    val passwordErrMsg : String? = null,

    val isDone : Boolean = false,
    val isLoading: Boolean = false,

    val errorMessage: String? = null
)