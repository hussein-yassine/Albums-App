package com.cme.songscompose.screens.albums

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cme.songscompose.data.model.Album
import com.cme.songscompose.data.repositories.AlbumsRepository
import com.cme.songscompose.utils.AlbumUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AlbumsViewModel @Inject constructor(
    private val repository: AlbumsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AlbumUiState(isLoading = true))
    val uiState: StateFlow<AlbumUiState> = _uiState

    init {
        getTopAlbums()
    }

    private fun getTopAlbums() {
        viewModelScope.launch {
            repository
                .getAlbums(10)
                .onSuccess { result ->
                    Timber.tag("TAG").e("AlbumsVB: SUCCESS")
                    _uiState.update { it.copy(isLoading = false, albums = result.feed.albums) }
                }.onFailure { error ->
                    Timber.tag("GET SONGS ERROR").e(error, "getTopSongs: ")
                    _uiState.update { it.copy(isLoading = false, error = error.localizedMessage) }
                }
        }
    }
}