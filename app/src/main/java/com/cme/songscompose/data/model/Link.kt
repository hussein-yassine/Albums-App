package com.cme.songscompose.data.model


import com.google.gson.annotations.SerializedName

data class Link(
    @SerializedName("self")
    val self: String
)