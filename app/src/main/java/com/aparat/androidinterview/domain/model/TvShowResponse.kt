package com.aparat.androidinterview.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class TvShowResponse(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val title: String,
    @Json(name = "first_air_date") val date: String,
    @Json(name = "genre_ids") val genres: List<Int>,
    @Json(name = "poster_path") val thumbnail: String,
    @Json(name = "vote_average") val voteAverage: Float,
)
