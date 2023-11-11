package jp.co.yumemi.android.code_check.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import jp.co.yumemi.android.code_check.constants.StringConstants.ROOM_DB_REPO_TABLE
import jp.co.yumemi.android.code_check.models.LocalGitHubRepoObject

/**
 * Data Access Object (DAO) interface for GitHub repository objects in the Room database.
 * This interface provides methods for performing CRUD (Create, Read, Update, Delete) operations
 * on local copies of GitHub repository objects.
 */
@Dao
interface GitHubObjectDao {
    /**
     * Inserts a new GitHub repository object into the local database.
     *
     * @param localGitHubRepoObject The GitHub repository object to be inserted.
     * @return The ID of the inserted object.
     */
    @Insert
    suspend fun insertGitHubObject(localGitHubRepoObject: LocalGitHubRepoObject): Long

    /**
     * Retrieves all GitHub repository objects from the local database.
     *
     * @return A LiveData list of GitHub repository objects, allowing for real-time updates.
     */
    @Query("SELECT * FROM $ROOM_DB_REPO_TABLE")
    fun getAllGitHubObjects(): LiveData<List<LocalGitHubRepoObject>>?

    /**
     * Deletes a GitHub repository object from the local database based on its ID.
     *
     * @param id The ID of the GitHub repository object to be deleted.
     */
    @Query("DELETE FROM $ROOM_DB_REPO_TABLE WHERE id = :id")
    suspend fun deleteGitHubObject(id: Long)
}