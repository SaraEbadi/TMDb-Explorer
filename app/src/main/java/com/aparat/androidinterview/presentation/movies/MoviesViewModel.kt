package com.aparat.androidinterview.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aparat.androidinterview.util.UiState
import com.aparat.androidinterview.domain.movies.MovieUseCase
import com.aparat.androidinterview.domain.movies.SearchMovieUseCase
import com.aparat.androidinterview.model.MovieResponse
import com.aparat.androidinterview.model.ResponseList
import com.aparat.androidinterview.model.error.NetworkError
import com.aparat.androidinterview.presentation.ui_model.MovieItem
import com.aparat.androidinterview.presentation.ui_model.toMovieItem
import com.aparat.androidinterview.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel@Inject constructor(
    private val moviesUseCase: MovieUseCase,
    private val searchMovieUseCase: SearchMovieUseCase,
    private val resourceProvider: ResourceProvider
): ViewModel() {
    private companion object {
        private const val QUERY_DEBOUNCE_IN_MILLIS = 300L
    }

    private var currentPage: Int = 0
    private var currentPageSearch: Int = 0
    private var totalPage: Int = 0
    private var searchJob: Job? = null

    private val _state: MutableStateFlow<UiState<List<MovieItem>>> = MutableStateFlow(UiState.Loading)
    val state = _state.asStateFlow()

    private val _searchQueryText = MutableStateFlow<String?>(null)
    val searchQueryText = _searchQueryText.asStateFlow()

    init {
        fetchMovies(1)
        startToCollectSearchQueries()
    }

    private fun fetchMovies(currentPage: Int) {
        viewModelScope.launch {
            _state.update { UiState.Loading }
            moviesUseCase(currentPage).fold(
                ifRight = ::success,
                ifLeft = ::failure
            )
        }
    }

    private fun searchMovie(query: String, currentPage: Int= 1) {
        searchJob?.cancel()
        currentPageSearch = currentPage
        if (query.isBlank()) {
            this.currentPage = 1
            fetchMovies(this.currentPage)
            return
        }
        searchJob = viewModelScope.launch {
            _state.update { UiState.Loading }
            searchMovieUseCase(query, currentPage).fold(
                ifRight = ::success,
                ifLeft = ::failure
            )
        }
    }

    fun onLoadMore() {
        if (_state.value != UiState.Loading && currentPage != null && currentPage!! < totalPage) {
            fetchMovies(currentPage.inc())
        }
    }

    fun onSearchLoadMore() {
        if (_state.value != UiState.Loading && !_searchQueryText.value.isNullOrBlank() && currentPageSearch!! < totalPage) {
            searchMovie(searchQueryText.value.orEmpty(), currentPageSearch.inc())
        }
    }

    private fun success(response: ResponseList<MovieResponse>) {
        totalPage = response.totalPages
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                _state.update { UiState.Success(response.results.map {
                    it.toMovieItem()
                }) }
            }
        }
    }

    private fun failure(error: NetworkError) {
        _state.update {  UiState.Error(resourceProvider.getErrorMessage(error)) }
    }

    fun onSearchQueryChange(query: String) {
        _searchQueryText.value = query
    }

    private fun startToCollectSearchQueries() {
        searchQueryText
            .filterNotNull()
            .debounce(QUERY_DEBOUNCE_IN_MILLIS)
            .onEach(::searchMovie)
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }
}