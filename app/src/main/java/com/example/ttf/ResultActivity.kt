package com.example.ttf

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ttf.databinding.ActivityResultBinding

class ResultActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        
        val binding = ActivityResultBinding.inflate(layoutInflater)


    }

}
