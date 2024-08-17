package com.aparat.androidinterview.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class MovieResponse(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "release_date") val date: String,
    @Json(name = "genre_ids") val genres: List<Int>,
    @Json(name = "poster_path") val thumbnail: String,
    @Json(name = "vote_average") val voteAverage: Float,
)
