package com.example.ttf

import com.example.ttf.bnpl.BnplResponse
import com.example.ttf.login.LoginRequest
import com.example.ttf.login.LoginResponse
import com.example.ttf.qr.QrResponse
import com.example.ttf.result.ResultResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT

interface RequestInterface {
    @Headers("Content-Type: application/json")
    @POST("/api/v1/users/login")
    fun requestLogin(@Body body: LoginRequest): Call<LoginResponse>

    @Headers("Content-Type: application/json")
    @PUT("/api/v1/ttf/dateofpay")
    fun putBnpl(@Header("X-AUTH-TOKEN") token: String, @Body bnpl: String): Call<BnplResponse>

    @Headers("Content-Type: application/json")
    @PUT("/api/v1/ttf/payrefusal")
    fun payment(@Header ("X-AUTH-TOKEN") token: String, @Body productPrice: Int): Call<QrResponse>

    @GET("/api/v1/ttf/qrInfo")
    fun qrInfo(@Header ("X-AUTH-TOKEN") token: String): Call<ResultResponse>

}