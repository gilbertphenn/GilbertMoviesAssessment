package gilbert.assessment.movies.io

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class HttpRequestInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val request = originalRequest.newBuilder().url(originalRequest.url).build()
        Log.d("url", request.toString())
        return chain.proceed(request)
    }
}