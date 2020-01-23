package me.uptop.prox.ui.base

import ru.terrakok.cicerone.Router

abstract class BaseRouterViewModel<ViewState, Event>(val router: Router) : BaseViewModel<ViewState, Event>() {
    fun exit() = router.exit()
}