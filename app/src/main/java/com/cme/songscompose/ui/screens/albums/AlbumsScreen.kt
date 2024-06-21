package com.cme.songscompose.ui.screens.albums

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.cme.songscompose.data.model.Album
import com.cme.songscompose.utils.ResourceFlowState
import timber.log.Timber


@Composable
fun AlbumsScreen(navController: NavController, viewModel: AlbumsViewModel = hiltViewModel()) {

    val uiState = viewModel.albumsTitles.collectAsState()
    val uiState2 = viewModel.albumsTitles2.collectAsState()
    Timber.tag("TAG").e(" 2 AlbumsScreen: ${uiState2.value}")

    when(uiState.value.state){
        ResourceFlowState.IDLE -> {
            Timber.tag("TAG").e("AlbumsScreen: IDLE")
        }
        is ResourceFlowState.LOADING -> {
            Timber.tag("TAG").e("AlbumsScreen: LOADING")
        }
        ResourceFlowState.SUCCESS-> {
            Timber.tag("TAG").e("AlbumsScreen: SUCCESS")
        }

        ResourceFlowState.ERROR-> {
            Timber.tag("TAG").e("AlbumsScreen: ERROR")
        }
    }
}

@Composable
fun AlbumItem(album: Album) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                // Navigate to detail screen
            }
    ) {
        Image(
            painter = rememberImagePainter(album.artworkUrl100),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        Text(album.name, fontWeight = FontWeight.Bold)
        Text(album.artistName)
    }
}
