package com.ergegananputra.aplikasikpu.data.repository

import android.util.Log
import com.ergegananputra.aplikasikpu.domain.repository.AuthRepository
import com.ergegananputra.aplikasikpu.utils.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AuthRepositoryImpl : AuthRepository {

    private val auth : FirebaseAuth by lazy {
        Firebase.auth
    }

    override suspend fun login(email: String, password: String): Result {
        val result = suspendCancellableCoroutine { continuation ->
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val result = Result.Success(true)
                        continuation.resume(result)
                    } else {
                        Log.d(this::class.simpleName, "login: ${it.exception}")
                        val result = Result.Error(Exception(it.exception))
                        continuation.resume(result)
                    }
                }

            continuation.invokeOnCancellation {
                Log.d(this::class.simpleName, "login: cancelled : ${it?.message}")
            }
        }

        return result
    }

    override suspend fun logout(): Result {
        return suspendCoroutine { continuation ->
            auth.signOut()
            val result = Result.Success(Unit)
            continuation.resume(result)
        }
    }

    override suspend fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    override suspend fun getUid(): String? {
        return try {
            auth.currentUser?.uid
        } catch (e: Exception) {
            Log.e(this::class.simpleName, "getUid: ", e)
            null
        }
    }

}