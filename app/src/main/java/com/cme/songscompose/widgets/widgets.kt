package com.cme.songscompose.widgets

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cme.songscompose.data.ui_models.AlbumUiModel
import com.cme.songscompose.data.ui_models.GenreUiModel


@Composable
fun NetworkImage(imageUrl: String, modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.Center) {
        AsyncImage(
            modifier = modifier,
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}

@ExperimentalAnimationApi
@Composable
fun AlbumItem(album: AlbumUiModel, onItemClick: (String) -> Unit = {}) {

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .clickable {
                onItemClick(album.id)
            }
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {


            NetworkImage(
                imageUrl = album.artworkUrl100,
                modifier = Modifier
                    .fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .background(Color.Black.copy(alpha = 0.15f))
                    .padding(8.dp)
            ) {
                Text(
                    text = album.name,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text =  album.artistName,
                    color = Color.LightGray,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

@Composable
fun GenresRow(genres: List<GenreUiModel>) {
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(bottom = 8.dp)
    ) {
        genres.forEach { genre ->
            Column(modifier = Modifier.padding(end = 8.dp)) {
                Text(
                    text = genre.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .background(
                            color = Color.LightGray.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .padding(start = 8.dp, end = 8.dp)
                )
            }
        }
    }
}