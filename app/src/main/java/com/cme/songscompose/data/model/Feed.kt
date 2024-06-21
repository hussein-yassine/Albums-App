package com.cme.songscompose.data.model


import com.google.gson.annotations.SerializedName

data class Feed(
    @SerializedName("author")
    val author: Author,
    @SerializedName("copyright")
    val copyright: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("links")
    val links: List<Link>,
    @SerializedName("title")
    val title: String,
    @SerializedName("updated")
    val updated: String,
    @SerializedName("results")
    val albums: List<Album>
)