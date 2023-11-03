package jp.co.yumemi.android.code_check.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import jp.co.yumemi.android.code_check.constants.StringConstants

@Entity(tableName = StringConstants.ROOM_DB_REPO_TABLE)
data class LocalGitHubRepoObject(
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