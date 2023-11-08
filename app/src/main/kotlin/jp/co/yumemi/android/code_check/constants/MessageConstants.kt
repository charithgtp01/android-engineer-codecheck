package jp.co.yumemi.android.code_check.constants

import jp.co.yumemi.android.code_check.utils.SharedPreferencesManager

object MessageConstants {
    const val SEARCH_VIEW_VALUE_EMPTY_ERROR_CODE = 2
    const val DATA_ALREADY_EXIST_CODE = 3
    const val FAV_ADDED_SUCCESS_CODE = 4
    const val FAV_DELETE_SUCCESS_CODE = 5

    private const val DATA_ALREADY_EXIST = "Data already exists"
    private const val FAV_ADDED_SUCCESS = "The account was added to favourites successfully"
    private const val FAV_DELETE_SUCCESS = "The account was deleted from favourites successfully"

    private const val JP_DATA_ALREADY_EXIST = "データはすでに存在します"
    private const val JP_FAV_ADDED_SUCCESS = "アカウントがお気に入りに追加されました"
    private const val JS_FAV_DELETE_SUCCESS = "アカウントがお気に入りから削除されました"

    fun getMessage(errorCode: Int): String {
        val language = SharedPreferencesManager.getSelectedLanguage()
        return when (errorCode) {
            DATA_ALREADY_EXIST_CODE -> if (language == StringConstants.ENGLISH) DATA_ALREADY_EXIST else JP_DATA_ALREADY_EXIST
            FAV_ADDED_SUCCESS_CODE -> if (language == StringConstants.ENGLISH) FAV_ADDED_SUCCESS else JP_FAV_ADDED_SUCCESS
            FAV_DELETE_SUCCESS_CODE -> if (language == StringConstants.ENGLISH) FAV_DELETE_SUCCESS else JS_FAV_DELETE_SUCCESS
            else -> "Unknown Error"
        }

    }
}