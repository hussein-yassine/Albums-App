package com.cme.songscompose.data.api_models


import com.google.gson.annotations.SerializedName

data class Album(
    @SerializedName("artistId")
    val artistId: String?,
    @SerializedName("artistName")
    val artistName: String,
    @SerializedName("artistUrl")
    val artistUrl: String?,
    @SerializedName("artworkUrl100")
    val artworkUrl100: String,
    @SerializedName("contentAdvisoryRating")
    val contentAdvisoryRating: String?,
    @SerializedName("genres")
    val genres: List<Genre>,
    @SerializedName("id")
    val id: String,
    @SerializedName("kind")
    val kind: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("releaseDate")
    val releaseDate: String,
    @SerializedName("url")
    val url: String
)