package agh.vote7.data

import agh.vote7.BuildConfig
import agh.vote7.VoteApplication
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

const val BASE_URL = "https://vote7.example.com/" // TODO(pjanczyk): use real URL

object RestApiProvider {
    val restApi: RestApi by lazy { restApi() }

    private fun restApi(): RestApi =
        retrofit()
            .create()

    private fun retrofit(): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(BASE_URL)
            .client(httpClient())
            .build()

    private fun httpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor())
            .addInterceptor(loggingInterceptor())
            .build()

    private fun tokenInterceptor(): Interceptor =
        Interceptor { chain ->
            val request = chain.request()

            val urlBuilder = request.url().newBuilder()
            VoteApplication.token?.let {
                urlBuilder.addQueryParameter("token", it)
            }
            val modifiedUrl = urlBuilder.build()

            val modifiedRequest = request
                .newBuilder()
                .url(modifiedUrl)
                .build()

            chain.proceed(modifiedRequest)
        }

    private fun loggingInterceptor(): Interceptor =
        LoggingInterceptor.Builder()
            .loggable(BuildConfig.DEBUG)
            .setLevel(Level.BASIC)
            .log(Platform.INFO)
            .request("Request")
            .response("Response")
            .enableAndroidStudio_v3_LogsHack(true)
            .build()
}
