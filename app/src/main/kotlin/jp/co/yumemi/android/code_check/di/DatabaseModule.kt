package jp.co.yumemi.android.code_check.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.co.yumemi.android.code_check.constants.StringConstants
import jp.co.yumemi.android.code_check.db.GitHubObjectDao
import jp.co.yumemi.android.code_check.db.GitHubObjectsDatabase
import jp.co.yumemi.android.code_check.repository.LocalGitHubRepository
import jp.co.yumemi.android.code_check.repository.LocalGitHubRepositoryImpl
import javax.inject.Singleton

/**
 * DatabaseModule object class is responsible for providing dependencies related to the local database setup.
 * This module provides the necessary dependencies for creating and accessing a local database
 * for storing GitHub-related objects and data.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    /**
     * Provides a singleton instance of the [GitHubObjectsDatabase] using Room Database.
     *
     * @param application The Android Application instance.
     * @return A Room database instance for storing GitHub-related data.
     */
    @Provides
    @Singleton
    fun provideGitHubObjectDatabase(application: Application): GitHubObjectsDatabase {
        return Room.databaseBuilder(
            application,
            GitHubObjectsDatabase::class.java,
            StringConstants.ROOM_DB_REPO_TABLE
        ).addMigrations(GitHubObjectsDatabase.MIGRATION_2_TO_3).build()
    }

    /**
     * Provides a singleton instance of the [GitHubObjectDao] for database operations.
     *
     * @param database The Room database instance.
     * @return A data access object for interacting with the GitHub-related data.
     */
    @Provides
    @Singleton
    fun provideGitHubObjectDao(database: GitHubObjectsDatabase): GitHubObjectDao {
        return database.gitHubObjectDao()
    }

    /**
     * Provides an implementation of the [LocalGitHubRepository] using the [GitHubObjectDao].
     *
     * @param dao The data access object for GitHub-related data.
     * @return An implementation of the local repository for accessing GitHub data.
     */
    @Provides
    @Singleton
    fun provideLocalGitHubRepository(
        application: Application,
        dao: GitHubObjectDao
    ): LocalGitHubRepository {
        return LocalGitHubRepositoryImpl(application, dao)
    }
}