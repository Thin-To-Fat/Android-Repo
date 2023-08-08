package com.example.ttf.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ttf.bnpl.PaymentActivity
import com.example.ttf.qr.QrActivity
import com.example.ttf.RequestToServer
import com.example.ttf.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val pref = this.getPreferences(0)
        val editor = pref.edit()

        super.onCreate(savedInstanceState)

        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            val email = binding.idEdit.text.toString()
            val pwd = binding.pwdEdit.text.toString()

            if (binding.idEdit.text.isNullOrBlank() || binding.pwdEdit.text.isNullOrBlank()) {
                Toast.makeText(this, "이메일과 비밀번호를 모두 입력하세요", Toast.LENGTH_LONG).show()
            } else {  // 로그인
                RequestToServer.service.requestLogin(
                    LoginRequest(
                        email = email,
                        password = binding.pwdEdit.text.toString()
                    )
                ).enqueue(object : Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Log.d("통신 실패", "${t.message}")
                    }
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        Log.d("통신22", "${pref.getString("token", "")}")
                        if (response.isSuccessful) {
                            if (response.body()!!.code == "U001") {
                                Log.d("성공", response.body()!!.result.toString())
                                editor.putString("token", response.body()!!.result!!.token).apply()

                                if (response.body()!!.result!!.bnplCk== "0"){ // 대금납부일이 지정 안되어있으면 지정 페이지로
                                    val payIntent = Intent(this@LoginActivity, PaymentActivity::class.java)
                                    payIntent.putExtra("token", response.body()!!.result!!.token)
                                    startActivity(payIntent)
                                    finish()
                                } else{ // 대금납부일이 지정 되어있으면 qr 페이지로
                                    val qrIntent = Intent(this@LoginActivity, QrActivity::class.java)
                                    qrIntent.putExtra("token", response.body()!!.result!!.token)
                                    startActivity(qrIntent)
                                    finish()
                                }

                            } else {
                                Log.d("실패", "실패")
                                Toast.makeText(
                                    this@LoginActivity,
                                    "아이디와 비밀번호를 확인해주세요.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                })

            }

        }

    }

}
