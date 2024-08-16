package com.aparat.androidinterview.presentation.ui_model

import com.aparat.androidinterview.model.TvShowResponse
import kotlinx.serialization.Serializable

@Serializable
data class TvShowItem(
    val id: Int,
    val title: String,
    val date: String,
    val genres: List<Int>,
    val thumbnail: String,
    val voteAverage: Float,
)

internal fun TvShowResponse.toTvShowItem(): TvShowItem {
    return TvShowItem(
        id = id,
        title = title,
        date = date,
        genres = genres,
        thumbnail = thumbnail,
        voteAverage = voteAverage,
    )
}