package com.example.theenglishpremierleague.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.theenglishpremierleague.MainActivity
import com.example.theenglishpremierleague.R
import kotlinx.coroutines.NonCancellable.isCancelled

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashn)
        Handler(Looper.getMainLooper()).postDelayed({
            if (!isCancelled) {
                startActivity(Intent(this@Splash, MainActivity::class.java))
                finishAffinity()
            }
        }, 1000)
    }
}