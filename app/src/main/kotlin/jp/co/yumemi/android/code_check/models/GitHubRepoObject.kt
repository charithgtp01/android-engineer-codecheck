package jp.co.yumemi.android.code_check.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Data class for Git Hub Object
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
    //Set Default Value to language variable
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
        ownerType = owner?.type
    )
}

