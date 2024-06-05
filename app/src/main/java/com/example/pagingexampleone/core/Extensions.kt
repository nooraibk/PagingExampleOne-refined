package com.example.pagingexampleone.core

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes

infix fun Long.calculateAndCheckTimeOf(savedTime : Long) : Boolean{
    val timeDifference = this - savedTime
//    val twentyFourHoursInMillis = 4 * 60 * 60 * 1000L //hh/mm//ss//ms
    val twentyFourHoursInMillis = 1 * 60 * 1000L
    "$timeDifference $twentyFourHoursInMillis".logit("Time:: ")
    return (timeDifference >= twentyFourHoursInMillis)
}

infix fun Context?.showToastOf(@StringRes resourceId: Int) {
    this?.let {
        Toast.makeText(this, resourceId, Toast.LENGTH_SHORT).show()
    }
}

fun String.logit(tag : String = "PagingExampleApp:: "){
    Log.d(tag, this)
}
