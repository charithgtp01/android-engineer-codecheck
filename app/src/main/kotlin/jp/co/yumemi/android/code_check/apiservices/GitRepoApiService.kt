package jp.co.yumemi.android.code_check.apiservices

import jp.co.yumemi.android.code_check.models.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * An interface representing an API service for fetching GitHub repositories from a server.
 *
 * This interface provides methods for interacting with the GitHub API to retrieve repositories.
 * It includes the base URL for the GitHub API, and a method to search for repositories matching
 * a specific query.
 *
 * @see [GitHub API](https://api.github.com/)
 */
interface GitRepoApiService {
    companion object {
        /** The endpoint for fetching repositories from the GitHub API. */
        const val REPOSITORIES_ENDPOINT = "repositories"
    }

    /**
     * Fetches a list of repositories from the GitHub API that match the provided query.
     *
     * @param query A string representing part of the repository name to search for.
     * @return A [Response] object containing the API response, which may include a list of repositories.
     *
     * @see [GitHub API documentation](https://api.github.com/)
     */
    @GET(REPOSITORIES_ENDPOINT)
    suspend fun getRepositories(@Query("q") query: String?): Response<ApiResponse>

}