package com.example.ttf.result

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ttf.R
import com.example.ttf.RequestToServer
import com.example.ttf.databinding.ActivityResultBinding
import com.example.ttf.qr.QrActivity
import com.example.ttf.qr.QrResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        
        val binding = ActivityResultBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val resultIntent = intent
        val account = resultIntent.getStringExtra("account")
        val now = resultIntent.getStringExtra("now")
        val token = resultIntent.getStringExtra("token")

        binding.paymentDate.text = now
        binding.account.text = account

        binding.confirmbtn.setOnClickListener{
            val QrIntent = Intent(this@ResultActivity, QrActivity::class.java)
            QrIntent.putExtra("token", token)
            startActivity(QrIntent)
            finish()
        }

    }

}
