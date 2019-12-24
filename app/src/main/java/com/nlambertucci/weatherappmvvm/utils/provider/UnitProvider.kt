package com.nlambertucci.weatherappmvvm.utils.provider

import com.nlambertucci.weatherappmvvm.utils.UnitSystem

interface UnitProvider {
    fun getUnitSystem():UnitSystem
}