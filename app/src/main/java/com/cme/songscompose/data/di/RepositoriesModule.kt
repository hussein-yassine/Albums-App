package com.cme.songscompose.data.di

import com.cme.songscompose.data.network.AlbumsApi
import com.cme.songscompose.data.repositories.AlbumsRepository
import com.cme.songscompose.data.repositories.AlbumsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {

    @Singleton
    @Provides
    fun provideAlbumsRepository(
        albumsApi: AlbumsApi, coroutineDispatcher: CoroutineDispatcher
    ): AlbumsRepository = AlbumsRepositoryImpl(
        albumsApi, coroutineDispatcher
    )
}