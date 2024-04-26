package com.example.pagingexampleone.core

import android.util.Log

fun Long.calculateTime(savedTime : Long) : Boolean{
    val timeDifference = this - savedTime
    val twentyFourHoursInMillis = 4 * 60 * 60 * 1000L //hh/mm//ss//ms
//    val twentyFourHoursInMillis = 1 * 60 * 1000L
    Log.d("Time::", "${timeDifference} $twentyFourHoursInMillis")
    return (timeDifference >= twentyFourHoursInMillis)
}