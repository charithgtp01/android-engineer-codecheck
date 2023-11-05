package jp.co.yumemi.android.code_check.apiservices

import jp.co.yumemi.android.code_check.models.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Api Service for fetch Git Hub Repos from Server
 */
interface GitRepoApiService {
    companion object {
        const val REPOSITORIES_ENDPOINT = "repositories"
    }

    /**
     * @param "q" String part of the repository name
     * @see "https://api.github.com/" to get Free APIs
     */
    @GET(REPOSITORIES_ENDPOINT)
    suspend fun getRepositories(@Query("q") query: String): Response<ApiResponse>

}