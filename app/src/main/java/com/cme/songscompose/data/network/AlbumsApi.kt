package com.cme.songscompose.data.network

import com.cme.songscompose.data.model.AlbumsResponse
import retrofit2.http.GET
import retrofit2.http.Path


/**
 *
 * Created by Hussein Yassine on 05, March, 2019.
 *
 */

interface AlbumsApi {
    @GET("api/v2/us/music/most-played/{count}/albums.json")
    suspend fun getTopSongs(@Path("count") count: Int): AlbumsResponse
}
