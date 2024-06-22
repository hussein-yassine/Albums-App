package com.cme.songscompose.data.ui_models

data class FeedUiModel(
    val id: String,
    val title: String,
    val copyright: String,
    val country: String,
    val icon: String,
    val updated: String,
    val albums: List<AlbumUiModel>
)
