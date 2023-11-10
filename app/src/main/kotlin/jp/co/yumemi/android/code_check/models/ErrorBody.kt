package jp.co.yumemi.android.code_check.models

/**
 * Data class representing an error response body message.
 *
 * This data class is used to encapsulate error messages received as response bodies from API
 * requests or other error-handling scenarios. It provides a convenient way to extract and
 * handle error messages in a structured manner.
 *
 * @property message The error message string describing the issue.
 */
data class ErrorBody(val message: String?)
