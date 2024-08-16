package com.aparat.androidinterview.domain.movies

import com.aparat.androidinterview.data.repository.movies.MoviesRepository
import arrow.core.Either
import com.aparat.androidinterview.domain.BaseUseCase
import com.aparat.androidinterview.domain.mapper.ErrorMapper
import com.aparat.androidinterview.model.MovieResponse
import com.aparat.androidinterview.model.ResponseList
import com.aparat.androidinterview.model.error.NetworkError
import javax.inject.Inject

class MovieUseCase @Inject constructor(private val moviesRepository: MoviesRepository,
                                       errorMapper: ErrorMapper): BaseUseCase(errorMapper) {
    suspend operator fun invoke (page: Int): Either<NetworkError, ResponseList<MovieResponse>>{
        return safeApiCall {
            moviesRepository.getMovies(page)
        }
    }
}