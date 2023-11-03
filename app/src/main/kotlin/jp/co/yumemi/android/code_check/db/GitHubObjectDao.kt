package jp.co.yumemi.android.code_check.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import jp.co.yumemi.android.code_check.models.LocalGitHubRepoObject

@Dao
interface GitHubObjectDao {
    @Insert
    suspend fun insertGitHubObject(localGitHubRepoObject: LocalGitHubRepoObject): Long

    @Query("SELECT * FROM github_repo_table")
    fun getAllGitHubObjects(): LiveData<List<LocalGitHubRepoObject>>

    @Update
    fun updateGitHubObject(gitHubRepoObject: LocalGitHubRepoObject)

//    @Query("DELETE FROM github_repo_table WHERE id = :id")
//    suspend fun deleteGitHubObject(id:Long)
//
//    @Query("DELETE FROM github_repo_table")
//    suspend fun deleteAllGitHubObject()
}