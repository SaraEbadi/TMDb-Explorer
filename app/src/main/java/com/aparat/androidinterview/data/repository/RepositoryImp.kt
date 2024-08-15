package com.aparat.androidinterview.data.repository

import arrow.core.Either
import arrow.retrofit.adapter.either.networkhandling.CallError
import com.aparat.androidinterview.model.MovieResponse
import com.aparat.androidinterview.model.ResponseList
import com.aparat.androidinterview.model.TvShowResponse
import com.aparat.androidinterview.service.MovieApi
import com.aparat.androidinterview.service.TvShowApi


class RepositoryImp(private val movieApi: MovieApi) : Repository {

    override suspend fun getMovies(page: Int): Either<CallError, ResponseList<MovieResponse>> {
        return movieApi.getPopularMovies(page)
    }

//    override suspend fun getTvShow(page: Int):  Either<CallError,ResponseList<TvShowResponse>> {
//        return tvShowApi.getPopularTvShows(page)
//    }

}