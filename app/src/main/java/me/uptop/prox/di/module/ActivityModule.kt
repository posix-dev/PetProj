package me.uptop.prox.di.module

import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import me.uptop.prox.MainActivity
import me.uptop.prox.utils.mvvm.error.ErrorHandler
import me.uptop.prox.di.module.vm.ViewModelModule

@Module(includes = [ViewModelModule::class])
class ActivityModule(private val activity: MainActivity) {

    @Provides
    fun provideActivity(): AppCompatActivity = activity

    @Provides
    fun provideErrorHandler(): ErrorHandler = activity
}