package com.aparat.androidinterview.presentation.movies

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.aparat.androidinterview.presentation.ui_model.MovieItem
import com.aparat.androidinterview.systemdesign.navigation.CustomNavType
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable
data object MovieRoot

@Serializable
data object Movies

@Serializable
data class MovieDetails(
    val movieItem: MovieItem
)

fun NavController.navigateToMovies(navOptions: NavOptions) = navigate(Movies, navOptions)

fun NavGraphBuilder.moviesScreen(navController: NavController) {

    navigation<MovieRoot>(startDestination = Movies) {
        composable<Movies> {
            val viewModel: MoviesViewModel = hiltViewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()
            val searchQueryTextState = viewModel.searchQueryText.collectAsStateWithLifecycle()

            MoviesScreen(
                onLoadMore = if (searchQueryTextState.value.isNullOrBlank()) viewModel::onLoadMore else viewModel::onSearchLoadMore,
                onSearchQueryChange = viewModel::onSearchQueryChange,
                searchQueryTextState = searchQueryTextState,
                movieUiState = state,
                onItemClicked = {
                    navController.navigate(MovieDetails(it))
                }
            )
        }

        composable<MovieDetails>(
            typeMap = mapOf(
                typeOf<MovieItem>() to CustomNavType.movieItemType
            )
        ) {
            val route = it.toRoute<MovieDetails>()
            MovieDetailScreen(route.movieItem)
        }
    }
}