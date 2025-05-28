package com.example.apk_uts_penjualanbuket

import android.os.Bundle
import android.os.Handler
import android.content.Intent
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)

        val logo = findViewById<ImageView>(R.id.imgLogo)
        val title = findViewById<TextView>(R.id.tvsplash)
        val subtitle = findViewById<TextView>(R.id.tvKata)

        val morph = AnimationUtils.loadAnimation(this, R.anim.morph)

        logo.startAnimation(morph)
        title.startAnimation(morph)
        subtitle.startAnimation(morph)

        Handler().postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 2500) // Delay 3 detik
    }
}
