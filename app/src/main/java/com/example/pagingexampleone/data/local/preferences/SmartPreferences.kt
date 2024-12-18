package com.example.pagingexampleone.data.local.preferences

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlin.reflect.KProperty

operator fun SharedPreferences.getValue(thisRef : Any, property : KProperty<*>) : Boolean{
    return getBoolean(property.name, false)
}

operator fun SharedPreferences.setValue(thisRef : Any, property : KProperty<*>, value : Boolean){
    edit {
        putBoolean(property.name, value)
    }
}
