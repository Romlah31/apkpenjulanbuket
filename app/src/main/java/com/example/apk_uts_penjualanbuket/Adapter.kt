package com.example.apk_uts_penjualanbuket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class Adapter(
    private val context: DashboardActivity,
    private val listProduk: List<produk>
) : ArrayAdapter<produk>(context, 0, listProduk) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_produk, parent, false)

        val produk = listProduk[position]

        val imgProduk = view.findViewById<ImageView>(R.id.imgProduk)
        val tvHarga = view.findViewById<TextView>(R.id.tvHarga)
        val btnPesan = view.findViewById<Button>(R.id.btnPesan)

        imgProduk.setImageResource(produk.namaGambar)
        tvHarga.text = produk.harga

        btnPesan.setOnClickListener {
            context.bukaDetail(produk.pesan)
        }
        return view
    }
}
