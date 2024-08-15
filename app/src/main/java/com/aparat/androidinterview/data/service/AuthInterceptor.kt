package com.aparat.androidinterview.service

import com.aparat.androidinterview.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if (request.header(TOKEN_HEADER) == null) {
            request = request.newBuilder()
                .addHeader(TOKEN_HEADER, "Bearer ${BuildConfig.TOKEN}")
                .build()
        }

        return chain.proceed(request)
    }

    companion object {
        private const val TOKEN_HEADER = "Authorization"
    }
}
