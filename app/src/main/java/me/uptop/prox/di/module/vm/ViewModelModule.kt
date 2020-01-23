package me.uptop.prox.di.module.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import me.uptop.prox.MainViewModel
import me.uptop.prox.ui.OneViewModel
import ru.x5.mfp.di.module.vm.ViewModelFactory

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OneViewModel::class)
    fun bindOneViewModel(viewModel: OneViewModel): ViewModel

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}