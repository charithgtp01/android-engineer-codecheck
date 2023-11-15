package jp.co.yumemi.android.code_check.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import jp.co.yumemi.android.code_check.constants.StringConstants
import jp.co.yumemi.android.code_check.models.LocalGitHubRepoObject

/**
 * This abstract class representing the local database for storing GitHub repository objects.
 * This database is used to persist and manage GitHub repository data locally.
 *
 * @param entities An array of entity classes that define the structure of the database.
 * @param version The version of the database schema. Incrementing this number triggers a database migration.
 */
@Database(entities = [LocalGitHubRepoObject::class], version = 3)
abstract class GitHubObjectsDatabase : RoomDatabase() {
    /**
     * Returns a Data Access Object (DAO) for interacting with the GitHub repository objects stored in the database.
     *
     * @return An instance of [GitHubObjectDao] for performing database operations on GitHub repository data.
     */
    abstract fun gitHubObjectDao(): GitHubObjectDao

    companion object {
        /**
         * Represents a migration from version 2 to version 3 of the database schema.
         * This migration adds a new column `htmlUrl` to the existing table.
         */
        val MIGRATION_2_TO_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Perform the necessary SQL operations to add the new column
                database.execSQL("ALTER TABLE ${StringConstants.ROOM_DB_REPO_TABLE} ADD COLUMN htmlUrl TEXT")
            }
        }
    }
}