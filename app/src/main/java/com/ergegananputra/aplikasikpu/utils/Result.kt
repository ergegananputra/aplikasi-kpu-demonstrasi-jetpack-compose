package com.ergegananputra.aplikasikpu.utils

sealed class Result {
    data class Success<T>(val data: T, val message: String? = null) : Result()
    data class Error(val exception: Exception, val message: String? = null) : Result()
}