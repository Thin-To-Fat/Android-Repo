package com.example.ttf

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object RequestToServer {

    var retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:7070/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var service: RequestInterface = retrofit.create(
        RequestInterface::class.java)

//    private fun provideOkHttpClient(
//        interceptor: AppInterceptor
//    ): OkHttpClient = OkHttpClient.Builder()
//        .run {
//            addInterceptor(interceptor)
//            build()
//        }
//
//    class AppInterceptor : Interceptor {
//        @Throws(IOException::class)
//        override fun intercept(chain: Interceptor.Chain)
//                : Response = with(chain) {
//            val newRequest = request().newBuilder()
//                .addHeader("X-AUTH-TOKEN", "33chRuAiqlSn5hn8tIme")
//                .build()
//
//            proceed(newRequest)
//        }
//    }





//    val okHttpClient = OkHttpClient.Builder()
//        .addInterceptor { chain: Interceptor.Chain ->
//            val original = chain.request()
//            if (original.url.encodedPath.equals("/users/check", true)
//                || original.url.encodedPath.equals("/users/join", true)
//                || original.url.encodedPath.equals("/users/login", true)
//            ) {
//                chain.proceed(original)
//            } else {
//                chain.proceed(original.newBuilder().apply {
//                    addHeader("X-AUTH-TOKEN", "")
//                }.build())
//            }
//        }.build()
}

