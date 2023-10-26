package jp.co.yumemi.android.code_check.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import jp.co.yumemi.android.code_check.apiservices.GitRepoApiService
import jp.co.yumemi.android.code_check.models.ErrorBody
import jp.co.yumemi.android.code_check.models.ErrorResponse
import jp.co.yumemi.android.code_check.models.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import javax.inject.Inject

/**
 * Repository class to pass data to View Model
 */
class GitHubRepository @Inject constructor(
    private val gitHubRepoApiService: GitRepoApiService
) {
    /**
     * Coroutines
     */
    suspend fun getRepositories(
        value: String
    ): Resource? {
        return withContext(Dispatchers.IO) {
            return@withContext getRepositoriesFromRemoteService(value)
        }
    }

    /**
     * @param  value: String search view text
     * @return ServerResponse Object
     */
    private suspend fun getRepositoriesFromRemoteService(
        value: String
    ): Resource {

        /* Get Server Response */
        val response = gitHubRepoApiService.getRepositories(value)
        return if (response.isSuccessful) {
            Resource.Success(data = response.body()!!)
        } else {
            val errorObject: ErrorBody = getErrorBodyFromResponse(response.errorBody())
            Resource.Error(
                ErrorResponse(
                    errorObject.message,
                    response.code()
                )
            )
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
