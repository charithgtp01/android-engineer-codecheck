package jp.co.yumemi.android.code_check.utils

import android.content.SharedPreferences
import jp.co.yumemi.android.code_check.constants.PreferenceKeys
import jp.co.yumemi.android.code_check.constants.StringConstants
import javax.inject.Inject
/**
 * A utility class for managing shared preferences.
 *
 * @property sharedPreferences The shared preferences instance.
 */
class SharedPreferencesManager @Inject constructor() {

    companion object {
        private var sharedPreferences: SharedPreferences? = null
        /**
         * Initialize the SharedPreferencesManager with the provided SharedPreferences instance.
         *
         * @param sharedPreferences The SharedPreferences instance to be used for storing preferences.
         */
        fun init(sharedPreferences: SharedPreferences) {
            this.sharedPreferences =
                sharedPreferences

        }

        /**
         * Get the selected language value from the shared preferences.
         *
         * Default language is English.
         *
         * @return The selected language code. Returns [StringConstants.ENGLISH] if not set.
         */
        fun getSelectedLanguage(): String? =
            sharedPreferences?.getString(PreferenceKeys.LANGUAGE.value, StringConstants.ENGLISH)

        /**
         * Update the language preference value in the shared preferences.
         *
         * @param jsonString The new language code to be stored.
         */
        fun updateSelectedLanguage(
            jsonString: String?
        ) {
            sharedPreferences?.edit()?.run {
                putString(PreferenceKeys.LANGUAGE.value, jsonString)
                apply()
            }
        }
    }
}
