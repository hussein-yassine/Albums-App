package com.cme.songscompose.data.ui_models

data class AlbumUiModel(
    val id: String,
    val artistName: String,
    val name: String,
    val releaseDate: String,
    val kind: String,
    val artistId: String?,
    val artistUrl: String?,
    val contentAdvisoryRating: String?,
    val artworkUrl100: String,
    val genres: List<GenreUiModel>,
    val url: String
)
