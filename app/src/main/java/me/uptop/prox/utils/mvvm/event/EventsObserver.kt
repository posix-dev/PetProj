package me.uptop.prox.utils.mvvm.event

import androidx.lifecycle.Observer

class EventsObserver<T>(private val onEvent: (T) -> Unit) : Observer<Event<T>> {

    override fun onChanged(event: Event<T>?) {
        event
            ?.getContentIfNotHandled()
            ?.let(onEvent)
    }
}