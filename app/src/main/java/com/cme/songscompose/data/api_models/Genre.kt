package com.cme.songscompose.data.api_models


import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("genreId")
    val genreId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)