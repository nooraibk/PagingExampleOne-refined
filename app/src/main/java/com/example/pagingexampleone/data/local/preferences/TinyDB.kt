package com.example.pagingexampleone.data.local.preferences

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.preference.PreferenceManager
import android.util.Log
import javax.inject.Inject

class TinyDB @Inject constructor(
    val context: Context
) {
    private val preferences: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    /**  Int preferences methods
     * Get int value from SharedPreferences at 'key'. If key not found, return 'defaultValue'
     *
     * @param key SharedPreferences key
     * @return int value at 'key' or 'defaultValue' if key not found
     * getIntMax use for default max value
     * getIntMin user for default min value
     */
    fun getIntMax(key: String?): Int {
        return preferences.getInt(key, 85)
    }

    fun getIntMin(key: String?): Int {
        return preferences.getInt(key, 0)
    }

    /**
     * Put int value into SharedPreferences with 'key' and save
     *
     * @param key   SharedPreferences key
     * @param value int value to be added
     */
    fun putInt(key: String?, value: Int) {
        checkForNullKey(key)
        preferences.edit().putInt(key, value).apply()
    }

    /** Boolean preferences methods
     * Get boolean value from SharedPreferences at 'key'. If key not found, return 'defaultValue'
     *
     * @param key SharedPreferences key
     * @return boolean value at 'key' or 'defaultValue' if key not found
     */
    fun getBoolean(key: String?): Boolean {
        return preferences.getBoolean(key, false)
    }

    /**
     * Put boolean value into SharedPreferences with 'key' and save
     *
     * @param key   SharedPreferences key
     * @param value boolean value to be added
     */
    fun putBoolean(key: String?, value: Boolean) {
        checkForNullKey(key)
        preferences.edit().putBoolean(key, value).apply()
    }

    /**
     * Get String value from SharedPreferences at 'key'. If key not found, return ""
     *
     * @param key SharedPreferences key
     * @return String value at 'key' or "" (empty String) if key not found
     */
    fun getString(key: String?): String? {
        return preferences.getString(key, "")
    }

    /**
     * Put String value into SharedPreferences with 'key' and save
     *
     * @param key   SharedPreferences key
     * @param value String value to be added
     */
    fun putString(key: String?, value: String?) {
        try {
            Log.d("TinyDB -> putString()","locale 1=$key and 2=$value")
            checkForNullKey(key)
            checkForNullValue(value)
            preferences.edit().putString(key, value).apply()
        } catch (exp: Exception) {
            exp.printStackTrace()
        }
    }

    /**
     * Get long value from SharedPreferences at 'key'. If key not found, return 'defaultValue'
     *
     * @param key          SharedPreferences key
     * @param defaultValue long value returned if key was not found
     * @return long value at 'key' or 'defaultValue' if key not found
     */
    fun getLong(key: String?, defaultValue: Long): Long {
        return preferences.getLong(key, defaultValue)
    }

    /**
     * Put long value into SharedPreferences with 'key' and save
     *
     * @param key   SharedPreferences key
     * @param value long value to be added
     */
    fun putLong(key: String?, value: Long) {
        checkForNullKey(key)
        preferences.edit().putLong(key, value).apply()
    }

    /**
     * Get float value from SharedPreferences at 'key'. If key not found, return 'defaultValue'
     *
     * @param key SharedPreferences key
     * @return float value at 'key' or 'defaultValue' if key not found
     */
    fun getFloat(key: String?): Float {
        return preferences.getFloat(key, 0f)
    }

    /**
     * Put float value into SharedPreferences with 'key' and save
     *
     * @param key   SharedPreferences key
     * @param value float value to be added
     */
    fun putFloat(key: String?, value: Float) {
        checkForNullKey(key)
        preferences.edit().putFloat(key, value).apply()
    }

    /**
     * Get double value from SharedPreferences at 'key'. If exception thrown, return 'defaultValue'
     *
     * @param key          SharedPreferences key
     * @param defaultValue double value returned if exception is thrown
     * @return double value at 'key' or 'defaultValue' if exception is thrown
     */
    fun getDouble(key: String?, defaultValue: Double): Double {
        val number = getString(key)
        return try {
            number!!.toDouble()
        } catch (e: NumberFormatException) {
            defaultValue
        }
    }

    /**
     * Put double value into SharedPreferences with 'key' and save
     *
     * @param key   SharedPreferences key
     * @param value double value to be added
     */
    fun putDouble(key: String?, value: Double) {
        checkForNullKey(key)
        putString(key, value.toString())
    }

    fun putTheme(key: String?, value: String?) {
        checkForNullKey(key)
        preferences.edit().putString(key, value).apply()
    }

    fun getTheme(key: String?): String? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            preferences.getString(key, "System default")
        } else preferences.getString(key, "Light mode")
    }

    /**
     * null keys would corrupt the shared pref file and make them unreadable this is MyLiveEarthLocation preventive measure
     *
     * @param key pref key
     */
    private fun checkForNullKey(key: String?) {
        if (key == null) {
            throw NullPointerException()
        }
    }

    /**
     * null keys would corrupt the shared pref file and make them unreadable this is MyLiveEarthLocation preventive measure
     *
     * @param value pref key
     */
    private fun checkForNullValue(value: String?) {
        if (value == null) {
            throw NullPointerException()
        }
    }

}