package com.aparat.androidinterview.util

import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun String.extractYear(inputFormat: String = "yyyy-MM-dd"): Int? {
    return try {
        val dateFormat = SimpleDateFormat(inputFormat, Locale.getDefault())
        val date = dateFormat.parse(this)
        Calendar.getInstance().apply {
            time = date
        }.get(Calendar.YEAR)
    } catch (e: Exception){
        Timber.tag("dateError").i("CalanderError")
        null
    }
}