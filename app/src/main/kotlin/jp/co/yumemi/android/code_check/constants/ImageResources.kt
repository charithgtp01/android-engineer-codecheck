package jp.co.yumemi.android.code_check.constants

import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.utils.SharedPreferencesManager

/**
 * Singleton object that provides image resources for different use cases in the application.
 * It allows you to retrieve image resource IDs based on predefined image codes and the selected language.
 */
object ImageResources {
    /**
     * Image code for the Git accounts search image.
     */
    const val GIT_ACCOUNT_SEARCH_IMAGE_CODE = 1

    /**
     * Image code for the "No Data" image.
     */
    const val NO_DATA_IMAGE_CODE = 2

    // Private image resource IDs
    private val GIT_ACCOUNT_SEARCH_IMAGE = R.mipmap.search_account
    private val NO_DATA_IMAGE = R.mipmap.no_data_en
    private val JP_NO_DATA_IMAGE = R.mipmap.no_data_jp

    /**
     * Retrieves the appropriate image resource ID based on the provided [imageCode] and the selected language.
     *
     * @param imageCode The code representing the desired image.
     * @return The resource ID of the requested image.
     */
    fun getImageResources(imageCode: Int): Int {
        val language = SharedPreferencesManager.getSelectedLanguage()
        return when (imageCode) {
            GIT_ACCOUNT_SEARCH_IMAGE_CODE -> GIT_ACCOUNT_SEARCH_IMAGE
            NO_DATA_IMAGE_CODE -> if (language == StringConstants.ENGLISH) NO_DATA_IMAGE else JP_NO_DATA_IMAGE
            else -> 0
        }

    }

}