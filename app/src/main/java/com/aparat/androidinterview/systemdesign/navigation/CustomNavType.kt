package com.aparat.androidinterview.systemdesign.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.aparat.androidinterview.presentation.ui_model.MovieItem
import com.aparat.androidinterview.presentation.ui_model.TvShowItem
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

object CustomNavType {
    val movieItemType = object: NavType<MovieItem>(
        isNullableAllowed = false
    ) {
        override fun get(bundle: Bundle, key: String): MovieItem? {
            return Json.decodeFromString(bundle.getString(key)?: return null)
        }

        override fun parseValue(value: String): MovieItem {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: MovieItem): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: MovieItem) {
            bundle.putString(key, Json.encodeToString(value))
        }

    }

    val tvShowItemType = object: NavType<TvShowItem>(
        isNullableAllowed = false
    ) {
        override fun get(bundle: Bundle, key: String): TvShowItem? {
            return Json.decodeFromString(bundle.getString(key)?: return null)
        }

        override fun parseValue(value: String): TvShowItem {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: TvShowItem): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: TvShowItem) {
            bundle.putString(key, Json.encodeToString(value))
        }
    }
}