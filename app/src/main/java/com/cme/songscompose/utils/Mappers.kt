package com.cme.songscompose.utils

import com.cme.songscompose.data.api_models.Album
import com.cme.songscompose.data.api_models.Feed
import com.cme.songscompose.data.api_models.Genre
import com.cme.songscompose.data.realm_models.RealmAlbum
import com.cme.songscompose.data.realm_models.RealmFeed
import com.cme.songscompose.data.realm_models.RealmGenre
import com.cme.songscompose.data.ui_models.AlbumUiModel
import com.cme.songscompose.data.ui_models.FeedUiModel
import com.cme.songscompose.data.ui_models.GenreUiModel
import io.realm.RealmList
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

fun Genre.toRealmGenre(): RealmGenre {
    return RealmGenre(
        genreId = this.genreId,
        name = this.name,
        url = this.url
    )
}

fun Album.toRealmAlbum(): RealmAlbum {
    return RealmAlbum(
        id = this.id,
        artistName = this.artistName,
        name = this.name,
        releaseDate = this.releaseDate,
        kind = this.kind,
        artistId = this.artistId,
        artistUrl = this.artistUrl,
        contentAdvisoryRating = this.contentAdvisoryRating,
        artworkUrl100 = this.artworkUrl100,
        genres = RealmList<RealmGenre>().apply {
            addAll(this@toRealmAlbum.genres.map { it.toRealmGenre() })
        },
        url = this.url
    )
}

fun Feed.toRealmFeed(): RealmFeed {
    return RealmFeed(
        id = this.id,
        title = this.title,
        copyright = this.copyright,
        country = this.country,
        icon = this.icon,
        updated = this.updated,
        albums = RealmList<RealmAlbum>().apply {
            addAll(this@toRealmFeed.albums.map {it.toRealmAlbum() })
        }
    )
}

fun RealmFeed.toUiModel(): FeedUiModel {
    return FeedUiModel(
        id = id,
        title = title,
        copyright = copyright,
        country = country,
        icon = icon,
        updated = updated,
        albums = albums.map { it.toUiModel() }
    )
}

fun RealmAlbum.toUiModel(): AlbumUiModel {
    return AlbumUiModel(
        id = id,
        artistName = artistName,
        name = name,
        releaseDate = releaseDate,
        kind = kind,
        artistId = artistId,
        artistUrl = artistUrl,
        contentAdvisoryRating = contentAdvisoryRating,
        artworkUrl100 = artworkUrl100,
        genres = genres.map { GenreUiModel(it.genreId, it.name, it.url) },
        url = url
    )
}

fun String.formatDate(): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val outputFormat = SimpleDateFormat("MMM. dd, yyyy", Locale.US)
        val date = inputFormat.parse(this)
        outputFormat.format(date)
    } catch (e: Exception) {
        this
    }
}