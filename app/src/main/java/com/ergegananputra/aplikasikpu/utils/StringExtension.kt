package com.ergegananputra.aplikasikpu.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.toTimestamp(): Long {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault())
    val date = dateFormat.parse(this)
    return date?.time ?: 0L
}

fun Long.toSimpleReadableString(): String {
    val date = Date(this)
    val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return sdf.format(date)
}