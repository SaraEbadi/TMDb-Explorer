package com.aparat.androidinterview.presentation.tvshows

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.aparat.androidinterview.presentation.ui_model.TvShowItem
import kotlinx.serialization.Serializable

@Serializable
data object TvShowRoot

@Serializable
data object TvShow

@Serializable
data class TvShowDetails(
    val details: TvShowItem
)

fun NavController.navigateToTvShow(navOptions: NavOptions) = navigate(TvShow, navOptions)

fun NavGraphBuilder.tvShowScreen(navController: NavController) {

    navigation<TvShowRoot>(startDestination = TvShow) {
        composable<TvShow> {
            val viewModel: TvShowViewModel = hiltViewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()
            val searchQueryTextState = viewModel.searchQueryText.collectAsStateWithLifecycle()

            TvShowScreen(
                onLoadMore = if (searchQueryTextState.value.isNullOrBlank()) viewModel::onLoadMore else viewModel::onSearchLoadMore,
                onSearchQueryChange = viewModel::onSearchQueryChange,
                searchQueryTextState = searchQueryTextState,
                tvShowUiState = state,
                onItemClicked = {
                    navController.navigate(TvShowDetails(it))
                }
            )
        }

//        composable<MovieDetails> {
//            val movieItem = it.toRoute<MovieDetails>()
//            MovieDetailScreen(movieItem.details)
//        }
    }
}