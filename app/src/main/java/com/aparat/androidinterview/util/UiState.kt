package com.aparat.androidinterview.util

sealed class UiState<out T : Any?> {
    data class Success<out T : Any?>(val value: T) : UiState<T>()
    data class Error(val message: String? = null) : UiState<Nothing>()
    data object Loading : UiState<Nothing>()
}