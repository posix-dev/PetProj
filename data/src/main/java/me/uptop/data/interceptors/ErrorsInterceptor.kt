package me.uptop.data.interceptors

import com.google.gson.Gson
import me.uptop.data.provider.NetworkAvailabilityProvider
import me.uptop.domain.error.ApiErrorException
import me.uptop.domain.error.BaseServerException
import me.uptop.domain.error.NetworkUnavailableException
import me.uptop.domain.error.UnauthorizedException
import okhttp3.Interceptor
import okhttp3.Response
import me.uptop.network.dto.common.ResponseObject
import java.net.HttpURLConnection.HTTP_FORBIDDEN
import java.net.HttpURLConnection.HTTP_INTERNAL_ERROR
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED
import javax.inject.Inject

class ErrorsInterceptor @Inject constructor(
    private val networkAvailabilityProvider: NetworkAvailabilityProvider
) : Interceptor {

    private val gson = Gson()

    private var unauthorizedErrorCallback: (() -> Unit)? = null

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!networkAvailabilityProvider.isNetworkAvailable()) {
            throw NetworkUnavailableException
        }
        val response = chain.proceed(chain.request())
        val traceId: String? = response.header("trace-id")

        when {
            response.code() in arrayOf(HTTP_UNAUTHORIZED, HTTP_FORBIDDEN) -> {
                unauthorizedErrorCallback?.invoke()
                throw UnauthorizedException("[ErrorsInterceptor]: code: ${response.code()}")
            }
            response.code() >= HTTP_INTERNAL_ERROR -> {
                throw BaseServerException(
                    code = response.code(),
                    msg = response.message(),
                    traceId = traceId,
                    requestInfo = response.request().toString()
                )
            }
            !response.isSuccessful -> {
                val parsedResponse = parseResponse(response)

                throw BaseServerException(
                    code = parsedResponse.status.code,
                    msg = parsedResponse.status.message,
                    desc = parsedResponse.status.description,
                    traceId = traceId,
                    requestInfo = response.request().toString()
                )
            }
            else -> {
                val parsedResponse = parseResponse(response)

                parsedResponse
                    .errors
                    ?.firstOrNull()
                    ?.run {
                        throw ApiErrorException(code, level, message)
                    }

                // while we are using keycloak authorization, we are forced to skip this check
                // if (parsedResponse.data == null) {
                // throw NoResponseDataProvided()
                // }
            }
        }

        return response
    }

    private fun parseResponse(response: Response): ResponseObject<*> {
        try {
            val responseBodyCopy = response.peekBody(Long.MAX_VALUE)
            return gson.fromJson(responseBodyCopy.string(), ResponseObject::class.java)
                ?: throw KotlinNullPointerException()
        } catch (ex: Throwable) {
            if (ex is UnauthorizedException || response.request().url().toString().contains("redirect_uri")) {
                // todo: this is temporary solution. If token has expired, we, as response, receive redirect page, instead of error
                unauthorizedErrorCallback?.invoke()
                throw UnauthorizedException("[ErrorsInterceptor]: When trying parse response")
            } else {
                throw ex
            }
        }
    }

    fun setUnauthorizedErrorCallback(callback: () -> Unit) {
        unauthorizedErrorCallback = callback
    }

    fun removeUnauthorizedErrorCallback() {
        unauthorizedErrorCallback = null
    }
}