package com.cme.songscompose.data.repositories

import com.cme.songscompose.data.model.AlbumsResponse

interface AlbumsRepository {

    suspend fun getAlbums(count: Int): Result<AlbumsResponse>

}