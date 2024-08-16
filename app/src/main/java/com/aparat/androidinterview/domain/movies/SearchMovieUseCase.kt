package com.aparat.androidinterview.domain.movies

import arrow.core.Either
import com.aparat.androidinterview.data.repository.Repository
import com.aparat.androidinterview.domain.BaseUseCase
import com.aparat.androidinterview.domain.mapper.ErrorMapper
import com.aparat.androidinterview.model.MovieResponse
import com.aparat.androidinterview.model.ResponseList
import com.aparat.androidinterview.model.error.NetworkError
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(private val repository: Repository, errorMapper: ErrorMapper): BaseUseCase(errorMapper) {
    suspend operator fun invoke (query: String, page: Int): Either<NetworkError, ResponseList<MovieResponse>> {
        return safeApiCall {
            repository.searchMovie(query, page)
        }
    }
}