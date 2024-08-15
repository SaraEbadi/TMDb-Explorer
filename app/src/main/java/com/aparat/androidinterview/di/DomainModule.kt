package com.aparat.androidinterview.di

import com.aparat.androidinterview.data.repository.Repository
import com.aparat.androidinterview.data.repository.RepositoryImp
import com.aparat.androidinterview.service.MovieApi
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
    ): Repository = RepositoryImp(movieApi)
}