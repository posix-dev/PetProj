package me.uptop.prox.di.module.test

import android.app.Application
import dagger.Module
import me.uptop.prox.di.module.AppModule

@Module
internal class TestAppModule(application: Application) : AppModule(application)