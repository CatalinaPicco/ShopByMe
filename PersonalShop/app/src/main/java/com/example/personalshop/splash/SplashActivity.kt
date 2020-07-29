package com.example.personalshop.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.personalshop.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}