package me.uptop.prox.di.component

import dagger.Component
import me.uptop.prox.di.module.ActivityModule
import me.uptop.prox.di.module.AppModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    //workers and services here

    fun plus(activityModule: ActivityModule): ActivityComponent
}