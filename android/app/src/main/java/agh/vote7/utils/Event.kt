package agh.vote7.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

class Event<out T : Any>(private val value: T) {
    var hasBeenHandled = false
        private set

    fun consume(): T? =
        if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            value
        }

    fun peek(): T = value
}

fun <T : Any> LiveData<Event<T>>.observeEvent(owner: LifecycleOwner, observer: Observer<in T>) =
    this.observe(owner, Observer { event ->
        event.consume()?.let { observer.onChanged(it) }
    })
