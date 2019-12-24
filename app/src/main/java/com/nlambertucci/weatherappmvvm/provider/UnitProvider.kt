package com.nlambertucci.weatherappmvvm.provider

import com.nlambertucci.weatherappmvvm.utils.UnitSystem

interface UnitProvider {
    fun getUnitSystem():UnitSystem
}