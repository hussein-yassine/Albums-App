package com.cme.songscompose.screens.albums

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cme.songscompose.data.ui_models.AlbumUiState
import com.cme.songscompose.navigation.AppScreens
import com.cme.songscompose.widgets.AlbumItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumsScreen(navController: NavController, viewModel: AlbumsViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = {
                    Text(
                        text = "Top 100 Albums",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                },
                actions = {
                    if (uiState.value.error != null && uiState.value.albums.isEmpty()) {
                        IconButton(onClick = { viewModel.retryFetch() }) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Retry",
                                tint = Color.Black
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors()
            )
        },
        content = { padding ->

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                if (uiState.value.isLoading) {
                    CircularProgressIndicator(color = Color.Black)
                } else if (uiState.value.error != null) {
                    Text(text = uiState.value.error!!)
                } else {
                    val albumsUiState = uiState.value
                    MainContent(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        navController = navController,
                        albumsUiState = albumsUiState
                    )
                }
            }
        })
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainContent(modifier: Modifier, navController: NavController, albumsUiState: AlbumUiState) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val feedCopyright = albumsUiState.copyright
        val albums = albumsUiState.albums
        if (albums.isEmpty()) {
            Text("No albums found")
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2)
            ) {
                items(albums) { album ->
                    AlbumItem(album) { albumId ->
                        navController.navigate(
                            route = AppScreens.AlbumDetailsScreen.name + "/$albumId" + "/$feedCopyright"
                        )
                    }
                }
            }
        }
    }
}

