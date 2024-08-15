package com.aparat.androidinterview.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aparat.androidinterview.UiState
import com.aparat.androidinterview.domain.MovieUseCase
import com.aparat.androidinterview.model.MovieResponse
import com.aparat.androidinterview.model.ResponseList
import com.aparat.androidinterview.model.error.NetworkError
import com.aparat.androidinterview.presentation.ui_model.MovieItem
import com.aparat.androidinterview.presentation.ui_model.toMovieItem
import com.aparat.androidinterview.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel@Inject constructor(
    private val moviesUseCase: MovieUseCase,
    private val resourceProvider: ResourceProvider
): ViewModel() {
    private companion object {
        private const val QUERY_DEBOUNCE_IN_MILLIS = 500L
    }

    private var currentPage: Int? = null
    private var totalPage: Int = 0

    private val _state: MutableStateFlow<UiState<List<MovieItem>>> = MutableStateFlow(UiState.Loading)
    val state: MutableStateFlow<UiState<List<MovieItem>>> = _state

    fun fetchMovies() {
        viewModelScope.launch {
            _state.value = UiState.Loading
            moviesUseCase(currentPage?.inc() ?:0).fold(
                ifRight = ::success,
                ifLeft = ::failure
            )
        }
    }

    fun onLoadMore() {
        if (_state.value != UiState.Loading && currentPage != null && currentPage!! < totalPage) {
            fetchMovies()
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

    private fun failure(error: NetworkError) {
        _state.value = UiState.Error(resourceProvider.getErrorMessage(error))
    }
}