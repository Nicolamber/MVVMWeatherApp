package com.nlambertucci.weatherappmvvm.utils.provider

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.nlambertucci.weatherappmvvm.utils.UnitSystem

const val UNIT_SYSTEM = "UNIT_SYSTEM"

class UnitProviderImpl (context: Context):PreferenceProvider(context), UnitProvider  {


    override fun getUnitSystem(): UnitSystem {
        val selectedName = preferences.getString(UNIT_SYSTEM,UnitSystem.METRIC.name)
        return  UnitSystem.valueOf(selectedName!!)
    }
}