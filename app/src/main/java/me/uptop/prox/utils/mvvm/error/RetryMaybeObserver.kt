package ru.x5.mfp.utils.mvvm.error

import io.reactivex.MaybeObserver
import io.reactivex.MaybeSource
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import me.uptop.prox.utils.mvvm.error.Retrier
import timber.log.Timber
import java.util.concurrent.atomic.AtomicReference

class RetryMaybeObserver<T : Any>(
    private val source: MaybeSource<T>,
    private val onSuccess: (T) -> Unit,
    private val onComplete: () -> Unit,
    private val errorHandler: (Throwable, Retrier) -> Unit
) : MaybeObserver<T>, Disposable, Retrier {

    private val currentDisposable = AtomicReference(Disposables.empty())

    override fun onComplete() {
        onComplete.invoke()
    }

    override fun onSuccess(t: T) {
        onSuccess.invoke(t)
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