package com.cme.songscompose.screens.albums

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cme.songscompose.data.repositories.AlbumsRepository
import com.cme.songscompose.data.ui_models.AlbumUiState
import com.cme.songscompose.utils.fastLog
import com.cme.songscompose.utils.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
                .collect { result ->
                    result.onSuccess { feed ->
                        _uiState.update { state ->
                            state.copy(
                                isLoading = false,
                                copyright = feed.copyright,
                                albums = feed.albums.map { it.toUiModel() }
                            )
                        }
                    }.onFailure { error ->
                        fastLog("GET Albums ERROR ${error.message}")
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = error.localizedMessage
                            )
                        }
                    }
                }
        }
    }
}