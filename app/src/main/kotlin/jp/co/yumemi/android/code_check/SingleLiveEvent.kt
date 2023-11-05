package jp.co.yumemi.android.code_check

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * When rotate the device, the fragment is recreated, and it reattaches to the LiveData, causing the
 * observer to trigger again.
 *
 * To prevent this behavior, we can use the SingleLiveEvent pattern. SingleLiveEvent is a custom
 * LiveData that only delivers events once.
 */
class SingleLiveEvent<T> : MutableLiveData<T>() {
    private val pending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) {
            Log.w(
                "SingleLiveEvent",
                "Multiple observers registered but only one will be notified of changes."
            )
        }

        // Observe the internal MutableLiveData
        super.observe(owner) { value ->
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(value)
            }
        }
    }

    @MainThread
    override fun setValue(value: T?) {
        pending.set(true)
        super.setValue(value)
    }

    @MainThread
    fun call() {
        value = null
    }

    companion object {


        fun <T> LiveData<T>.observeOnce(owner: LifecycleOwner, observer: Observer<T>) {
            observe(owner) {
                observer.onChanged(it)
                removeObserver(observer)
            }
        }
    }
}