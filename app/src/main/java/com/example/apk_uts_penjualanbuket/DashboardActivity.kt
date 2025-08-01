package com.example.apk_uts_penjualanbuket

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)

        val listView = findViewById<ListView>(R.id.listViewProduk)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        val dataProduk = listOf(
            produk(R.drawable.produk1, "Rp 85.000", "Halo, saya mau pesan produk 1 (Rp 85.000)"),
            produk(R.drawable.produk2, "Rp 100.000", "Halo, saya mau pesan produk 2 (Rp 100.000)"),
            produk(R.drawable.produk3, "Rp 45.000", "Halo, saya mau pesan produk 3 (Rp 45.000)"),
            produk(R.drawable.produk4, "Rp 120.000", "Halo, saya mau pesan produk 4 (Rp 120.000)")
        )

        val adapter = Adapter(this, dataProduk)
        listView.adapter = adapter

        // Tombol Logout
        btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    fun bukaDetail(pesan: String) {
        val intent = Intent(this, PesananActivity::class.java)
        startActivity(intent)
    }
}
