package com.cme.songscompose.data.ui_models

data class AlbumUiState(
    val isLoading: Boolean = false,
    val albums: List<AlbumUiModel> = emptyList(),
    val error: String? = null
)
