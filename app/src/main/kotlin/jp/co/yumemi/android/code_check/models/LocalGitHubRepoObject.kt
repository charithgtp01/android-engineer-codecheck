package jp.co.yumemi.android.code_check.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "github_repo_table")
data class LocalGitHubRepoObject (
    @PrimaryKey
    val id: Long,
    val name: String?,
    val avatarUrl: String?,
    val language: String,
    val stargazersCount: Long?,
    val watchersCount: Long?,
    val forksCount: Long?,
    val openIssuesCount: Long?
)