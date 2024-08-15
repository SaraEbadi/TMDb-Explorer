package com.aparat.androidinterview.util

import android.content.Context
import androidx.annotation.StringRes
import com.aparat.androidinterview.R
import com.aparat.androidinterview.model.error.HttpError
import com.aparat.androidinterview.model.error.NetworkError
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ResourceProvider @Inject constructor(@ApplicationContext private val context: Context) : BaseResourceProvider {

    override fun getString(@StringRes id: Int): String {
        return context.getString(id)
    }

    override fun getString(@StringRes resId: Int, vararg formatArgs: Any): String {
        return context.getString(resId, *formatArgs)
    }

    override fun getErrorMessage(error: NetworkError): String {
        return when (error) {
            HttpError.ConnectionFailed -> getString(R.string.error_connection)
            is HttpError.InvalidResponse -> error.message ?: getString(R.string.error_not_defined)
            HttpError.TimeOut -> getString(R.string.error_timeout)
            HttpError.UnAuthorized -> getString(R.string.error_unauthorized)
            is NetworkError.NotDefined -> getString(R.string.error_not_defined)
            NetworkError.Null -> getString(R.string.error_null)
        }
    }
}