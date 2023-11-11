package jp.co.yumemi.android.code_check

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import dagger.hilt.android.HiltAndroidApp
import jp.co.yumemi.android.code_check.utils.NetworkUtils
import jp.co.yumemi.android.code_check.utils.SharedPreferencesManager

/**
 * Custom [Application] class for MyApplication.
 *
 * This class is annotated with [@HiltAndroidApp]
 * to enable Hilt for dependency injection throughout the application.
 */
@HiltAndroidApp
class MyApplication : Application() {
    /**
     * Called when the application is starting, before any other application objects have been created.
     */
    override fun onCreate() {
        super.onCreate()
        // Initialize NetworkUtils with the system's connectivity service.
        getSystemService(Context.CONNECTIVITY_SERVICE)
            .let { it as ConnectivityManager }
            .let { NetworkUtils.init(it) }

        // Initialize SharedPreferencesManager with the application's SharedPreferences.
        getSharedPreferences(
            getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        ).let { SharedPreferencesManager.init(it) }
    }
}