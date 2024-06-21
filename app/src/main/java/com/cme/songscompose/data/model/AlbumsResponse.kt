package com.cme.songscompose.data.model


import com.google.gson.annotations.SerializedName

data class AlbumsResponse(
    @SerializedName("feed")
    val feed: Feed
)