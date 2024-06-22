package com.cme.songscompose.data.api_models

import com.google.gson.annotations.SerializedName

data class Feed(
    @SerializedName("copyright")
    val copyright: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("updated")
    val updated: String,
    @SerializedName("results")
    val albums: List<Album>
)