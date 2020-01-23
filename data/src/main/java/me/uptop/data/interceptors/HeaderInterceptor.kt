package me.uptop.data.interceptors

import me.uptop.data.provider.AuthProvider
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor(private val authProvider: AuthProvider) : Interceptor {
    companion object {
        private const val TOKEN_HEADER_KEY = "Authorization"
        private const val BEARER = "Bearer"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain
            .request()
            .newBuilder()
            .maybeAddTokenHeader()
            .build()
            .let { chain.proceed(it) }
    }

    private fun Request.Builder.maybeAddTokenHeader() =
        authProvider
            .token
            ?.let { addHeader(TOKEN_HEADER_KEY, "$BEARER $it") }
            ?: this
}