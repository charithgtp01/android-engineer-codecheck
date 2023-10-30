package jp.co.yumemi.android.code_check

import androidx.test.espresso.IdlingResource

// Define an IdlingResource to wait for the view to be ready
class MyIdlingResource : IdlingResource {
    private var resourceCallback: IdlingResource.ResourceCallback? = null
    private var isIdle = false

    override fun getName(): String {
        return MyIdlingResource::class.java.name
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {

    }

    override fun isIdleNow(): Boolean {
        if (isIdle) {
            resourceCallback?.onTransitionToIdle()
        }
        return isIdle
    }

    fun setReady() {
        isIdle = true
    }

    fun setNotReady() {
        isIdle = false
    }

    fun registerCallback(callback: IdlingResource.ResourceCallback) {
        resourceCallback = callback
    }
}
