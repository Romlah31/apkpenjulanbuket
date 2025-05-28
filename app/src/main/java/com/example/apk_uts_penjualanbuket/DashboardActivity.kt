package com.example.apk_uts_penjualanbuket

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)

        val listView = findViewById<ListView>(R.id.listViewProduk)

        val dataProduk = listOf(
            produk(R.drawable.produk1, "Rp 85.000", "Halo, saya mau pesan produk 1 (Rp 85.000)"),
            produk(R.drawable.produk2, "Rp 100.000", "Halo, saya mau pesan produk 2 (Rp 100.000)"),
            produk(R.drawable.produk3, "Rp 45.000", "Halo, saya mau pesan produk 3 (Rp 45.000)"),
            produk(R.drawable.produk4, "Rp 120.000", "Halo, saya mau pesan produk 4 (Rp 120.000)")
        )

        val adapter = Adapter(this, dataProduk)
        listView.adapter = adapter
    }

    fun bukaWhatsapp(pesan: String) {
        val nomor = "6283822994459"
        val url = "https://wa.me/$nomor?text=${Uri.encode(pesan)}"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}
