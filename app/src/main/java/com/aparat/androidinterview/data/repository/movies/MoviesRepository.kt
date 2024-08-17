package com.aparat.androidinterview.data.repository.movies

import com.aparat.androidinterview.domain.model.MovieResponse
import com.aparat.androidinterview.domain.model.ResponseList


interface MoviesRepository {
    suspend fun getMovies(page: Int): ResponseList<MovieResponse>
    suspend fun searchMovie(query: String, page: Int): ResponseList<MovieResponse>
}