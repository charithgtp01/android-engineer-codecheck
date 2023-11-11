package jp.co.yumemi.android.code_check

import android.content.Context
import android.os.Build
import jp.co.yumemi.android.code_check.constants.StringConstants
import jp.co.yumemi.android.code_check.utils.SharedPreferencesManager
import java.util.Locale

object LocalHelper {
    private var JAPANESE = "ja"
    private var ENGLISH = "en"
    private fun updateRes(context: Context?): Context? {

        return if (context == null) {
            null
        } else {
            val lang = SharedPreferencesManager.getSelectedLanguage()
                ?.let { getLocalLangFromSelectedLang(it) }
            val locale = Locale(lang)
            Locale.setDefault(locale)
            val configuration = context.resources.configuration.apply {
                setLocale(locale)
                setLayoutDirection(locale)
            }
            context.createConfigurationContext(configuration)
        }
    }

    private fun updateResLegacy(context: Context?): Context? {
        return when (context) {
            null -> null
            else -> {
                val lang = SharedPreferencesManager.getSelectedLanguage()
                    ?.let { getLocalLangFromSelectedLang(it) }
                val locale = Locale(lang)
                Locale.setDefault(locale)
                val resources = context.resources
                val configuration = resources.configuration.apply {
                    setLocale(locale)
                    setLayoutDirection(locale)
                }
                resources.updateConfiguration(configuration, resources.displayMetrics)
                context
            }
        }
    }

    fun getString(context: Context, input: Int): String {
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> updateRes(context)?.getString(input)
                ?: throw IllegalStateException("updateRes is null")

            else -> updateResLegacy(context)?.getString(input) ?: throw IllegalStateException("updateResLegacy is null")
        }
    }


    private fun getLocalLangFromSelectedLang(selectedLanguage: String): String {
        return when (selectedLanguage) {
            StringConstants.JAPANESE -> JAPANESE
            else -> ENGLISH
        }
    }
}