package com.aparat.androidinterview

sealed class UiState<out T : Any?> {
    data class Success<out T : Any?>(val value: T) : UiState<T>()
    data class Error(val errorObject: ErrorObject? = null) : UiState<Nothing>()
    object Loading : UiState<Nothing>()
}