package com.cme.songscompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cme.songscompose.screens.album_details.AlbumDetailsScreen
import com.cme.songscompose.screens.album_details.AlbumDetailsViewModel
import com.cme.songscompose.screens.albums.AlbumsScreen
import com.cme.songscompose.screens.albums.AlbumsViewModel
import com.cme.songscompose.utils.Constants

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
            route = "$route/{album_id}/{copyright}",
            arguments = listOf(
                navArgument(
                    name = Constants.Navigation.ALBUM_ID
                ) {
                    type = NavType.StringType
                },
                navArgument(
                    name = Constants.Navigation.COPYRIGHT
                ) {
                    type = NavType.StringType
                }
            )
        ) { navBack ->
            navBack.arguments?.let { bundle ->
                val albumId = bundle.getString(Constants.Navigation.ALBUM_ID)
                val copyright = bundle.getString(Constants.Navigation.COPYRIGHT)
                AlbumDetailsScreen(
                    albumId = albumId ?: "",
                    copyright = copyright ?: "",
                    viewModel = hiltViewModel<AlbumDetailsViewModel>(),
                    onBackPress = { navController.popBackStack() }
                )
            }
        }
    }
}