package com.aparat.androidinterview.domain.tvshows

import arrow.core.Either
import com.aparat.androidinterview.data.repository.shows.ShowsRepository
import com.aparat.androidinterview.domain.BaseUseCase
import com.aparat.androidinterview.domain.mapper.ErrorMapper
import com.aparat.androidinterview.model.ResponseList
import com.aparat.androidinterview.model.TvShowResponse
import com.aparat.androidinterview.model.error.NetworkError
import javax.inject.Inject

class TvShowUseCase @Inject constructor(private val showsRepository: ShowsRepository,
                                       errorMapper: ErrorMapper
): BaseUseCase(errorMapper) {
    suspend operator fun invoke (page: Int): Either<NetworkError, ResponseList<TvShowResponse>> {
        return safeApiCall {
            showsRepository.getTvShows(page)
        }
    }
}