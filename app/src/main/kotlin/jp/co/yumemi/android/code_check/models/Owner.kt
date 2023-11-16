package jp.co.yumemi.android.code_check.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Data class representing the owner of a GitHub repository.
 *
 * @property avatarUrl The URL of the owner's avatar image.
 * @property type The type of the owner, e.g., "User" or "Organization."
 * @property htmlUrl Owner's profile web link.
 */
@Parcelize
data class Owner(
    @SerializedName("avatar_url") val avatarUrl: String?,
    @SerializedName("html_url") val htmlUrl: String?,
    val type: String?
) : Parcelable
