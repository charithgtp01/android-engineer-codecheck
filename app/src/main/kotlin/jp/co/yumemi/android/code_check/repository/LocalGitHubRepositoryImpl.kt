package jp.co.yumemi.android.code_check.repository

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.lifecycle.LiveData
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.db.GitHubObjectDao
import jp.co.yumemi.android.code_check.models.LocalDBQueryResponse
import jp.co.yumemi.android.code_check.models.LocalGitHubRepoObject
import jp.co.yumemi.android.code_check.utils.LocalHelper
import javax.inject.Inject

/**
 * Implementation of the LocalGitHubRepository interface for local database operations related to GitHub objects.
 *
 * This class provides methods for inserting, retrieving, and deleting GitHub objects from a local database.
 * It handles exceptions, such as SQLiteConstraintException, and returns appropriate response objects.
 *
 * @param gitHubObjectDao The Data Access Object (DAO) for GitHub objects, which is used to perform database operations.
 */
class LocalGitHubRepositoryImpl @Inject constructor(
    private val context: Context, private val gitHubObjectDao: GitHubObjectDao
) : LocalGitHubRepository {
    /**
     * Inserts a GitHub object into the local database.
     *
     * @param gitHubDataClass The GitHub object to be inserted.
     * @return A [LocalDBQueryResponse] indicating the result of the insertion operation.
     */
    override suspend fun insertGitHubObject(gitHubDataClass: LocalGitHubRepoObject): LocalDBQueryResponse {
        return try {
            gitHubObjectDao.insertGitHubObject(gitHubDataClass)
            LocalDBQueryResponse(
                true,
                LocalHelper.getString(context, R.string.add_fav_success_message)
            )
        } catch (e: SQLiteConstraintException) {
            if (e.message?.contains("UNIQUE constraint failed") == true) {
                // Handle the case where the record already exists (primary key constraint violation)
                Log.d("Android Engineer Code Check", "Data already exists")
                LocalDBQueryResponse(
                    false,
                    LocalHelper.getString(context, R.string.data_already_exist)
                )
            } else {
                // Handle other SQLiteConstraintExceptions
                // Return DB exception
                LocalDBQueryResponse(false, e.message.toString())

            }
        }

    }

    /**
     * Retrieves all GitHub repositories from the local database as a LiveData list.
     *
     * @return A [LiveData] containing a list of [LocalGitHubRepoObject] representing GitHub repositories.
     */
    override fun getAllRepositories(): LiveData<List<LocalGitHubRepoObject>>? {
        return gitHubObjectDao.getAllGitHubObjects()
    }

    /**
     * Deletes a GitHub object from the local database by its ID.
     *
     * @param id The ID of the GitHub object to be deleted.
     * @return A [LocalDBQueryResponse] indicating the result of the deletion operation.
     */
    override suspend fun deleteGitHubObjectDao(id: Long): LocalDBQueryResponse {
        return try {
            gitHubObjectDao.deleteGitHubObject(id)
            LocalDBQueryResponse(
                true,
                LocalHelper.getString(context, R.string.delete_fav_success_message)
            )
        } catch (e: SQLiteConstraintException) {
            // Handle other SQLiteConstraintExceptions
            // Return DB exception
            LocalDBQueryResponse(false, e.message.toString())
        }
    }
}