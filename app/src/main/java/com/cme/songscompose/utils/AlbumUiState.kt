package com.cme.songscompose.utils

import com.cme.songscompose.data.model.Album

data class AlbumUiState(
    val isLoading: Boolean = false,
    val albums: List<Album> = emptyList(),
    val error: String? = null
)
