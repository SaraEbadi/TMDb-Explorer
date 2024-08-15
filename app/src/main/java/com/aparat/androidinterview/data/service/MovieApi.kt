package com.aparat.androidinterview.service

import arrow.core.Either
import arrow.retrofit.adapter.either.networkhandling.CallError
import com.aparat.androidinterview.PAGE
import com.aparat.androidinterview.QUERY
import com.aparat.androidinterview.model.MovieResponse
import com.aparat.androidinterview.model.ResponseList
import com.aparat.androidinterview.model.error.NetworkError
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/{movie_id}")
    suspend fun getMovie(
        @Path("movie_id") movieId: Int
    ): Either<CallError, MovieResponse>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query(PAGE) page: Int
    ): ResponseList<MovieResponse>

    @GET("search/movie")
    suspend fun searchMovie(
        @Query(QUERY) query: String,
        @Query(PAGE) page: Int
    ): Either<CallError, ResponseList<MovieResponse>>
}
