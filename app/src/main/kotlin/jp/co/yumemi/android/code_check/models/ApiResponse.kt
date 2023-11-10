package jp.co.yumemi.android.code_check.models

/**
 * Represents a response from a server request, indicating the success status along with associated data.
 *
 * @property items The list of data items contained in the response.
 * @property success A boolean flag indicating whether the request was successful.
 * @property message A descriptive message providing additional information about the response.
 */
data class ApiResponse(
    val items: List<GitHubRepoObject>?, val success: Boolean, val message: String?
)
