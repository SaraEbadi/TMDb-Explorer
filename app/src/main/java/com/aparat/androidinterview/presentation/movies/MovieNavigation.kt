package com.aparat.androidinterview.presentation.movies

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kotlinx.serialization.Serializable

@Serializable
data object MovieRoot

@Serializable
data object Movies

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
    }
}