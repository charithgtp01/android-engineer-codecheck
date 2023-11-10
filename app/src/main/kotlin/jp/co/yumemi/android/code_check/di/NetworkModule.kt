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
 * NetworkModule object class is responsible for providing Dependency Injection (DI) components
 * related to network operations, including base URL, converters, HTTP client, Retrofit instance,
 * and API service interfaces.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Provides the base URL of the Rest API.
     *
     * @return Base URL as a String.
     */
    @Singleton
    @Provides
    fun provideBaseUrl(): String {
        return Config.BASE_URL.value
    }

    /**
     * Provides a Gson Converter Factory for JSON serialization and deserialization.
     *
     * @return Converter.Factory instance for Gson-based conversion.
     */
    @Singleton
    @Provides
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    /**
     * Provides an OkHttpClient instance with added interceptors for handling headers.
     *
     * @return OkHttpClient instance configured with interceptors.
     */
    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        val okHttpClient =
            OkHttpClient.Builder().addInterceptor(ServiceInterceptor())
        return okHttpClient.build()
    }

    /**
     * Provides a Retrofit instance for making network requests.
     *
     * @param baseUrl Base URL of the API.
     * @param okHttpClient OkHttpClient with interceptors.
     * @param converterFactory Converter Factory for JSON conversion.
     *
     * @return Retrofit instance configured for the given parameters.
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
     * Provides an API service interface for communicating with the remote API.
     *
     * @param retrofit Retrofit instance for API communication.
     *
     * @return GitRepoApiService for accessing remote GitHub repositories.
     */
    @Singleton
    @Provides
    fun provideGithubAccountApiService(retrofit: Retrofit): GitRepoApiService {
        return retrofit.create(GitRepoApiService::class.java)
    }

    /**
     * Provides a GitHubRepository that acts as an abstraction layer for accessing remote data.
     *
     * @param gitHubRepoApiService GitRepoApiService for accessing remote data.
     *
     * @return GitHubRepository for managing interactions with GitHub repositories.
     */
    @Singleton
    @Provides
    fun provideGithubAccountRepository(
        gitHubRepoApiService: GitRepoApiService
    ): GitHubRepository {
        return GitHubRepositoryImpl(gitHubRepoApiService)
    }
}