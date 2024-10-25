package com.ergegananputra.aplikasikpu.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun String.toTimestamp(): Long {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault())
    val date = dateFormat.parse(this)
    return date?.time ?: 0L
}