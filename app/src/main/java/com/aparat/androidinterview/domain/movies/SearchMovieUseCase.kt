package com.aparat.androidinterview.domain.movies

import arrow.core.Either
import com.aparat.androidinterview.data.repository.movies.MoviesRepository
import com.aparat.androidinterview.domain.BaseUseCase
import com.aparat.androidinterview.domain.mapper.ErrorMapper
import com.aparat.androidinterview.domain.model.MovieResponse
import com.aparat.androidinterview.domain.model.ResponseList
import com.aparat.androidinterview.domain.model.error.NetworkError
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(private val moviesRepository: MoviesRepository, errorMapper: ErrorMapper): BaseUseCase(errorMapper) {
    suspend operator fun invoke (query: String, page: Int): Either<NetworkError, ResponseList<MovieResponse>> {
        return safeApiCall {
            moviesRepository.searchMovie(query, page)
        }
    }
}