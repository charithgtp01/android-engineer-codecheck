package jp.co.yumemi.android.code_check.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import jp.co.yumemi.android.code_check.models.GitHubRepoObject
import jp.co.yumemi.android.code_check.models.LocalGitHubRepoObject

@Database(entities = [LocalGitHubRepoObject::class], version = 2)
abstract class GitHubObjectsDatabase : RoomDatabase() {
    abstract fun gitHubObjectDao(): GitHubObjectDao
}