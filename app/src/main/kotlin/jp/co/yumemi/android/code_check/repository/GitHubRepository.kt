package jp.co.yumemi.android.code_check.repository

import jp.co.yumemi.android.code_check.models.ApiResponse
import javax.inject.Singleton
/**
 * Repository for interacting with the GitHub API to retrieve a list of repositories.
 *
 * This interface provides methods for fetching repositories based on a search query.
 *
 * @property value The search query string to filter repositories by.
 */
@Singleton
interface GitHubRepository {
    /**
     * Fetch a list of repositories from the GitHub API based on a search query.
     *
     * @param value The search query string.
     * @return An [ApiResponse] containing the response data from the GitHub API.
     */
    suspend fun getRepositories(
        value: String?
    ): ApiResponse
}