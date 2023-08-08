package com.example.ttf.qr

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.ttf.R
import com.example.ttf.RequestToServer
import com.example.ttf.result.ResultActivity
import com.example.ttf.databinding.ActivityQrBinding
import com.example.ttf.result.ResultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class QrActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr)
        val binding = ActivityQrBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val payIntent = intent
        val token = payIntent.getStringExtra("token")

        var accountIntent = ""
        var balanceIntent = ""
        var limitIntent = ""

        if (token != null) {
            RequestToServer.service.qrInfo(
                token=token
            ).enqueue(object: Callback<ResultResponse> {
                override fun onFailure(call: Call<ResultResponse>, t:Throwable){
                    Log.d("통신실패", "${t.message}")
                }
                override fun onResponse(
                    call: Call<ResultResponse>,
                    response: Response<ResultResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()!!.code == "T005") {
                            accountIntent = response.body()!!.result?.accountNum.toString()
                            balanceIntent = response.body()!!.result?.balance.toString()+ "원"
                            limitIntent = response.body()!!.result?.limitAmount+ "원"
                            binding.accountTxt.text = accountIntent
                            binding.balanceTxt.text = balanceIntent
                            binding.limitTxt.text = limitIntent
                        }
                    }
                }
            })
        }

        binding.qrBtn.setOnClickListener{
            if (token != null) {
                RequestToServer.service.payment(
                    token = token,
                    productPrice = 10000
                ).enqueue(object: Callback<QrResponse>{
                    override fun onFailure(call: Call<QrResponse>, t:Throwable){
                        Log.d("통신실패", "${t.message}")
                    }

                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onResponse(
                        call: Call<QrResponse>,
                        response: Response<QrResponse>
                    ) {
                        if (response.isSuccessful) {
                            if (response.body()!!.code == "T002") {
                                Toast.makeText(
                                    this@QrActivity,
                                    "10000원 결제 완료",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val qrIntent = Intent(this@QrActivity, ResultActivity::class.java)
                                qrIntent.putExtra("account", accountIntent)
                                qrIntent.putExtra("now", LocalDateTime.now().format(
                                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toString())
                                qrIntent.putExtra("token", token)
                                startActivity(qrIntent)
                                finish()
                            } else{
                                Log.d("payno", "결제 실패")
                                Toast.makeText(
                                    this@QrActivity,
                                    "사용 가능 한도를 초과했습니다!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                })
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}