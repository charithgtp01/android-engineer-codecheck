package jp.co.yumemi.android.code_check.models

/**
 * Success Server Response Object
 */
data class ApiResponse(
    val items: List<GitHubRepoObject>, val success: Boolean, val message: String
) {

}
