package com.cme.songscompose.utils

import com.cme.songscompose.BuildConfig
import timber.log.Timber


fun Any.fastLog(message: String?) {
    if (BuildConfig.DEBUG) Timber.tag("[LOG]").wtf(message)
}

fun Any.debug(message: String) {
    if (BuildConfig.DEBUG) Timber.tag("[DEBUG]").d(message)
}