package me.uptop.prox.di.module.data.network

import android.content.Context
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import me.uptop.data.interceptors.ErrorsInterceptor
import me.uptop.data.interceptors.HeaderInterceptor
import me.uptop.data.provider.NetworkAvailabilityProvider
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModuleInterceptors {
    @Provides
    @Singleton
    fun provideNetworkAvailability(context: Context) =
        NetworkAvailabilityProvider(context)

    @Provides
    @Singleton
    fun provideErrorInterceptor(networkAvailabilityProvider: NetworkAvailabilityProvider) =
        ErrorsInterceptor(networkAvailabilityProvider)

    @Provides
    @Singleton
    @Named("api")
    fun provideNetworkInterceptorsApi(errorsInterceptor: ErrorsInterceptor, headerInterceptor: HeaderInterceptor):
            Array<Interceptor> = arrayOf(errorsInterceptor, headerInterceptor)

    @Provides
    @Singleton
    @Named("info")
    fun provideNetworkInterceptorsInfo(errorsInterceptor: ErrorsInterceptor):
        Array<Interceptor> = arrayOf(errorsInterceptor)
}