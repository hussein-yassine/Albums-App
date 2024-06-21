package com.cme.songscompose.widgets

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cme.songscompose.data.model.Album


@Composable
fun NetworkImage(imageUrl: String, modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.Center) {
        AsyncImage(
            modifier = modifier,
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .size(250)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun AlbumItem(album: Album = Album.defaultAlbum, onItemClick: (String) -> Unit = {}) {

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
