package jp.co.yumemi.android.code_check.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.co.yumemi.android.code_check.db.GitHubObjectDao
import jp.co.yumemi.android.code_check.db.GitHubObjectsDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideGitHubObjectDatabase(application: Application): GitHubObjectsDatabase {
        return Room.databaseBuilder(
            application,
            GitHubObjectsDatabase::class.java,
            "github_repo_table"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideGitHubObjectDao(database: GitHubObjectsDatabase): GitHubObjectDao {
        return database.gitHubObjectDao()
    }
}