package com.ergegananputra.aplikasikpu.domain.repository

import com.ergegananputra.aplikasikpu.utils.Result

interface AuthRepository {
    suspend fun login(email: String, password: String): Result
    suspend fun logout(): Result
    suspend fun isUserLoggedIn(): Boolean

    suspend fun getUid(): String?
}