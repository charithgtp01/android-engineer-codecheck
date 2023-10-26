package jp.co.yumemi.android.code_check

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import dagger.hilt.android.HiltAndroidApp
import jp.co.yumemi.android.code_check.utils.NetworkUtils

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        NetworkUtils.init(connectivityManager)
    }
}