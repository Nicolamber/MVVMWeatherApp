package com.nlambertucci.weatherappmvvm.utils

import android.provider.Settings
import kotlinx.coroutines.*

fun <T> lazyDeferred(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>>{
    return lazy {
        GlobalScope.async (start = CoroutineStart.LAZY){
            block.invoke(this)
        }
    }
}