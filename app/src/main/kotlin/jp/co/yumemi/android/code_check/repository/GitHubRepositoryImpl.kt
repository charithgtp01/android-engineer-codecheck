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
 * Repository class to pass data to View Model
 */
class GitHubRepositoryImpl @Inject constructor(
    private val gitHubRepoApiService: GitRepoApiService
) : GitHubRepository {
    /**
     * Coroutines
     */
    override suspend fun getRepositories(
        value: String?
    ): ApiResponse {
        return withContext(Dispatchers.IO) {
            return@withContext getRepositoriesFromRemoteService(value)
        }
    }

    /**
     * @param  value: String search view text
     * @return ServerResponse Object
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
     * Deserialize error response.body
     * @param errorBody Error Response
     */
    private fun getErrorBodyFromResponse(errorBody: ResponseBody?): ErrorBody {
        val gson = Gson()
        val type = object : TypeToken<ErrorBody>() {}.type
        return gson.fromJson(errorBody?.charStream(), type)
    }

}
