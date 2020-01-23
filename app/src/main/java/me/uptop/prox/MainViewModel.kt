package me.uptop.prox

import me.uptop.prox.ui.base.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(): BaseViewModel<MainViewModel.State, Nothing>() {
    override fun getInitialViewState(): State = State()

    data class State(val isLoading: Boolean = false)
}
