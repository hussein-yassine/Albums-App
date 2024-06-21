package com.cme.songscompose.data.model


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
) {
    companion object {
        val defaultAlbum = Album(
            artistId = "1500139475",
            artistName = "Peso Pluma",
            artistUrl = "https://music.apple.com/us/artist/peso-pluma/1500139475",
            artworkUrl100 = "https://is1-ssl.mzstatic.com/image/thumb/Music211/v4/3d/e9/65/3de9651e-fe11-0525-7eee-762861af1152/198391808319.jpg/100x100bb.jpg",
            contentAdvisoryRating = "Explict",
            genres = listOf(
                Genre(genreId = "34", name = "Music", url = "https://itunes.apple.com/us/genre/id34"),
                Genre(genreId = "12", name = "Latin", url = "https://itunes.apple.com/us/genre/id12")
            ),
            id = "1748015447",
            kind = "albums",
            name = "ÉXODO",
            releaseDate = "2024-06-20",
            url = "https://music.apple.com/us/album/%C3%A9xodo/1748015447"
        )
    }
}