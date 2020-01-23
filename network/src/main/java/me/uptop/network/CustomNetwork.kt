package me.uptop.network

import me.uptop.network.api.SomeApi
import me.uptop.network.apistub.SomeApiStub
import okhttp3.Interceptor
import javax.inject.Inject
import javax.inject.Named

interface MFPNetwork {

    val someApi: SomeApi
}

class MFPNetworkImpl @Inject constructor(
    @Named("api") val interceptors: Array<Interceptor>,
    @Named("info") val interceptorsInfo: Array<Interceptor>
) : MFPNetwork {

    override val someApi: SomeApi by lazy {
        SomeApi.Builder(
            baseUrl = BuildConfig.API_FLEET_URL,
            interceptors = interceptors
        ).create()
    }

}

class MFPNetworkStubImpl @Inject constructor(
    @Named("api") val interceptors: Array<Interceptor>,
    @Named("info") val interceptorsInfo: Array<Interceptor>
) : MFPNetwork {

    override val someApi: SomeApi by lazy {
        SomeApiStub()
    }
}

class MFPNetworkTestImpl @Inject constructor(
    @Named("api") val interceptors: Array<Interceptor>,
    @Named("info") val interceptorsInfo: Array<Interceptor>
) : MFPNetwork {

    companion object {
        private const val LOCAL_HOST = "http://127.0.0.1:8607/"
    }

    override val someApi: SomeApi by lazy {
        SomeApi.Builder(
            baseUrl = LOCAL_HOST,
            interceptors = interceptors
        ).create()
    }
}