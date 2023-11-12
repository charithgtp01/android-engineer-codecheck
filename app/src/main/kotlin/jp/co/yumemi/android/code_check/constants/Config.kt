package jp.co.yumemi.android.code_check.constants

/**
 * Configuration settings for your application, including base URL and API header type.
 * Use this enum to define various configuration options for your app.
 */
enum class Config(val value: String) {
    /**
     * The base URL of the application's API. This URL is used to make network requests.
     */
    BASE_URL("https://api.github.com/search/"),

    /**
     * The API header type for content negotiation. This header type specifies the desired format for API responses.
     */
    API_HEADER_TYPE("application/vnd.github.v3+json")
}