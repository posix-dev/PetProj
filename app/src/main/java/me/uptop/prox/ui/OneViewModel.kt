package me.uptop.prox.ui

import me.uptop.prox.ui.base.BaseViewModel
import javax.inject.Inject

class OneViewModel @Inject constructor(): BaseViewModel<OneViewModel.State, Nothing>() {

    override fun getInitialViewState(): State = State(isLoading = false)

    data class State(val isLoading: Boolean?  = null)
}