package me.uptop.prox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewTreeObserver
import me.uptop.prox.di.Injector
import me.uptop.prox.ui.base.ISnackbarWrapper
import me.uptop.prox.ui.base.SnackbarDelegate
import me.uptop.prox.ui.routing.NavigatorCommandListener
import me.uptop.prox.utils.mvvm.error.ErrorHandler
import me.uptop.prox.utils.mvvm.error.Retrier
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MainActivity : AppCompatActivity(),
    ErrorHandler,
    ISnackbarWrapper,
    NavigatorCommandListener,
    ViewTreeObserver.OnWindowFocusChangeListener {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var router: Router

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.initAndInjectActivityComponent(this)
        super.onCreate(savedInstanceState)
        viewModel = Injector.provideViewModel(this, MainViewModel::class.java)

        setContentView(R.layout.activity_main)
    }

    private fun render(state: MainViewModel.State) {
//        progress.isShowing = state.isLoading
    }

    override fun invoke(p1: Throwable, p2: Retrier) {
    }

    override fun wrapWithSnackBarDelegate(block: SnackbarDelegate.() -> Unit) {
    }

    override fun onCommandApply() {
    }
}
