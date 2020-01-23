package me.uptop.prox.di.module.data.network

import dagger.Binds
import dagger.Module
import dagger.Provides
import me.uptop.data.NetworkType
import me.uptop.domain.BuildConfigQualifier
import me.uptop.network.MFPNetwork
import me.uptop.network.MFPNetworkImpl
import me.uptop.network.MFPNetworkStubImpl
import me.uptop.network.MFPNetworkTestImpl
import javax.inject.Named
import javax.inject.Singleton

//@Module(includes = [NetworkModule.NetworkModuleBindings::class])
@Module
open class NetworkModule {

//    @Provides
//    @BuildConfigQualifier
//    fun provideMFPNetwork(
//        @Named(NetworkType.NETWORK_STUB) stub: MFPNetwork,
//        @Named(NetworkType.NETWORK_LIVE) live: MFPNetwork,
//        @Named(NetworkType.NETWORK_TEST) test: MFPNetwork
//    ): MFPNetwork {
//        return when (BuildConfig.NETWORK_TYPE) {
//            NetworkType.NETWORK_STUB -> stub
//            NetworkType.NETWORK_LIVE -> live
//            NetworkType.NETWORK_TEST -> test
//            else -> live
//        }
//    }
//
//    @Module(includes = [NetworkModuleInterceptors::class])
//    interface NetworkModuleBindings {
//
//        @Binds
//        @Singleton
//        @Named(NetworkType.NETWORK_STUB)
//        fun bindNetworkStub(mfpNetworkStubImpl: MFPNetworkStubImpl): MFPNetwork
//
//        @Binds
//        @Singleton
//        @Named(NetworkType.NETWORK_LIVE)
//        fun bindNetwork(mfpNetworkImpl: MFPNetworkImpl): MFPNetwork
//
//        @Binds
//        @Singleton
//        @Named(NetworkType.NETWORK_TEST)
//        fun bindNetworkTest(mfpNetworkTestImpl: MFPNetworkTestImpl): MFPNetwork
//    }
}