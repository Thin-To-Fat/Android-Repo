package com.example.ttf.bnpl

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ttf.PreferenceUtil
import com.example.ttf.QrActivity
import com.example.ttf.R
import com.example.ttf.RequestToServer
import com.example.ttf.databinding.ActivityPaymentBinding

class PaymentActivity: AppCompatActivity() {

    companion object {
        lateinit var prefs: PreferenceUtil
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val preferences = PreferenceUtil(applicationContext)
        super.onCreate(savedInstanceState)

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
                Log.d("hi", itemList[position])
                selectThing = itemList[position]
                positions = position
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        binding.payDayBtn.setOnClickListener{
            if (positions == 0){
                Log.d("cck", positions.toString())
                Toast.makeText(this, "날짜를 선택해주세요", Toast.LENGTH_LONG).show()
            }else{
                Log.d("cck", positions.toString())
                RequestToServer.service.putBnpl(
                    token= prefs.getString("token", "default"),
                    BnplRequest(bnpl = selectThing)
                )
            }
            val qrIntent = Intent(this, QrActivity::class.java)
            startActivity(qrIntent)
            finish()
        }

    }
}

