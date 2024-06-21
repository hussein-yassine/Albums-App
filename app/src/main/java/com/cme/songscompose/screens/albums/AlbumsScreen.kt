package com.cme.songscompose.screens.albums

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cme.songscompose.navigation.AppScreens
import com.cme.songscompose.widgets.AlbumItem
import timber.log.Timber

@Composable
fun AlbumsScreen(navController: NavController, viewModel: AlbumsViewModel = hiltViewModel()) {
    Scaffold(
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                modifier = Modifier.statusBarsPadding(),
                title = {
                    Text(
                        text = "Top 100 Albums",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                },
                backgroundColor = Color.White
            )
        },
        content = { padding ->
        MainContent(
            modifier = Modifier.fillMaxSize().padding(padding),
            navController = navController,
            viewModel = viewModel
        )
    })
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainContent(modifier: Modifier,navController: NavController, viewModel: AlbumsViewModel) {
    val uiState = viewModel.uiState.collectAsState()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (uiState.value.isLoading) {
            CircularProgressIndicator(
                color = Color.Black
            )
            Timber.tag("TAG").e("AlbumsScreen: LOADING")
        } else {
            Timber.tag("TAG").e("AlbumsScreen: SUCCESS")
            val albums = uiState.value.albums
            if (albums.isEmpty()) {
                Text("No albums found")
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2)
                ) {
                    items(albums) { album ->
                        AlbumItem(album) { albumId ->
                            navController.navigate(route = AppScreens.AlbumDetailsScreen.name+"/$albumId")
                        }
                    }
                }
            }
        }
        if (uiState.value.error != null) {
            Timber.tag("TAG").e("AlbumsScreen: ERROR")
            Text(uiState.value.error!!)
        }
    }
}

