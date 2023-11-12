package jp.co.yumemi.android.code_check.repository

import androidx.lifecycle.LiveData
import jp.co.yumemi.android.code_check.models.LocalDBQueryResponse
import jp.co.yumemi.android.code_check.models.LocalGitHubRepoObject
import javax.inject.Singleton
/**
 * Repository interface for managing local GitHub repository data in a Room database.
 *
 * This interface provides methods for performing CRUD (Create, Read, Delete)
 * operations on GitHub repository objects stored in the local database.
 *
 * @see LocalGitHubRepoObject for the data class representing a GitHub repository.
 */
@Singleton
interface LocalGitHubRepository {
    /**
     * Inserts a GitHub repository object into the local database.
     *
     * @param gitHubDataClass The GitHub repository object to be inserted.
     * @return A [LocalDBQueryResponse] indicating the result of the insert operation.
     */
    suspend fun insertGitHubObject(gitHubDataClass: LocalGitHubRepoObject): LocalDBQueryResponse

    /**
     * Retrieves all GitHub repository objects stored in the local database as a LiveData list.
     *
     * @return A [LiveData] instance containing a list of [LocalGitHubRepoObject].
     */
    fun getAllRepositories(): LiveData<List<LocalGitHubRepoObject>>?

    /**
     * Deletes a GitHub repository object from the local database by its unique identifier.
     *
     * @param id The unique identifier of the GitHub repository to be deleted.
     * @return A [LocalDBQueryResponse] indicating the result of the delete operation.
     */
    suspend fun deleteGitHubObjectDao(id: Long): LocalDBQueryResponse
}