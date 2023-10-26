package jp.co.yumemi.android.code_check.di

import jp.co.yumemi.android.code_check.constants.Config
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Includes Network Request and Headers
 */
class ServiceInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()

        /*
         * Modify network requests and headers
         */
        val modifiedRequest: Request = originalRequest.newBuilder()
            .header("Accept", Config.API_HEADER_TYPE.value)
            .build()
        return chain.proceed(modifiedRequest)
    }

}
