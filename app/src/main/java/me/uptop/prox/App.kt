package me.uptop.prox

import android.app.Application
import io.reactivex.plugins.RxJavaPlugins
import io.sentry.Sentry
import me.uptop.prox.di.Injector
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Injector.initAppComponent(this)
        Timber.plant(Timber.DebugTree())

        if (!BuildConfig.DEBUG) {
            RxJavaPlugins.setErrorHandler {
                Timber.e(it)
                Sentry.capture(it)
            }
        }
    }
}