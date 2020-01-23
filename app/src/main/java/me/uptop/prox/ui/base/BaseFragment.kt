package me.uptop.prox.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import me.uptop.uikit.ProgressDialog

abstract class BaseFragment<ViewModel : BaseViewModel<ViewState, Event>, ViewState, Event> :
    Fragment(), SnackbarDelegate {
    @get:LayoutRes
    protected abstract val layoutRes: Int

    private val progress by lazy { ProgressDialog(requireContext()) }

    protected lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = provideViewModel()
        viewModel.initialize()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getViewStateObservable()
            .observe(viewLifecycleOwner, Observer {
                render(it ?: return@Observer)
            })

        viewModel.getEventsObservable()
            .observe(viewLifecycleOwner, Observer {
                handleEvent(it.getContentIfNotHandled() ?: return@Observer)
            })
    }

    protected fun setProgressShowing(isShowing: Boolean) {
        progress.isShowing = isShowing
    }

    protected fun showProgressNow() {
        progress.showNow()
    }

    fun getProgressDialog(): ProgressDialog = progress

    override fun onStop() {
        progress.dismissNow()
        super.onStop()
    }

    override fun systemMessage(@StringRes message: Int, sticky: Boolean, duration: Int) {
        activity?.let {
            (it as ISnackbarWrapper).wrapWithSnackBarDelegate {
                systemMessage(message, sticky, duration)
            }
        }
    }

    override fun actionSystemMessage(
        @StringRes message: Int,
        @StringRes actionMessage: Int,
        sticky: Boolean,
        duration: Int,
        action: () -> Unit
    ) {
        activity?.let {
            (it as ISnackbarWrapper).wrapWithSnackBarDelegate {
                actionSystemMessage(message, actionMessage, sticky, duration, action)
            }
        }
    }

    override fun retriableSystemMessage(
        @StringRes message: Int,
        sticky: Boolean,
        duration: Int,
        retry: () -> Unit
    ) {
        activity?.let {
            (it as ISnackbarWrapper).wrapWithSnackBarDelegate {
                retriableSystemMessage(message, sticky, duration, retry)
            }
        }
    }

    override fun retriableSystemMessage(
        message: String,
        sticky: Boolean,
        duration: Int,
        retry: () -> Unit
    ) {
        activity?.let {
            (it as ISnackbarWrapper).wrapWithSnackBarDelegate {
                retriableSystemMessage(message, sticky, duration, retry)
            }
        }
    }

    protected abstract fun provideViewModel(): ViewModel

    protected abstract fun render(state: ViewState)

    protected open fun handleEvent(event: Event) = Unit
}