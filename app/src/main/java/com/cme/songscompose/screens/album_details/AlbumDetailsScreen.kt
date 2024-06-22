package com.cme.songscompose.screens.album_details

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.cme.songscompose.data.ui_models.AlbumUiModel
import com.cme.songscompose.utils.formatDate
import com.cme.songscompose.widgets.GenresRow
import com.cme.songscompose.widgets.NetworkImage
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@Composable
fun AlbumDetailsScreen(
    albumId: String,
    copyright:String,
    viewModel: AlbumDetailsViewModel = hiltViewModel(),
    onBackPress: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsState()
    val albumUiState = uiState.value

    setBarColors()

    LaunchedEffect(albumId) {
        viewModel.getAlbumById(albumId)
    }

    when {
        albumUiState.isLoading -> {
            CircularProgressIndicator(color = Color.Black)
        }
        albumUiState.error != null -> {
            Text(
                text = albumUiState.error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
        }
        albumUiState.album != null -> {
            AlbumDetailsContent(
                album = albumUiState.album,
                copyright = copyright,
                onBackPress = onBackPress
            )
        }
    }
}


@Composable
fun AlbumDetailsContent(
    album: AlbumUiModel = AlbumUiModel.default(),
    copyright: String = "Copyright © 2024 Apple Inc. All rights reserved.",
    onBackPress: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box {

            NetworkImage(
                imageUrl = album.artworkUrl100,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            )

            // Back Icon
            IconButton(
                onClick = onBackPress,
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(start = 16.dp, top = 16.dp)
                    .background(color = Color.Gray.copy(alpha = 0.6f), shape = CircleShape)
                    .size(36.dp)
                    .align(Alignment.TopStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Album Info
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = album.name,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Gray
            )
            Text(
                text = album.artistName,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))

            GenresRow(genres = album.genres)
        }

        Spacer(modifier = Modifier.weight(1f))

        // Release Date and Copyright Information
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Released ${album.releaseDate.formatDate()}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Text(
                text = copyright,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Visit the Album Button
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(album.url))
            val context = LocalContext.current
            Button(
                onClick = {
                    context.startActivity(intent, null)
                },
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                colors = ButtonDefaults.buttonColors(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Visit The Album")
            }
        }
    }
}

@Composable
private fun setBarColors() {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()
    val statusBarLight = Color.White.copy(alpha = 0.2f)
    val statusBarDark = Color.Black

    DisposableEffect(systemUiController, useDarkIcons) {
        systemUiController.setStatusBarColor(
            color = if (useDarkIcons) {
                statusBarLight
            } else {
                statusBarDark
            },
            darkIcons = useDarkIcons
        )
        onDispose { }
    }
}