package me.uptop.prox

import android.app.Application
import io.reactivex.plugins.RxJavaPlugins
import io.sentry.Sentry
import me.uptop.prox.di.Injector
import timber.log.Timber
import java.util.concurrent.TimeUnit

class App : Application() {
    companion object {
        private val VIBRATE_INTERVAL_TIME = TimeUnit.SECONDS.toMillis(1L)
    }

    override fun onCreate() {
        super.onCreate()
        Injector.initAppComponent(this)
        Timber.plant(Timber.DebugTree())
//        maybeSetupNotificationsChannel()

//        if (BuildConfig.DEBUG) {
//            FirebaseInstanceId
//                .getInstance()
//                .instanceId
//                .addOnSuccessListener { Timber.d(it.token) }
//
//            Stetho.initializeWithDefaults(this)
//        }

        if (!BuildConfig.DEBUG) {
            RxJavaPlugins.setErrorHandler {
                Timber.e(it)
                Sentry.capture(it)
            }
        }
//
//        initSentry()
//        initGmsHandlerException()
    }
}