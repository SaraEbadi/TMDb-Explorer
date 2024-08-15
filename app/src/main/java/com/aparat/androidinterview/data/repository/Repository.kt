package com.aparat.androidinterview.data.repository

import arrow.core.Either
import arrow.retrofit.adapter.either.networkhandling.CallError
import com.aparat.androidinterview.model.MovieResponse
import com.aparat.androidinterview.model.ResponseList
import com.aparat.androidinterview.model.TvShowResponse


interface Repository {

    suspend fun getMovies(page: Int): ResponseList<MovieResponse>
//    suspend fun getTvShow(page: Int): Either<CallError, ResponseList<TvShowResponse>>
}