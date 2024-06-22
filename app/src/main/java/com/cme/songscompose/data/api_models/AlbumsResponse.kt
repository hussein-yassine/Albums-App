package com.cme.songscompose.data.api_models


import com.google.gson.annotations.SerializedName

data class AlbumsResponse(
    @SerializedName("feed")
    val feed: Feed
)