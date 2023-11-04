package jp.co.yumemi.android.code_check.repository

import jp.co.yumemi.android.code_check.models.ApiResponse
import javax.inject.Singleton

@Singleton
interface GitHubRepository {
    suspend fun getRepositories(
        value: String
    ): ApiResponse
}