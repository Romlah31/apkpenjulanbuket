package com.example.apk_uts_penjualanbuket

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)

        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val switchRemember = findViewById<SwitchCompat>(R.id.switchRemember)
        val tvRegister = findViewById<TextView>(R.id.tvRegister)

        val sharedPref = getSharedPreferences("login_pref", MODE_PRIVATE)
        val isRemembered = sharedPref.getBoolean("remember", false)

        if (isRemembered) {
            etUsername.setText(sharedPref.getString("username", ""))
            etPassword.setText(sharedPref.getString("password", ""))
            switchRemember.isChecked = true
        } else {
            etUsername.setText("")
            etPassword.setText("")
            switchRemember.isChecked = false
            sharedPref.edit().clear().apply()
        }

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                val userPref = getSharedPreferences("user_register", MODE_PRIVATE)
                val registeredUsername = userPref.getString("username", null)
                val registeredPassword = userPref.getString("password", null)

                if (username == registeredUsername && password == registeredPassword) {
                    Toast.makeText(this, "Login berhasil!", Toast.LENGTH_SHORT).show()

                    if (switchRemember.isChecked) {
                        sharedPref.edit().apply {
                            putString("username", username)
                            putString("password", password)
                            putBoolean("remember", true)
                            apply()
                        }
                    } else {
                        sharedPref.edit().clear().apply()
                    }

                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Username atau password salah!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Username dan password tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            }
        }

        tvRegister.text = Html.fromHtml("Don’t have an account? <u>Signup</u>")
        tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onResume() {
        super.onResume()

        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val switchRemember = findViewById<SwitchCompat>(R.id.switchRemember)

        val sharedPref = getSharedPreferences("login_pref", MODE_PRIVATE)
        val isRemembered = sharedPref.getBoolean("remember", false)

        if (!isRemembered) {
            etUsername.setText("")
            etPassword.setText("")
            switchRemember.isChecked = false
            sharedPref.edit().clear().apply()
        }
    }

}
