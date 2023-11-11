package jp.co.yumemi.android.code_check.constants

/**
 * Enum class defining keys used for storing and retrieving preferences.
 *
 * This enum provides a set of keys to access specific preferences in the application's settings.
 * Each key is associated with a corresponding preference value and should be used for consistency
 * when accessing or modifying preferences.
 *
 * @property value The String value associated with the preference key.
 */
enum class PreferenceKeys(val value: String) {
    /**
     * Key for storing and retrieving the selected language preference.
     * The value associated with this key represents the user's chosen language.
     */
    LANGUAGE("language")
}