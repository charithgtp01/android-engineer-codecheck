package jp.co.yumemi.android.code_check.utils

import android.content.Context
import android.os.Build
import jp.co.yumemi.android.code_check.constants.StringConstants
import java.util.Locale
import java.util.Locale.setDefault

/**
 * A helper object for managing localization in the application.
 *
 * This object provides methods to update the app's locale based on the user's selected language,
 * and retrieve localized strings accordingly.
 */
object LocalHelper {
    // Constants representing language codes
    private var JAPANESE = "ja"
    private var ENGLISH = "en"

    /**
     * Updates the app's resources configuration based on the selected language using AndroidX.
     *
     * @param context The application context.
     * @return A new context with the updated configuration, or null if the update fails.
     */
    private fun updateRes(context: Context?): Context? {
        return context?.let {
            SharedPreferencesManager.getSelectedLanguage()?.let {
                val configuration = context.resources.configuration.apply {
                    Locale(getLocalLangFromSelectedLang(it)).apply {
                        setDefault(this)
                        setLocale(this)
                        setLayoutDirection(this)
                    }
                }
                context.createConfigurationContext(configuration)
            }
        }
    }

    /**
     * Updates the app's resources configuration based on the selected language using legacy methods.
     *
     * @param context The application context.
     * @return The same context with the updated configuration, or null if the update fails.
     */
    private fun updateResLegacy(context: Context?): Context? {
        return context?.let { ctx ->
            SharedPreferencesManager.getSelectedLanguage()?.let { lang ->
                val configuration = ctx.resources.configuration.apply {
                    Locale(getLocalLangFromSelectedLang(lang)).apply {
                        setDefault(this)
                        setLocale(this)
                        setLayoutDirection(this)
                    }
                }
                ctx.resources.updateConfiguration(configuration, ctx.resources.displayMetrics)
                ctx
            }
        }
    }


    /**
     * Gets the localized string for the given resource ID based on the user's selected language.
     *
     * @param context The application context.
     * @param input The resource ID of the string to be localized.
     * @return The localized string.
     * @throws IllegalStateException if the updateRes or updateResLegacy is null.
     */
    fun getString(context: Context, input: Int): String {
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> updateRes(context)?.getString(input)
                ?: throw IllegalStateException("updateRes is null")

            else -> updateResLegacy(context)?.getString(input)
                ?: throw IllegalStateException("updateResLegacy is null")
        }
    }

    /**
     * Maps the selected language to its corresponding language code.
     *
     * @param selectedLanguage The selected language.
     * @return The language code (e.g., "ja" for Japanese, "en" for English).
     */
    private fun getLocalLangFromSelectedLang(selectedLanguage: String): String =
        run {
            when (selectedLanguage) {
                StringConstants.JAPANESE -> JAPANESE
                else -> ENGLISH
            }
        }
}