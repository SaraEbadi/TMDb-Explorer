package com.aparat.androidinterview.data.repository.shows

import com.aparat.androidinterview.domain.model.ResponseList
import com.aparat.androidinterview.domain.model.TvShowResponse
import com.aparat.androidinterview.service.TvShowApi

class ShowsRepositoryImp(private val tvShowApi: TvShowApi): ShowsRepository {
    override suspend fun getTvShows(page: Int): ResponseList<TvShowResponse> {
        return tvShowApi.getPopularTvShows(page)
    }

    override suspend fun searchTvShows(query: String, page: Int): ResponseList<TvShowResponse> {
        return tvShowApi.searchTvShows(query, page)
    }
}