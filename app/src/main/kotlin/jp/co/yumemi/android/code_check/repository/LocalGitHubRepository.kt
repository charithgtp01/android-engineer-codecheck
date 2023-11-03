package jp.co.yumemi.android.code_check.repository

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import jp.co.yumemi.android.code_check.constants.MessageConstants
import jp.co.yumemi.android.code_check.constants.MessageConstants.DATA_ALREADY_EXIST_CODE
import jp.co.yumemi.android.code_check.constants.MessageConstants.FAV_ADDED_SUCCESS_CODE
import jp.co.yumemi.android.code_check.constants.MessageConstants.getMessage
import jp.co.yumemi.android.code_check.db.GitHubObjectDao
import jp.co.yumemi.android.code_check.models.GitHubRepoObject
import jp.co.yumemi.android.code_check.models.LocalDBQueryResponse
import jp.co.yumemi.android.code_check.models.LocalGitHubRepoObject
import javax.inject.Inject

open class LocalGitHubRepository @Inject constructor(private val gitHubObjectDao: GitHubObjectDao) {
    suspend fun insertGitHubObject(gitHubDataClass: LocalGitHubRepoObject): LocalDBQueryResponse {
        return try {
            gitHubObjectDao.insertGitHubObject(gitHubDataClass)
            LocalDBQueryResponse(true, getMessage(FAV_ADDED_SUCCESS_CODE))
        } catch (e: SQLiteConstraintException) {
            if (e.message?.contains("UNIQUE constraint failed") == true) {
                // Handle the case where the record already exists (primary key constraint violation)
                Log.d("Android Engineer Code Check", "Data already exists")
                LocalDBQueryResponse(false, getMessage(DATA_ALREADY_EXIST_CODE))
            } else {
                // Handle other SQLiteConstraintExceptions
                // Return DB exception
                LocalDBQueryResponse(false, e.message.toString())

            }
        }

    }

    fun getAllRepositories(): LiveData<List<LocalGitHubRepoObject>> {
        return gitHubObjectDao.getAllGitHubObjects()
    }

    suspend fun updateGitHubObjectDao(gitHubDataClass: LocalGitHubRepoObject) {
        gitHubObjectDao.updateGitHubObject(gitHubDataClass)
    }

//    suspend fun deleteGitHubObjectDao(id: Int) {
//        gitHubObjectDao.deleteGitHubObject(id)
//    }
}