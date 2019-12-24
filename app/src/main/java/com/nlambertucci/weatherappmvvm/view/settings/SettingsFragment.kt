package com.nlambertucci.weatherappmvvm.view.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import com.nlambertucci.weatherappmvvm.R


class SettingsFragment: PreferenceFragmentCompat() {



    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity)?.supportActionBar?.title ="Configuración"
        (activity as AppCompatActivity)?.supportActionBar?.subtitle = null
    }


}