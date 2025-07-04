package com.example.shopn.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopn.databinding.FavoriteDesignBinding
import com.example.shopn.room.Favorite
import androidx.appcompat.app.AlertDialog

class FavoriteAdapter(
    private val context: Context,
    private val favoriteList: List<Favorite>,
    private val onRemoveClick: (Favorite) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    inner class FavoriteViewHolder(val binding: FavoriteDesignBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = FavoriteDesignBinding.inflate(LayoutInflater.from(context), parent, false)
        return FavoriteViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favoriteItem = favoriteList[position]
        val design = holder.binding

        design.textViewName.text = favoriteItem.productName
        design.textViewBrand.text = "Marka: ${favoriteItem.productBrand}"
        design.textViewCategory.text = "Kategori: ${favoriteItem.productCategory}"
        design.textViewPrice.text = "Fiyat: ₺${favoriteItem.productPrice}"

        val imageUrl = "http://kasimadalan.pe.hu/urunler/resimler/${favoriteItem.productImage}"
        Glide.with(context).load(imageUrl).into(design.imageViewProduct)

        design.imageButtonRemove.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Favorilerden Kaldır")
                .setMessage("Bu ürünü favorilerinizden kaldırmak istediğinize emin misiniz?")
                .setPositiveButton("Evet") { _, _ ->
                    onRemoveClick(favoriteItem)
                }
                .setNegativeButton("İptal", null)
                .show()
        }



    }

    override fun getItemCount() = favoriteList.size
}
