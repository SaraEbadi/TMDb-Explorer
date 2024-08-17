package com.aparat.androidinterview.presentation.tvshows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aparat.androidinterview.domain.tvshows.SearchTvShowUseCase
import com.aparat.androidinterview.domain.tvshows.TvShowUseCase
import com.aparat.androidinterview.domain.model.ResponseList
import com.aparat.androidinterview.domain.model.TvShowResponse
import com.aparat.androidinterview.domain.model.error.NetworkError
import com.aparat.androidinterview.presentation.ui_model.TvShowItem
import com.aparat.androidinterview.presentation.ui_model.toTvShowItem
import com.aparat.androidinterview.util.ResourceProvider
import com.aparat.androidinterview.util.UiState
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
class TvShowViewModel@Inject constructor(
    private val tvShowUseCase: TvShowUseCase,
    private val searchTvShowUseCase: SearchTvShowUseCase,
    private val resourceProvider: ResourceProvider
): ViewModel() {
    private companion object {
        private const val QUERY_DEBOUNCE_IN_MILLIS = 300L
    }

    private var currentPage: Int = 0
    private var currentPageSearch: Int = 0
    private var totalPage: Int = 0
    private var searchJob: Job? = null

    private val _state: MutableStateFlow<UiState<List<TvShowItem>>> = MutableStateFlow(UiState.Loading)
    val state = _state.asStateFlow()

    private val _searchQueryText = MutableStateFlow<String?>(null)
    val searchQueryText = _searchQueryText.asStateFlow()

    init {
        fetchTvShow(1)
        startToCollectSearchQueries()
    }

    private fun fetchTvShow(currentPage: Int) {
        viewModelScope.launch {
            _state.update { UiState.Loading }
            tvShowUseCase(currentPage).fold(
                ifRight = ::success,
                ifLeft = ::failure
            )
        }
    }

    private fun searchTvShow(query: String, currentPage: Int= 1) {
        searchJob?.cancel()
        currentPageSearch = currentPage
        if (query.isBlank()) {
            this.currentPage = 1
            fetchTvShow(this.currentPage)
            return
        }
        searchJob = viewModelScope.launch {
            _state.update { UiState.Loading }
            searchTvShowUseCase(query, currentPage).fold(
                ifRight = ::success,
                ifLeft = ::failure
            )
        }
    }

    fun onLoadMore() {
        if (_state.value != UiState.Loading && currentPage != null && currentPage!! < totalPage) {
            fetchTvShow(currentPage.inc())
        }
    }

    fun onSearchLoadMore() {
        if (_state.value != UiState.Loading && !_searchQueryText.value.isNullOrBlank() && currentPageSearch!! < totalPage) {
            searchTvShow(searchQueryText.value.orEmpty(), currentPageSearch.inc())
        }
    }

    private fun success(response: ResponseList<TvShowResponse>) {
        totalPage = response.totalPages
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                _state.update { UiState.Success(response.results.map {
                    it.toTvShowItem()
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
            .onEach(::searchTvShow)
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }
}