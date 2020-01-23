package me.uptop.network.security

import android.annotation.SuppressLint
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

object StubbedSslSocketFactory {
    val TRUST_ALL_CERTS = object : X509TrustManager {

        override fun getAcceptedIssuers(): Array<X509Certificate> = emptyArray()

        @SuppressLint("TrustAllX509TrustManager")
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) = Unit

        @SuppressLint("TrustAllX509TrustManager")
        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) = Unit
    }

    fun create(): SSLSocketFactory {
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, arrayOf(TRUST_ALL_CERTS), java.security.SecureRandom())
        return sslContext.socketFactory
    }
}