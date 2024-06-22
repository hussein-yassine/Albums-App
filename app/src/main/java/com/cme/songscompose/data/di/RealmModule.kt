package com.cme.songscompose.data.di

import android.content.Context
import com.cme.songscompose.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RealmModule {

    @Provides
    @Singleton
    fun providesRealmDatabase(
        @ApplicationContext context: Context, realmConfiguration: RealmConfiguration
    ): Realm {
        Realm.init(context)
        Realm.setDefaultConfiguration(realmConfiguration)
        return Realm.getDefaultInstance()
    }

    @Provides
    @Singleton
    fun provideRealmConfig(@ApplicationContext context: Context): RealmConfiguration{
        Realm.init(context)
        return RealmConfiguration
            .Builder()
            .name(Constants.RealmDb.DB_NAME)
            .schemaVersion(1)
            .build()

    }

}