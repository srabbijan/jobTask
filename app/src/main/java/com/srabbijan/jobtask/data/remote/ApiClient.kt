package com.srabbijan.jobtask.data.remote

import android.os.Bundle
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.srabbijan.jobtask.BuildConfig
import com.srabbijan.jobtask.utils.Constants
import com.srabbijan.jobtask.utils.Constants.APP_NAME
import com.srabbijan.jobtask.utils.Constants.APP_PLATFORM
import com.srabbijan.jobtask.utils.Constants.KEY_HEADER_APP_NAME
import com.srabbijan.jobtask.utils.Constants.KEY_HEADER_PLATFORM
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiClient {
    private const val connectionTimeout: Long = 25L
    private const val writeTimeout: Long = 25L
    private const val readTimeout: Long = 25L


    @Provides
    fun provideApi(): ApiServices {
        return getRetrofitClient(
            Constants.BASE_URL
        ).create(ApiServices::class.java)
    }

    @Provides
    @Singleton
    fun getRetrofitClient(baseUrl: String): Retrofit {

        val httpClient: OkHttpClient
        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level =
                HttpLoggingInterceptor.Level.BODY // Covers both Header & Body
        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        }

        httpClient = OkHttpClient.Builder()
//            .authenticator(authenticator = AuthAuthenticator())
            .addNetworkInterceptor(RESPONSE_INTERCEPTOR)
            .addNetworkInterceptor(REWRITE_RESPONSE_INTERCEPTOR)
            .connectTimeout(connectionTimeout, TimeUnit.SECONDS)
            .writeTimeout(writeTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader(KEY_HEADER_PLATFORM, APP_PLATFORM)
                    .addHeader(KEY_HEADER_APP_NAME, APP_NAME)
//                    .addHeader(KEY_HEADER_LOCALE, language)
//                    .addHeader(KEY_HEADER_DEVICE_ID, Utils.getAndroidId())
//                    .addHeader(KEY_HEADER_DEVICE_INFO, Utils.getAndroidDeviceInfo())
//                    .addHeader(KEY_HEADER_APP_VERSION, Utils.getAppVersionCode().toString())
                    .build()
                chain.proceed(request)
            }
//            .addInterceptor(responseCachingInterceptor)
//            .addInterceptor(interceptor)
            .addInterceptor(loggingInterceptor) // Don't add multiple logging interceptors
//            .addInterceptor(
//                ChuckerInterceptor.Builder(AppManager.appContext())
//                    .collector(ChuckerCollector(AppManager.appContext()))
//                    .maxContentLength(250000L)
//                    .redactHeaders(emptySet())
//                    .alwaysReadResponseBody(false)
//                    .build()
//            )//api end point checking
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }

    private val REWRITE_RESPONSE_INTERCEPTOR = Interceptor { chain ->
        val originalResponse = chain.proceed(chain.request())
        val cacheControl = originalResponse.header("Cache-Control")

        if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
            cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")
        ) {
            originalResponse.newBuilder()
                .header("Cache-Control", "public, max-age=" + 10)
                .build()
        } else {
            originalResponse
        }
    }

    private val RESPONSE_INTERCEPTOR = Interceptor { chain ->
        val originalResponse = chain.proceed(chain.request())
        if (originalResponse.code == Http_401) {
            val bundle = Bundle()
//            bundle.putBoolean(INTENT_KEY_IS_AUTH_ERROR, true)
//            AppManager.appContext()
//                .navigateTo(LoginActivity::class.java, withIntentData = bundle, clearTask = true)
        }
        originalResponse
    }

//    private val responseCachingInterceptor = object : Interceptor {
//        override fun intercept(chain: Interceptor.Chain): Response {
//            "responseCachingInterceptor() called".logInfo()
//            val endPoint = chain.request().url.toUri().path.toString() ?: ""
//            if (ApiCacheManager.shouldResponseLoadFromCache(endPoint)) {
//                "loading api response from cache: $endPoint".logError("CachingInterceptor")
////                val cacheResponseBodyString = """
////                    ${PreferenceManager.loadApiResponseFromCache(endPoint)}
////                """
//                val cacheResponseBodyString = PreferenceManager.loadApiResponseFromCache(endPoint)
//                "responseString: $cacheResponseBodyString".logError("CachingInterceptor")
//                try {
//                    return Response.Builder()
//                        .code(Http_200)
//                        .protocol(Protocol.HTTP_2)
//                        .message(cacheResponseBodyString)
//                        .body(
//                            cacheResponseBodyString
//                                .toByteArray()
//                                .toResponseBody("application/json".toMediaTypeOrNull())
//                        )
//                        .request(chain.request())
//                        .addHeader("content-type", "application/json")
//                        .build()
//                } catch (e: Exception) {
//                    "Intered in Catch Section".logError("Pref")
//                    return chain.proceed(chain.request())
//                }
//            } else {
//                val networkResponse = chain.proceed(chain.request())
//                if (networkResponse.code == Http_200 && ApiCacheManager.shouldCache(endPoint)) {
//                    try {
//                        val responseBodyString = networkResponse.peekBody(Long.MAX_VALUE).string()
//                        ApiCacheManager.cacheTheResponse(endPoint, responseBodyString)
//                    } catch (e: Exception) {
//                        e.printStackTrace()
//                    }
//                }
//                return networkResponse
//            }
//        }
//    }

//
//    private val HEADER_INTERCEPTOR = Interceptor { chain ->
//        val request = chain.request()
//        val builder = request.newBuilder()
//        builder.addHeader(KEY_HEADER_ACCEPT, KEY_HEADER_ACCEPT_VALUE)
//        builder.addHeader(KEY_HEADER_CONTENT_TYPE, KEY_HEADER_CONTENT_TYPE_VALUE)
//        builder.addHeader(KEY_HEADER_AUTHORIZATION, "Bearer ${PreferenceManager.jwtToken}")
//        chain.proceed(builder.build())
//    }

}


internal const val Http_200 = 200
internal const val Http_401 = 401
internal const val Http_404 = 404

/*
class AuthAuthenticator() : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            val newToken = getNewToken(
                RefreshTokenRequest(
                    PreferenceManager.userId.toInt(),
                    PreferenceManager.refreshToken
                )
            )

            if (!newToken.isSuccessful || newToken.body() == null) {
                PreferenceManager.clearAllData()
                val bundle = Bundle()
                bundle.putBoolean(INTENT_KEY_IS_AUTH_ERROR, true)
                AppManager.appContext()
                    .navigateTo(
                        LoginActivity::class.java,
                        withIntentData = bundle,
                        clearTask = true
                    )
            }

            newToken.body()?.let {
                PreferenceManager.jwtToken = it.jwtToken.toString()
                PreferenceManager.refreshToken = it.refreshToken.toString()
                PreferenceManager.expires = it.expires.toString()
                PreferenceManager.refreshTokenExpires = it.refreshTokenExpires.toString()
                response.request.newBuilder()
                    .header("Authorization", "Bearer ${it.jwtToken}")
                    .build()
            }
        }
    }

    private suspend fun getNewToken(request: RefreshTokenRequest): retrofit2.Response<Token> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val service = retrofit.create(ApiServiceAuth::class.java)
        return service.submitRefreshToken(request)
    }
}*/
