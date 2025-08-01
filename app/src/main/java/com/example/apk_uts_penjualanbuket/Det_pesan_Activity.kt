package com.example.apk_uts_penjualanbuket

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class Det_pesan_Activity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FirestoreRecyclerAdapter<item_pesanan, PesananViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_det_pesan)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Tombol kembali ke dashboard
        val btnKembali = findViewById<Button>(R.id.btnKembaliDashboard)
        btnKembali.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }

        val query = FirebaseFirestore.getInstance()
            .collection("pesanan")
            .orderBy("timestamp", Query.Direction.DESCENDING)

        val options = FirestoreRecyclerOptions.Builder<item_pesanan>()
            .setQuery(query, item_pesanan::class.java)
            .setLifecycleOwner(this)
            .build()

        adapter = object : FirestoreRecyclerAdapter<item_pesanan, PesananViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PesananViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_pesanan, parent, false)
                return PesananViewHolder(view)
            }

            override fun onBindViewHolder(holder: PesananViewHolder, position: Int, model: item_pesanan) {
                val docId = snapshots.getSnapshot(position).id
                holder.bind(model)

                holder.btnHapus.setOnClickListener {
                    AlertDialog.Builder(this@Det_pesan_Activity)
                        .setTitle("Konfirmasi Hapus")
                        .setMessage("Apakah yakin ingin menghapus data ini?")
                        .setPositiveButton("Hapus") { _, _ ->
                            FirebaseFirestore.getInstance()
                                .collection("pesanan")
                                .document(docId)
                                .delete()
                                .addOnSuccessListener {
                                    Toast.makeText(applicationContext, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(applicationContext, "Gagal menghapus data", Toast.LENGTH_SHORT).show()
                                }
                        }
                        .setNegativeButton("Batal", null)
                        .show()
                }

                holder.btnEdit.setOnClickListener {
                    showEditDialog(docId, model)
                }
            }
        }

        recyclerView.adapter = adapter
    }

    private fun showEditDialog(docId: String, pesanan: item_pesanan) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.activity_edit, null)
        val inputNama = dialogView.findViewById<EditText>(R.id.editNama)
        val inputProduk = dialogView.findViewById<EditText>(R.id.editProduk)
        val inputJumlah = dialogView.findViewById<EditText>(R.id.editJumlah)

        inputNama.setText(pesanan.nama)
        inputProduk.setText(pesanan.produk)
        inputJumlah.setText(pesanan.jumlah.toString())

        AlertDialog.Builder(this)
            .setTitle("Edit Pesanan")
            .setView(dialogView)
            .setPositiveButton("Simpan") { _, _ ->
                val updateData = mapOf(
                    "nama" to inputNama.text.toString(),
                    "produk" to inputProduk.text.toString(),
                    "jumlah" to (inputJumlah.text.toString().ifBlank { "1" })
                )

                FirebaseFirestore.getInstance()
                    .collection("pesanan")
                    .document(docId)
                    .update(updateData)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Data diperbarui", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Gagal memperbarui data", Toast.LENGTH_SHORT).show()
                    }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    class PesananViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNama: TextView = itemView.findViewById(R.id.tvNama)
        private val tvProduk: TextView = itemView.findViewById(R.id.tvProduk)
        private val tvJumlah: TextView = itemView.findViewById(R.id.tvJumlah)
        val btnEdit: Button = itemView.findViewById(R.id.btnEdit)
        val btnHapus: Button = itemView.findViewById(R.id.btnHapus)

        fun bind(pesanan: item_pesanan) {
            tvNama.text = pesanan.nama
            tvProduk.text = pesanan.produk
            tvJumlah.text = "Jumlah: ${pesanan.jumlah}"
        }
    }
}
