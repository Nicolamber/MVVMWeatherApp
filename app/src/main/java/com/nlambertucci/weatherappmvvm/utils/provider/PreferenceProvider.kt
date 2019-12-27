package com.nlambertucci.weatherappmvvm.utils.provider

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

abstract class PreferenceProvider(context: Context) {

    private val appContext = context.applicationContext
    protected val preferences: SharedPreferences
        get() {
            return PreferenceManager.getDefaultSharedPreferences(appContext)
        }
}