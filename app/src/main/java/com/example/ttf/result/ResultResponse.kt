package com.example.ttf.result

import com.example.ttf.login.SomeData

data class ResultResponse (
    val code: String,
    val message: String,
    val result: ResultData?
    )

data class ResultData(
    val balance: String,
    val accountNum: String,
    val limitAmount: String
)