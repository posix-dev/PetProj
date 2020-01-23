package me.uptop.network.api

import com.google.gson.GsonBuilder
import me.uptop.network.BuildConfig
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import me.uptop.network.converter.UtcTimeDateConverter
import me.uptop.network.security.StubbedSslSocketFactory
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit

abstract class ApiFactory<RetrofitInterface>(
    private val baseUrl: String,
    private val outputClass: Class<RetrofitInterface>,
    private val interceptors: Array<Interceptor>
) {

    companion object {
        private const val TIMEOUT_MIN = 1L
        private val gsonFactory = GsonConverterFactory.create(
            GsonBuilder()
                .serializeNulls()
                .registerTypeAdapter(Date::class.java, UtcTimeDateConverter())
                .create()
        )
    }

    fun create(): RetrofitInterface {
        val client = createHttpClient()

        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(gsonFactory)
            .build()

        return retrofit.create(outputClass)
    }

    private fun createHttpClient(): OkHttpClient {
        val builder = OkHttpClient
            .Builder()
            .readTimeout(TIMEOUT_MIN, TimeUnit.MINUTES)
            .connectTimeout(TIMEOUT_MIN, TimeUnit.MINUTES)

        interceptors.forEach {
            builder.addInterceptor(it)
        }

        if (BuildConfig.API_LOGGING_ENABLED) {
            builder.addInterceptor(
                HttpLoggingInterceptor()
                    .also {
                        it.level = HttpLoggingInterceptor.Level.BODY
                    }
            )

            builder.addInterceptor(
                BaseUrlChangingInterceptor().get()
            )
        }

        if (BuildConfig.DEBUG) {
            builder.sslSocketFactory(
                StubbedSslSocketFactory.create(),
                StubbedSslSocketFactory.TRUST_ALL_CERTS
            )
        }

        return builder.build()
    }
}

class BaseUrlChangingInterceptor : Interceptor {
    private var sInterceptor: BaseUrlChangingInterceptor? = null

    private var httpUrl: HttpUrl? = null

    fun get(): BaseUrlChangingInterceptor {
        if (sInterceptor == null) {
            sInterceptor = BaseUrlChangingInterceptor()
        }
        return sInterceptor as BaseUrlChangingInterceptor
    }

    fun setInterceptor(url: String) {
        httpUrl = HttpUrl.parse(url)
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()

        httpUrl?.let {
            original = original.newBuilder()
                .url(it)
                .build()
        }

        return chain.proceed(original)
    }
}