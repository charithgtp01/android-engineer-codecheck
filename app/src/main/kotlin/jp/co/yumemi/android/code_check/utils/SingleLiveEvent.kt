package jp.co.yumemi.android.code_check.utils

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * A custom implementation of [MutableLiveData] that ensures only one observer
 * will be notified of changes, particularly useful for events in Android architecture components.

 * When rotate the device, the fragment is recreated, and it reattaches to the LiveData, causing the
 * observer to trigger again.
 *
 * To prevent this behavior, we can use the SingleLiveEvent pattern. SingleLiveEvent is a custom
 * LiveData that only delivers events once.
 *
 * @param T The type of data held by this instance.
 */
class SingleLiveEvent<T> : MutableLiveData<T>() {
    private val pending = AtomicBoolean(false)

    /**
     * Observes the data held by this [SingleLiveEvent] and notifies the given [observer] only once.
     * Subsequent observations will not receive updates until a new value is set.
     *
     * @param owner The lifecycle owner for this observer.
     * @param observer The observer that will be notified of data changes.
     */
    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) {
            Log.w(
                "SingleLiveEvent",
                "Multiple observers registered but only one will be notified of changes."
            )
        }

        // Observe the internal MutableLiveData
        super.observe(owner) {
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(it)
            }
        }
    }

    /**
     * Sets the given [value] as the new data and marks the event as pending.
     * Only the first observer will be notified when the data changes.
     *
     * @param value The new data value to set.
     */
    @MainThread
    override fun setValue(value: T?) {
        pending.set(true)
        super.setValue(value)
    }

    companion object {
        /**
         * Observes the data held by a [LiveData] only once, then automatically removes the observer.
         *
         * @param owner The lifecycle owner for this observer.
         * @param observer The observer that will be notified of data changes.
         */
        fun <T> LiveData<T>.observeOnce(owner: LifecycleOwner, observer: Observer<T>) {
            observe(owner) {
                observer.onChanged(it)
                removeObserver(observer)
            }
        }
    }
}