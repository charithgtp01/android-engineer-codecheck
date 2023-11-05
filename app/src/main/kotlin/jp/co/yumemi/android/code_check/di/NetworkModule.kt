package jp.co.yumemi.android.code_check.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.co.yumemi.android.code_check.apiservices.GitRepoApiService
import jp.co.yumemi.android.code_check.constants.Config
import jp.co.yumemi.android.code_check.repository.GitHubRepository
import jp.co.yumemi.android.code_check.repository.GitHubRepositoryImpl
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Singleton Component Class for DI
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Get Base Url of the Rest API
     */
    @Singleton
    @Provides
    fun provideBaseUrl(): String {
        return Config.BASE_URL.value
    }

    /**
     * Create Gson Convertor Factory
     */
    @Singleton
    @Provides
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    /**
     * Create OkHttpClient
     * Add Interceptor with Headers
     */
    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        val okHttpClient =
            OkHttpClient.Builder().addInterceptor(ServiceInterceptor())
        return okHttpClient.build()
    }

    /**
     * Create Retrofit Instance
     */
    @Singleton
    @Provides
    fun provideRetrofit(
        baseUrl: String,
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()
    }

    /**
     *  Abstract the communication with the remote API
     *  Create Api Service Interface
     */
    @Singleton
    @Provides
    fun provideGithubAccountApiService(retrofit: Retrofit): GitRepoApiService {
        return retrofit.create(GitRepoApiService::class.java)
    }

    /**
     * Create abstraction layer
     * GitHubRepoApiService provide to access remote data
     */
    @Singleton
    @Provides
    fun provideGithubAccountRepository(
        gitHubRepoApiService: GitRepoApiService
    ): GitHubRepository {
        return GitHubRepositoryImpl(gitHubRepoApiService)
    }
}