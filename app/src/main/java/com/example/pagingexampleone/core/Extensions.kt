package com.example.pagingexampleone.core

import android.content.Context
import android.util.Log
import android.widget.Toast

infix fun Long.calculateAndCheckTime(savedTime : Long) : Boolean{
    val timeDifference = this - savedTime
//    val twentyFourHoursInMillis = 4 * 60 * 60 * 1000L //hh/mm//ss//ms
    val twentyFourHoursInMillis = 10 * 60 * 1000L
    "$timeDifference $twentyFourHoursInMillis".logger("Time:: ")
    return (timeDifference >= twentyFourHoursInMillis)
}

infix fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun String.logger(tag : String = "PagingExampleApp:: "){
    Log.d(tag, this)
}
