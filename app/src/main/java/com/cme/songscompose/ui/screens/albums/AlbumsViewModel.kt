package com.cme.songscompose.ui.screens.albums

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cme.songscompose.data.model.Album
import com.cme.songscompose.data.repositories.AlbumsRepository
import com.cme.songscompose.utils.Resource
import com.cme.songscompose.utils.ResourceFlow
import com.cme.songscompose.utils.ResourceFlowState
import com.cme.songscompose.utils.ResourceState
import com.cme.songscompose.utils.setError
import com.cme.songscompose.utils.setLoading
import com.cme.songscompose.utils.setSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AlbumsViewModel @Inject constructor(
    private val repository: AlbumsRepository
) : ViewModel() {

    private val _albumsTitles = MutableStateFlow<ResourceFlow<List<String>>>(
        ResourceFlow(state = ResourceFlowState.IDLE)
    )
    val albumsTitles: StateFlow<ResourceFlow<List<String>>>
        get() = _albumsTitles

    private val _albumsTitles2 = MutableStateFlow<List<String>>(emptyList())
    val albumsTitles2: StateFlow<List<String>>
        get() = _albumsTitles2

    init {
        getTopAlbums()
    }

    private fun getTopAlbums() {
        viewModelScope.launch {
            _albumsTitles.setLoading()
            repository
                .getAlbums(10)
                .onSuccess { songs ->
                    Timber.tag("TAG").e("AlbumsVB: SUCCESS")
                    _albumsTitles.setSuccess(songs.feed.albums.map { it.name })
                    _albumsTitles2.value = songs.feed.albums.map { it.name }
                }.onFailure { error ->
                    Timber.tag("GET SONGS ERROR").e(error, "getTopSongs: ")
                    _albumsTitles.setError("Error", Exception(error))
                }
        }
    }
}