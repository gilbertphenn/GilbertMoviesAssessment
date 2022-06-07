package gilbert.assessment.movies.io

import gilbert.assessment.movies.BuildConfig
import gilbert.assessment.movies.utils.Config
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RestClient {

    fun getClient(): APIInterfaces {
        val logging = HttpLoggingInterceptor()
        val okClient = OkHttpClient.Builder()
        logging.level = HttpLoggingInterceptor.Level.BODY
        okClient.connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val original = chain.request()
                val method = original.method
                val body = original.body

                val request = original.newBuilder()
                    .method(method, body)
                    .build()

                return@addInterceptor chain.proceed(request)
            }

        if (BuildConfig.DEBUG) {
            okClient.addInterceptor(logging)
        }

        val client = Retrofit.Builder()
            .baseUrl(Config.BASE_URL)
            .client(okClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(AdapterFactory())
            .build()

        return client.create(APIInterfaces::class.java)
    }
}