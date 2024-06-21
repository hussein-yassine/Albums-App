package com.cme.songscompose.data.repositories

import com.cme.songscompose.data.model.AlbumsResponse
import com.cme.songscompose.data.network.AlbumsApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlbumsRepositoryImpl @Inject constructor(
    private val albumsApi: AlbumsApi,
    private val coroutineDispatcher: CoroutineDispatcher
) : AlbumsRepository {

    override suspend fun getAlbums(count: Int): Result<AlbumsResponse> {
        return withContext(coroutineDispatcher) {
            try {
                Result.success(albumsApi.getTopSongs(count = count))
            } catch (e: Exception) {
                e.printStackTrace()
                Result.failure(e)
            }
        }
    }

}