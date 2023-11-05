package jp.co.yumemi.android.code_check.repository

import androidx.lifecycle.LiveData
import jp.co.yumemi.android.code_check.models.LocalDBQueryResponse
import jp.co.yumemi.android.code_check.models.LocalGitHubRepoObject
import javax.inject.Singleton

@Singleton
interface LocalGitHubRepository {
    suspend fun insertGitHubObject(gitHubDataClass: LocalGitHubRepoObject): LocalDBQueryResponse
    fun getAllRepositories(): LiveData<List<LocalGitHubRepoObject>>

    suspend fun deleteGitHubObjectDao(id: Long): LocalDBQueryResponse
}