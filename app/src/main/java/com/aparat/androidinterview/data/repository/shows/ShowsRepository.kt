package com.aparat.androidinterview.data.repository.shows

import com.aparat.androidinterview.domain.model.ResponseList
import com.aparat.androidinterview.domain.model.TvShowResponse

interface ShowsRepository {
    suspend fun getTvShows(page: Int): ResponseList<TvShowResponse>
    suspend fun searchTvShows(query: String, page: Int): ResponseList<TvShowResponse>
}