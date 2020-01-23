package me.uptop.network.api

import io.reactivex.Completable
import okhttp3.Interceptor
import retrofit2.http.POST

interface SomeApi {
    @POST("some/path")
    fun sendIncidents(): Completable

    class Builder(
        baseUrl: String,
        interceptors: Array<Interceptor> = emptyArray()
    ) : ApiFactory<SomeApi>(
        baseUrl = baseUrl,
        outputClass = SomeApi::class.java,
        interceptors = interceptors
    )
}