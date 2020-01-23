package ru.x5.mfp.utils.mvvm.error

import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import me.uptop.prox.utils.mvvm.error.Retrier
import timber.log.Timber
import java.util.concurrent.atomic.AtomicReference

class RetryObservableObserver<T : Any>(
    private val source: ObservableSource<T>,
    private val onNext: (T) -> Unit,
    private val onComplete: () -> Unit,
    private val errorHandler: (Throwable, Retrier) -> Unit
) : Observer<T>, Disposable, Retrier {

    private var currentDisposable: AtomicReference<Disposable> = AtomicReference(Disposables.empty())

    override fun onComplete() {
        onComplete.invoke()
    }

    override fun onNext(t: T) {
        onNext.invoke(t)
    }

    override fun onSubscribe(d: Disposable) {
        currentDisposable.getAndSet(d).dispose()
    }

    override fun onError(e: Throwable) {
        errorHandler(e, this)
        Timber.e(e)
    }

    fun observe(): Disposable {
        source.subscribe(this)
        return this
    }

    override fun isDisposed(): Boolean = currentDisposable.get().isDisposed

    override fun dispose() {
        currentDisposable.get().dispose()
    }

    override fun invoke() {
        source.subscribe(this)
    }
}