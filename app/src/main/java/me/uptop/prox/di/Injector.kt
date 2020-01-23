package me.uptop.prox.di

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import me.uptop.prox.MainActivity
import me.uptop.prox.di.component.ActivityComponent
import me.uptop.prox.di.component.AppComponent
import me.uptop.prox.di.component.DaggerAppComponent
import me.uptop.prox.di.module.ActivityModule
import me.uptop.prox.di.module.AppModule
import me.uptop.prox.di.module.test.TestAppModule

object Injector {
    private lateinit var appComponent: AppComponent
    private lateinit var activityComponent: ActivityComponent

    fun initAppComponent(application: Application) {
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(application))
            .build()
    }

    fun initTestAppComponent(application: Application) {
        appComponent = DaggerAppComponent
            .builder()
            .appModule(TestAppModule(application))
            .build()
    }

    fun initAndInjectActivityComponent(activity: MainActivity) {
        activityComponent = appComponent.plus(ActivityModule(activity))
        activityComponent.inject(activity)
    }

    fun <VM : ViewModel> provideViewModel(fragment: Fragment, type: Class<VM>): VM =
        ViewModelProviders
            .of(
                fragment,
                activityComponent.getViewModelFactory()
            )
            .get(type)

    fun <VM : ViewModel> provideViewModel(activity: AppCompatActivity, type: Class<VM>): VM =
        ViewModelProviders
            .of(
                activity,
                activityComponent.getViewModelFactory()
            )
            .get(type)
}