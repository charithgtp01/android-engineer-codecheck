package jp.co.yumemi.android.code_check.models

/**
 * Room data query custom response
 * Using to show DB exceptions
 */
data class LocalDBQueryResponse(
    val success: Boolean, val message: String
)
