package me.uptop.data.provider

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import io.reactivex.Observable
import javax.inject.Inject

class NetworkAvailabilityProvider @Inject constructor(context: Context) {

    private val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun isNetworkAvailable(): Boolean = cm.activeNetworkInfo?.isConnected ?: false

    fun getConnectivityObservable(): Observable<Boolean> {
        return Observable
            .create<Boolean> { emitter ->
                val networkRequest = NetworkRequest.Builder().build()
                val callback = object : ConnectivityManager.NetworkCallback() {

                    override fun onAvailable(network: Network) {
                        emitter.onNext(true)
                    }

                    override fun onLost(network: Network) {
                        emitter.onNext(false)
                    }
                }
                cm.registerNetworkCallback(networkRequest, callback)
                emitter.setCancellable { cm.unregisterNetworkCallback(callback) }
            }
            .share()
            .distinctUntilChanged()
    }
}