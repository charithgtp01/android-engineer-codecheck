package jp.co.yumemi.android.code_check

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource

object AnimationIdlingResource {

    private const val RESOURCE = "AnimationIdlingResource"

    // Using CountingIdlingResource to track the number of animations
    private val countingIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        countingIdlingResource.decrement()
    }

    fun getIdlingResource(): IdlingResource {
        return countingIdlingResource
    }
}