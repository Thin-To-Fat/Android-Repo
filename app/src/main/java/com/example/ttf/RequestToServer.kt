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
        //.baseUrl("http://192.0.0.4:7070/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var service: RequestInterface = retrofit.create(
        RequestInterface::class.java)

}

