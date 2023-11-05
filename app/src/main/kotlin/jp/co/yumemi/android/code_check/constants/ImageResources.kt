package jp.co.yumemi.android.code_check.constants

import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.utils.SharedPreferencesManager

object ImageResources {
    const val GIT_ACCOUNT_SEARCH_IMAGE_CODE = 1
    const val NO_DATA_IMAGE_CODE = 2


    private val GIT_ACCOUNT_SEARCH_IMAGE = R.mipmap.search_account
    private val NO_DATA_IMAGE = R.mipmap.no_data_en

    private val JP_NO_DATA_IMAGE = R.mipmap.no_data_jp

    fun getImageResources(imageCode: Int): Int {
        val language = SharedPreferencesManager.getSelectedLanguage()
        return when (imageCode) {
            GIT_ACCOUNT_SEARCH_IMAGE_CODE -> GIT_ACCOUNT_SEARCH_IMAGE
            NO_DATA_IMAGE_CODE -> if (language == StringConstants.ENGLISH) NO_DATA_IMAGE else JP_NO_DATA_IMAGE
            else -> 0
        }

    }

}