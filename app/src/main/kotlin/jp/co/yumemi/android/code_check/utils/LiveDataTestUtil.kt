package jp.co.yumemi.android.code_check.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

object LiveDataTestUtil {
    fun <T> getValue(liveData: LiveData<T>): T? {
        var data: T? = null
        val latch = CountDownLatch(1)

        val observer = Observer<T> {
            data = it
            latch.countDown()
        }

        liveData.observeForever(observer)

        try {
            latch.await(2, TimeUnit.SECONDS) // Adjust the timeout as needed
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } finally {
            liveData.removeObserver(observer)
        }

        return data
    }
}