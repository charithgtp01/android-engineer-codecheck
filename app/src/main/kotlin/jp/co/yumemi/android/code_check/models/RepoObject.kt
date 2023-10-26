package jp.co.yumemi.android.code_check.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Data class for Git Hub Object
 */
@Parcelize
data class RepoObject(
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

