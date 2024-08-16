package com.aparat.androidinterview.systemdesign.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.aparat.androidinterview.presentation.movies.MovieRoot
import com.aparat.androidinterview.presentation.movies.moviesScreen

@Composable
fun TMDbNavHost(navController : NavHostController, padding: PaddingValues) {
    NavHost(navController = navController, startDestination = MovieRoot, modifier = Modifier.padding(padding) ){
        moviesScreen(navController)
    }
}
