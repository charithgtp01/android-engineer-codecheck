package jp.co.yumemi.android.code_check.db

import androidx.room.Database
import androidx.room.RoomDatabase
import jp.co.yumemi.android.code_check.models.LocalGitHubRepoObject

/**
 * This abstract class representing the local database for storing GitHub repository objects.
 * This database is used to persist and manage GitHub repository data locally.
 *
 * @param entities An array of entity classes that define the structure of the database.
 * @param version The version of the database schema. Incrementing this number triggers a database migration.
 */
@Database(entities = [LocalGitHubRepoObject::class], version = 2)
abstract class GitHubObjectsDatabase : RoomDatabase() {
    /**
     * Returns a Data Access Object (DAO) for interacting with the GitHub repository objects stored in the database.
     *
     * @return An instance of [GitHubObjectDao] for performing database operations on GitHub repository data.
     */
    abstract fun gitHubObjectDao(): GitHubObjectDao
}