package com.aparat.androidinterview.di

import com.aparat.androidinterview.data.repository.movies.MoviesRepository
import com.aparat.androidinterview.data.repository.MoviesRepositoryImp
import com.aparat.androidinterview.data.repository.shows.ShowsRepository
import com.aparat.androidinterview.data.repository.shows.ShowsRepositoryImp
import com.aparat.androidinterview.service.MovieApi
import com.aparat.androidinterview.service.TvShowApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule{

    @Provides
    @Singleton
    fun provideMovieRepository(
        movieApi: MovieApi
    ): MoviesRepository = MoviesRepositoryImp(movieApi)

    @Provides
    @Singleton
    fun provideShowsRepository(
        tvShowApi: TvShowApi
    ): ShowsRepository = ShowsRepositoryImp(tvShowApi)
}