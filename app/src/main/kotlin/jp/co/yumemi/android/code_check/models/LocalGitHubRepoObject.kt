package jp.co.yumemi.android.code_check.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import jp.co.yumemi.android.code_check.constants.StringConstants

/**
 * Represents a local database entity for storing GitHub repository information.
 *
 * This class is used to define the structure of a table in a local database where GitHub repository
 * information is stored.
 *
 * @param id The unique identifier of the repository.
 * @param name The name of the GitHub repository.
 * @param avatarUrl The URL of the repository owner's avatar image.
 * @param ownerType The type of the repository owner (e.g., User, Organization).
 * @param language The primary programming language used in the repository.
 * @param stargazersCount The number of users who have starred the repository.
 * @param watchersCount The number of users who are watching the repository.
 * @param forksCount The number of forks created from the repository.
 * @param openIssuesCount The number of open issues in the repository.
 */
@Entity(tableName = StringConstants.ROOM_DB_REPO_TABLE)
data class LocalGitHubRepoObject(
    @PrimaryKey
    val id: Long,
    val name: String?,
    val avatarUrl: String?,
    val htmlUrl: String?,
    val ownerType: String?,
    val language: String?,
    val stargazersCount: Long?,
    val watchersCount: Long?,
    val forksCount: Long?,
    val openIssuesCount: Long?
)