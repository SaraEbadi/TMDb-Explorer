package com.aparat.androidinterview.domain

import arrow.core.Either
import com.aparat.androidinterview.domain.mapper.ErrorMapper
import com.aparat.androidinterview.domain.model.error.NetworkError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseUseCase(private val errorMapper: ErrorMapper) {

    protected suspend fun <T : Any> safeApiCall(call: suspend () -> T): Either<NetworkError, T> {
        return withContext(Dispatchers.IO) {
            getResult(call)
        }
    }

    private suspend fun <T : Any> getResult(call: suspend () -> T): Either<NetworkError, T> {
        return try {
            Either.Right(call.invoke())
        } catch (t: Throwable) {
            Either.Left(errorMapper.getError(t))
        }
    }
}