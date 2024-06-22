package com.cme.songscompose.screens.album_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cme.songscompose.data.repositories.AlbumsRepository
import com.cme.songscompose.data.ui_models.AlbumDetailsUiState
import com.cme.songscompose.utils.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumDetailsViewModel @Inject constructor(
    private val repository: AlbumsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AlbumDetailsUiState(isLoading = true))
    val uiState: StateFlow<AlbumDetailsUiState> = _uiState

    fun getAlbumById(albumId: String) {
        viewModelScope.launch {
            _uiState.value = AlbumDetailsUiState(isLoading = true)
            val result = repository.getAlbumById(albumId)
            result.onSuccess { album ->
                _uiState.value = AlbumDetailsUiState(
                    isLoading = false,
                    album = album.toUiModel()
                )
            }.onFailure { error ->
                _uiState.value = AlbumDetailsUiState(
                    isLoading = false,
                    error = error.localizedMessage
                )
            }
        }
    }
}