package com.aparat.androidinterview.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aparat.androidinterview.UiState
import com.aparat.androidinterview.domain.MovieUseCase
import com.aparat.androidinterview.model.MovieResponse
import com.aparat.androidinterview.model.ResponseList
import com.aparat.androidinterview.presentation.ui_model.MovieItem
import com.aparat.androidinterview.presentation.ui_model.toMovieItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel@Inject constructor(
    val moviesUseCase: MovieUseCase
): ViewModel() {
    private companion object {
        private const val QUERY_DEBOUNCE_IN_MILLIS = 500L
    }

    private var currentPage: Int? = null
    private var totalPage: Int = 0

    private val _state: MutableStateFlow<UiState<List<MovieItem>>> = MutableStateFlow(UiState.Loading)
    val state: MutableStateFlow<UiState<List<MovieItem>>> = _state

    suspend fun fetchMovies() {
        viewModelScope.launch {
            _state.value = UiState.Loading
            moviesUseCase(currentPage?.inc() ?:0).fold(
                ifRight = { ::success },
                ifLeft = { ::failure }
            )
        }
    }

    private fun success(response: ResponseList<MovieResponse>) {
        currentPage = response.page ?: 0
        totalPage = response.totalPages ?: 0
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                _state.value = UiState.Success(response.results.map {
                    it.toMovieItem()
                })
            }
        }
    }

    private fun failure(throwable: Throwable) {
        _state.value
    }
}