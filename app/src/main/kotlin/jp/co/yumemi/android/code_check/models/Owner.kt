package jp.co.yumemi.android.code_check.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Data Class for Git Hub Owner's Account
 */
@Parcelize
data class Owner(@SerializedName("avatar_url") val avatarUrl: String?) : Parcelable
