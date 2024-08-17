package com.aparat.androidinterview.presentation.ui_model

import com.aparat.androidinterview.domain.model.MovieResponse
import kotlinx.serialization.Serializable

@Serializable
data class MovieItem(
    val id: Int,
    val title: String,
    val date: String,
    val genres: List<Int>,
    val thumbnail: String,
    val voteAverage: Float,
)

internal fun MovieResponse.toMovieItem(): MovieItem {
    return MovieItem(
        id = id,
        title = title,
        date = date,
        genres = genres,
        thumbnail = thumbnail,
        voteAverage = voteAverage,
    )
}