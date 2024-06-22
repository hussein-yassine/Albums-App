package com.cme.songscompose.data.ui_models

data class AlbumDetailsUiState(
    val isLoading: Boolean = false,
    val album: AlbumUiModel? = null,
    val error: String? = null
)
