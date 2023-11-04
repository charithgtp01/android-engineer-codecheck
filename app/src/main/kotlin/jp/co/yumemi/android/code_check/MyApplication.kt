package jp.co.yumemi.android.code_check

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import androidx.preference.PreferenceManager
import dagger.hilt.android.HiltAndroidApp
import jp.co.yumemi.android.code_check.utils.NetworkUtils
import jp.co.yumemi.android.code_check.utils.SharedPreferencesManager

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        NetworkUtils.init(connectivityManager)

        val sharedPreferences: SharedPreferences = getSharedPreferences(
            getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )

        SharedPreferencesManager.init(sharedPreferences)


    }
}