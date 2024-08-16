package com.aparat.androidinterview.service

import arrow.core.Either
import arrow.retrofit.adapter.either.networkhandling.CallError
import com.aparat.androidinterview.util.PAGE
import com.aparat.androidinterview.util.QUERY
import com.aparat.androidinterview.model.ResponseList
import com.aparat.androidinterview.model.TvShowResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowApi {

    @GET("tv/{tv_id}")
    suspend fun getTvShow(
        @Path("tv_id") tvId: Int
    ): Either<CallError, TvShowResponse>

    @GET("tv/popular")
    suspend fun getPopularTvShows(
        @Query(PAGE) page: Int
    ): ResponseList<TvShowResponse>

    @GET("search/tv")
    suspend fun searchTvShows(
        @Query(QUERY) query: String,
        @Query(PAGE) page: Int
    ): ResponseList<TvShowResponse>
}
