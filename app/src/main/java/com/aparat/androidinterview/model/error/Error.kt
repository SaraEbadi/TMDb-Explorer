package com.aparat.androidinterview.model.error

sealed class NetworkError {
    /**
     * an unexpected error
     */
    data class NotDefined(val throwable: Throwable) : NetworkError()

    data object Null : NetworkError()
}

sealed class HttpError : NetworkError() {
    data object ConnectionFailed : NetworkError()

    data class InvalidResponse(val code: Int, val message: String?) : HttpError()

    data object TimeOut : NetworkError()

    data object UnAuthorized : NetworkError()
}