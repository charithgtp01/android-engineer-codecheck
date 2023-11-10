package jp.co.yumemi.android.code_check.di

import jp.co.yumemi.android.code_check.constants.Config
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * An OkHttp Interceptor responsible for modifying network requests and headers before they are sent.
 * It adds custom headers, such as "Accept", to the outgoing requests.
 *
 * @property chain The OkHttp Interceptor Chain to proceed with the modified request.
 */
class ServiceInterceptor : Interceptor {

    /**
     * Intercepts the network request, modifies headers, and proceeds with the request.
     *
     * @param chain The OkHttp Interceptor Chain for handling the request.
     * @return The response received after processing the modified request.
     */
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
