package me.uptop.prox.ui.base

import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import me.uptop.prox.utils.mvvm.error.Retrier
import ru.x5.mfp.utils.mvvm.error.RetryCompletableObserver
import ru.x5.mfp.utils.mvvm.error.RetryMaybeObserver
import ru.x5.mfp.utils.mvvm.error.RetryObservableObserver
import ru.x5.mfp.utils.mvvm.error.RetrySingleObserver
import me.uptop.prox.utils.mvvm.event.Event as LiveEvent

abstract class BaseViewModel<ViewState, Event> : ViewModel() {
    private val onNextStub: (Any) -> Unit = { Unit }
    private val onCompleteStub: () -> Unit = { Unit }

    private val viewStateLiveData = MutableLiveData<ViewState>()
    private val eventsLiveData = SingleLiveEvent<Event>()

    protected var viewState: ViewState
        get() = viewStateLiveData.value!!
        set(value) {
            viewStateLiveData.value = value
        }

    protected val disposables = CompositeDisposable()

    @CallSuper
    open fun initialize() {
        if (viewStateLiveData.value == null) {
            viewState = getInitialViewState()
        }
    }

    protected abstract fun getInitialViewState(): ViewState

    protected fun sendEvent(event: Event) {
        eventsLiveData.postValue(LiveEvent(event))
    }

    fun getViewStateObservable(): LiveData<ViewState> = viewStateLiveData

    fun getEventsObservable(): SingleLiveEvent<Event> = eventsLiveData

    fun <T : Any> Single<T>.subscribeRetriebleBy(
        errorHandler: (Throwable, Retrier) -> Unit,
        onSuccess: (T) -> Unit = onNextStub
    ): Disposable = RetrySingleObserver(this, onSuccess, errorHandler).observe()

    fun <T : Any> Observable<T>.subscribeRetriebleBy(
        errorHandler: (Throwable, Retrier) -> Unit,
        onComplete: () -> Unit = onCompleteStub,
        onNext: (T) -> Unit = onNextStub
    ): Disposable = RetryObservableObserver(this, onNext, onComplete, errorHandler).observe()

    fun <T : Any> Maybe<T>.subscribeRetriebleBy(
        errorHandler: (Throwable, Retrier) -> Unit,
        onComplete: () -> Unit = onCompleteStub,
        onSuccess: (T) -> Unit = onNextStub
    ): Disposable = RetryMaybeObserver(this, onSuccess, onComplete, errorHandler).observe()

    fun Completable.subscribeRetriebleBy(
        errorHandler: (Throwable, Retrier) -> Unit,
        onComplete: () -> Unit = onCompleteStub
    ): Disposable = RetryCompletableObserver(this, onComplete, errorHandler).observe()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}