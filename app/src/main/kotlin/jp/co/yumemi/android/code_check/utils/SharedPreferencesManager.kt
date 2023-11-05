package jp.co.yumemi.android.code_check.utils

import android.content.SharedPreferences
import jp.co.yumemi.android.code_check.constants.PreferenceKeys
import jp.co.yumemi.android.code_check.constants.StringConstants
import javax.inject.Inject

/**
 * Common Utils class for create common methods
 */
class SharedPreferencesManager @Inject constructor() {

    companion object {
        private var sharedPreferences: SharedPreferences? = null

        fun init(sharedPreferences: SharedPreferences) {
            this.sharedPreferences =
                sharedPreferences

        }

        /**
         * Get selected language value from key value
         * Default language is English
         */
        fun getSelectedLanguage(
        ): String? {
            return sharedPreferences?.getString(
                PreferenceKeys.LANGUAGE.value,
                StringConstants.ENGLISH
            )
        }

        /**
         * Update language preference value
         */
        fun updateSelectedLanguage(
            jsonString: String?
        ) {

            val editor = sharedPreferences?.edit()
            editor?.putString(PreferenceKeys.LANGUAGE.value, jsonString)
            editor?.apply()
        }
    }
}
