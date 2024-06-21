package com.cme.songscompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cme.songscompose.ui.screens.album_details.AlbumDetailsScreen
import com.cme.songscompose.ui.screens.albums.AlbumsScreen
import com.cme.songscompose.ui.screens.albums.AlbumsViewModel

@ExperimentalComposeUiApi
@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.AlbumsScreen.name) {
        composable(AppScreens.AlbumsScreen.name) {
            AlbumsScreen(
                navController = navController,
                viewModel = hiltViewModel<AlbumsViewModel>()
            )
        }

        val route = AppScreens.AlbumDetailsScreen.name
        composable(
            route = "$route/{album_id}",
            arguments = listOf(
                navArgument(
                    name = "album_id"
                ) {
                    type = NavType.StringType
                }
            )
        ) { navBack ->
            navBack.arguments?.getString("album_id")?.let { albumId ->
                AlbumDetailsScreen(albumId = albumId)
            }
        }
    }
}