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
){
    companion object {
        fun default() = AlbumUiModel(
            id = "",
            artistName = "Unknown Artist",
            name = "Unknown Album",
            releaseDate = "Unknown Release Date",
            kind = "Unknown Kind",
            artistId = null,
            artistUrl = null,
            contentAdvisoryRating = null,
            artworkUrl100 = "",
            genres = listOf(),
            url = ""
        )
    }
}
