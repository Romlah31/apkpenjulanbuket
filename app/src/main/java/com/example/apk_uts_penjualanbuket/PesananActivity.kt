package com.example.apk_uts_penjualanbuket

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class PesananActivity : AppCompatActivity() {

    private lateinit var etNama: EditText
    private lateinit var etProduk: EditText
    private lateinit var etJumlah: EditText
    private lateinit var btnSimpan: Button
    private lateinit var btnWhatsapp: Button

    private val firestore by lazy { FirebaseFirestore.getInstance() }

    private var pesanWhatsapp: String = ""
    private val nomorWA = "6283822994459"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesanan)

        etNama = findViewById(R.id.etNama)
        etProduk = findViewById(R.id.etProduk)
        etJumlah = findViewById(R.id.etJumlah)
        btnSimpan = findViewById(R.id.btnSimpan)
        btnWhatsapp = findViewById(R.id.btnWhatsapp)

        btnSimpan.setOnClickListener {
            val nama = etNama.text.toString().trim()
            val produk = etProduk.text.toString().trim()
            val jumlah = etJumlah.text.toString().trim()

            val intent = Intent(this, Det_pesan_Activity::class.java)
            startActivity(intent)


            if (nama.isEmpty() || produk.isEmpty() || jumlah.isEmpty()) {
                Toast.makeText(this, "Lengkapi semua data", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val dataPesanan = hashMapOf(
                "nama" to nama,
                "produk" to produk,
                "jumlah" to jumlah,
                "timestamp" to System.currentTimeMillis()
            )

            firestore.collection("pesanan")
                .add(dataPesanan)
                .addOnSuccessListener {
                    Toast.makeText(this, "Pesanan disimpan", Toast.LENGTH_SHORT).show()
                    pesanWhatsapp = "Halo, saya $nama ingin pesan $produk sebanyak $jumlah buah."
                    btnWhatsapp.isEnabled = true
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Gagal menyimpan", Toast.LENGTH_SHORT).show()
                }
        }

        btnWhatsapp.setOnClickListener {
            val url = "https://wa.me/$nomorWA?text=${Uri.encode(pesanWhatsapp)}"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }
}
