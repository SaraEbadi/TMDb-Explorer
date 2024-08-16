package com.aparat.androidinterview.data.repository.shows

import com.aparat.androidinterview.model.ResponseList
import com.aparat.androidinterview.model.TvShowResponse

interface ShowsRepository {
    suspend fun getTvShows(page: Int): ResponseList<TvShowResponse>
    suspend fun searchTvShows(query: String, page: Int): ResponseList<TvShowResponse>
}