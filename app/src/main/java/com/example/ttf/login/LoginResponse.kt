package com.example.ttf.login

data class LoginResponse (
    val code: String,
    val message: String,
    val result: SomeData?

)

data class SomeData(
    val token: String,
    val bnplCk: String
)