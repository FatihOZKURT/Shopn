package com.example.shopn.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CartItem(
    @SerializedName("sepetId")
    val cartId: Int,
    @SerializedName("ad")
    val productName: String,
    @SerializedName("resim")
    val productImage: String,
    @SerializedName("kategori")
    val productCategory: String,
    @SerializedName("fiyat")
    val productPrice: Int,
    @SerializedName("marka")
    val productBrand: String,
    @SerializedName("siparisAdeti")
    val orderQuantity: Int,
    @SerializedName("kullaniciAdi")
    val username: String
) : Serializable