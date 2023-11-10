package jp.co.yumemi.android.code_check.models

/**
 * A data class representing a custom response for Room database queries.
 * This class is used to handle responses related to database operations and to communicate any exceptions.
 *
 * @property success A boolean indicating the success of the database operation.
 * @property message An optional error message that provides additional details in case of failure.
 */
data class LocalDBQueryResponse(
    val success: Boolean, val message: String?
)
