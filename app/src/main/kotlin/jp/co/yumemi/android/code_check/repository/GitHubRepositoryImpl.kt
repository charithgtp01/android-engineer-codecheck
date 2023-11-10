package jp.co.yumemi.android.code_check.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import jp.co.yumemi.android.code_check.apiservices.GitRepoApiService
import jp.co.yumemi.android.code_check.models.ApiResponse
import jp.co.yumemi.android.code_check.models.ErrorBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import javax.inject.Inject

/**
 * Repository class responsible for fetching GitHub repository data and passing it to View Models.
 *
 * This class interacts with the GitHub API service to retrieve repository data based on a search query.
 * It handles the API response, deserialization of error responses, and returns the data to View Models
 * in a structured [ApiResponse] object.
 *
 * @property gitHubRepoApiService The API service used to communicate with the GitHub API.
 */
class GitHubRepositoryImpl @Inject constructor(
    private val gitHubRepoApiService: GitRepoApiService
) : GitHubRepository {
    /**
     * Asynchronously fetches repositories from the remote GitHub API.
     *
     * @param value Search query text entered in the search view.
     * @return An [ApiResponse] object containing the result of the operation, including data or error details.
     */
    override suspend fun getRepositories(
        value: String?
    ): ApiResponse {
        return withContext(Dispatchers.IO) {
            return@withContext getRepositoriesFromRemoteService(value)
        }
    }

    /**
     * Fetches GitHub repositories from the remote API service and handles the response.
     *
     * This function makes an API request to the GitHub API and processes the response to create
     * an [ApiResponse] object. It includes the data if the request is successful or an error message
     * in case of failure.
     *
     * @param value Search query text entered in the search view.
     * @return An [ApiResponse] object containing the result of the operation.
     */
    private suspend fun getRepositoriesFromRemoteService(
        value: String?
    ): ApiResponse {

        /* Get Server Response */
        val response = gitHubRepoApiService.getRepositories(value)

        return if (response.isSuccessful) {
            ApiResponse(
                success = true,
                message = "Data fetched Successfully",
                items = response.body()?.items
            )
        } else {
            val errorObject: ErrorBody = getErrorBodyFromResponse(response.errorBody())
            ApiResponse(success = false, message = errorObject.message, items = listOf())
        }
    }

    /**
     * Deserialize error response.body.
     *
     * This function deserializes the error response body and extracts error details
     * from the response using GSON.
     *
     * @param errorBody Error Response as a [ResponseBody] object.
     * @return An [ErrorBody] object containing error details.
     */
    private fun getErrorBodyFromResponse(errorBody: ResponseBody?): ErrorBody {
        val gson = Gson()
        val type = object : TypeToken<ErrorBody>() {}.type
        return gson.fromJson(errorBody?.charStream(), type)
    }

}
