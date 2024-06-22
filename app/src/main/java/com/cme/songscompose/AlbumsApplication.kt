package com.cme.songscompose

import android.app.Application
import com.cme.songscompose.utils.Constants
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm
import io.realm.RealmConfiguration
import timber.log.Timber


@HiltAndroidApp
class AlbumsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}