package jp.co.yumemi.android.code_check.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Data class representing a GitHub repository object.
 *
 * This class encapsulates information about a GitHub repository, including its ID, name, owner,
 * programming language, stargazers count, watchers count, forks count, and open issues count.
 * Additionally, it provides a default value for the programming language in case it's null or empty.
 *
 * @property id The unique identifier of the repository.
 * @property name The name of the repository.
 * @property owner The owner of the repository, which includes details like avatar URL and type.
 * @property nullableLanguage The programming language used in the repository (nullable).
 * @property stargazersCount The number of users who have starred the repository.
 * @property watchersCount The number of users who are watching the repository.
 * @property forksCount The number of forks created from the repository.
 * @property openIssuesCount The number of open issues in the repository.
 * @property language The programming language used in the repository, with a default value of "No Language Data"
 *
 * @see LocalGitHubRepoObject Use the `toGitHubDataClass` extension function to convert this object to a local data class.
 */
@Parcelize
data class GitHubRepoObject(
    val id: Long,
    val name: String?,
    val owner: Owner?,
    @SerializedName("language")
    private val nullableLanguage: String?,
    @SerializedName("stargazers_count")
    val stargazersCount: Long?,
    @SerializedName("watchers_count")
    val watchersCount: Long?,
    @SerializedName("forks_count")
    val forksCount: Long?,
    @SerializedName("open_issues_count")
    val openIssuesCount: Long?,
) : Parcelable {
    val language: String
        get() = nullableLanguage ?: "No Language Data"
}

fun GitHubRepoObject.toGitHubDataClass(): LocalGitHubRepoObject {
    return LocalGitHubRepoObject(
        id = id, // Set the ID as needed
        name = name ?: "",
        language = language,
        stargazersCount = stargazersCount,
        watchersCount = watchersCount,
        forksCount = forksCount,
        openIssuesCount = openIssuesCount,
        avatarUrl = owner?.avatarUrl,
        ownerType = owner?.type,
        htmlUrl = owner?.htmlUrl,
    )
}

