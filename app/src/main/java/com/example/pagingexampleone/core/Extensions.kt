package com.example.pagingexampleone.core

import android.content.Context
import android.util.Log
import android.widget.Toast

fun Long.calculateAndCheckTime(savedTime : Long) : Boolean{
    val timeDifference = this - savedTime
//    val twentyFourHoursInMillis = 4 * 60 * 60 * 1000L //hh/mm//ss//ms
    val twentyFourHoursInMillis = 10 * 60 * 1000L
    Log.d("Time::", "${timeDifference} $twentyFourHoursInMillis")
    return (timeDifference >= twentyFourHoursInMillis)
}

fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}