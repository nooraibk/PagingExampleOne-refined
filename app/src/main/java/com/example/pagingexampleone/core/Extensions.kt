package com.example.pagingexampleone.core

fun Long.calculateTime(savedTime : Long) : Boolean{
    val timeDifference = this - savedTime
    val twentyFourHoursInMillis = 24 * 60 * 60 * 1000L
    return (timeDifference >= twentyFourHoursInMillis)
}