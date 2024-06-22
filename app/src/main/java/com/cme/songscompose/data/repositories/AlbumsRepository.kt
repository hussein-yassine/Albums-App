package com.cme.songscompose.data.repositories

import com.cme.songscompose.data.realm_models.RealmAlbum
import com.cme.songscompose.data.realm_models.RealmFeed
import kotlinx.coroutines.flow.Flow

interface AlbumsRepository {

    suspend fun getAlbums(count: Int): Flow<Result<RealmFeed>>

    suspend fun getAlbumById(albumId: String): Result<RealmAlbum>
}