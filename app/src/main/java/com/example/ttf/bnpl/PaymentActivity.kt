package com.example.ttf.bnpl

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ttf.qr.QrActivity
import com.example.ttf.R
import com.example.ttf.RequestToServer
import com.example.ttf.databinding.ActivityPaymentBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val payIntent = intent
        val token = payIntent.getStringExtra("token")

        val binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val itemList = resources.getStringArray(R.array.payments)
        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemList)
        var selectThing = ""
        var positions = 0
        binding.spinner.adapter = adapter
        binding.spinner.setSelection(1)
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selectThing = itemList[position]
                positions = position
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        binding.payDayBtn.setOnClickListener{
            if (positions == 0){
                Toast.makeText(this, "날짜를 선택해주세요", Toast.LENGTH_LONG).show()
            }else{
                if (token != null) {
                    RequestToServer.service.putBnpl(
                        token = token,
                        bnpl = selectThing.substring(0 until selectThing.length-1)
                    ).enqueue(object: Callback<BnplResponse>{
                        override fun onFailure(call: Call<BnplResponse>, t:Throwable){
                            Log.d("통신실패", "${t.message}")
                        }

                        override fun onResponse(
                            call: Call<BnplResponse>,
                            response: Response<BnplResponse>
                        ) {
                            if (response.isSuccessful) {
                                if (response.body()!!.code == "T004") {
                                    val qrIntent = Intent(this@PaymentActivity, QrActivity::class.java)
                                    qrIntent.putExtra("token", token)
                                    startActivity(qrIntent)
                                    finish()
                                }
                            }
                        }
                    })
                }

            }
        }

    }
}

