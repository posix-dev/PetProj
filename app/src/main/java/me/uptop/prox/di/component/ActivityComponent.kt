package me.uptop.prox.di.component

import androidx.lifecycle.ViewModelProvider
import dagger.Subcomponent
import me.uptop.prox.MainActivity
import me.uptop.prox.di.module.ActivityModule
import me.uptop.prox.di.scope.ActivityScope

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(mainActivity: MainActivity)

    fun getViewModelFactory(): ViewModelProvider.Factory
}