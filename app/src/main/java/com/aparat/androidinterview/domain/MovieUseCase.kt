package com.aparat.androidinterview.domain

import com.aparat.androidinterview.data.repository.Repository
import arrow.core.Either
import arrow.retrofit.adapter.either.networkhandling.CallError
import com.aparat.androidinterview.domain.mapper.ErrorMapper
import com.aparat.androidinterview.model.MovieResponse
import com.aparat.androidinterview.model.ResponseList
import com.aparat.androidinterview.model.error.NetworkError
import javax.inject.Inject
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Private

class MovieUseCase @Inject constructor(private val repository: Repository,
                                       errorMapper: ErrorMapper): BaseUseCase(errorMapper) {
    suspend operator fun invoke (page: Int): Either<NetworkError, ResponseList<MovieResponse>>{
        return safeApiCall {
            repository.getMovies(page)
        }
    }
}