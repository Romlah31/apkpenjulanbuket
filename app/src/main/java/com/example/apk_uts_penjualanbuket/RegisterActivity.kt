package com.example.apk_uts_penjualanbuket

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)

        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etConfirmPassword = findViewById<EditText>(R.id.etConfirmPassword)
        val btnSignup = findViewById<Button>(R.id.btnSignup)
        val tvLogin = findViewById<TextView>(R.id.tvLogin)
        val imgGoogle = findViewById<ImageView>(R.id.imgGoogle)
        val imgEmail = findViewById<ImageView>(R.id.imgEmail)
        val imgFacebook = findViewById<ImageView>(R.id.imgFacebook)

        // Teks underline "Already have an account?"
        tvLogin.text = Html.fromHtml("Already have an account? <u>Login</u>")


        tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }


        btnSignup.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                val sharedPref = getSharedPreferences("user_register", MODE_PRIVATE)
                sharedPref.edit().apply {
                    putString("username", username)
                    putString("password", password)
                    apply()
                }

            when {
                username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() -> {
                    Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
                }

                password != confirmPassword -> {
                    Toast.makeText(this, "Password tidak cocok", Toast.LENGTH_SHORT).show()
                }

                else -> {
                    val sharedPref = getSharedPreferences("user_register", MODE_PRIVATE)
                    sharedPref.edit().apply {
                        putString("username", username)
                        putString("email", email)
                        putString("password", password)
                        apply()
                    }

                    Toast.makeText(this, "Registrasi berhasil!", Toast.LENGTH_SHORT).show()


                    etUsername.setText("")
                    etEmail.setText("")
                    etPassword.setText("")
                    etConfirmPassword.setText("")


                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            }
        }
        imgGoogle.setOnClickListener {
            showSocialRegisterDialog("Google")

        }

        imgEmail.setOnClickListener {
            showSocialRegisterDialog("Email")
        }

        imgFacebook.setOnClickListener {
            showSocialRegisterDialog("Facebook")
        }
    }}

    private fun showSocialRegisterDialog(platform: String) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_register_social, null)
        val etUsername = dialogView.findViewById<EditText>(R.id.etSocialUsername)

        val dialog = android.app.AlertDialog.Builder(this)
            .setTitle("Daftar dengan $platform")
            .setView(dialogView)
            .setPositiveButton("Daftar") { _, _ ->
                val username = etUsername.text.toString().trim()
                if (username.isNotEmpty()) {
                    val sharedPref = getSharedPreferences("user_register", MODE_PRIVATE)
                    sharedPref.edit().apply {
                        putString("username", username)
                        putString("password", "default123")
                        apply()
                    }

                    Toast.makeText(this, "Akun $username berhasil dibuat!", Toast.LENGTH_SHORT)
                        .show()

                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Username tidak boleh kosong", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Batal", null)
            .create()

        dialog.show()

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
