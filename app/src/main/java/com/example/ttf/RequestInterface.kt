package com.example.ttf

import com.example.ttf.bnpl.BnplRequest
import com.example.ttf.bnpl.BnplResponse
import com.example.ttf.login.LoginRequest
import com.example.ttf.login.LoginResponse
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

    @PUT("/api/v1/ttf/dateofpay")
    fun putBnpl(@Header("X-AUTH-TOKEN") token: String, @Body body: BnplRequest): Call<BnplResponse>

}