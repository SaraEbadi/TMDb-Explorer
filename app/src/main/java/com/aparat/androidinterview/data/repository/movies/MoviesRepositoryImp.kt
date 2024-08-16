package com.aparat.androidinterview.data.repository

import com.aparat.androidinterview.data.repository.movies.MoviesRepository
import com.aparat.androidinterview.model.MovieResponse
import com.aparat.androidinterview.model.ResponseList
import com.aparat.androidinterview.service.MovieApi


class MoviesRepositoryImp(private val movieApi: MovieApi) : MoviesRepository {

    override suspend fun getMovies(page: Int): ResponseList<MovieResponse> {
        return movieApi.getPopularMovies(page)
    }

    override suspend fun searchMovie(query: String, page: Int): ResponseList<MovieResponse>{
        return movieApi.searchMovie(query, page)
    }

}