package com.example.shopn.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Products(
    @SerializedName("id")
    var productId: Int,
    @SerializedName("ad")
    var productName: String,
    @SerializedName("resim")
    var productImage: String,
    @SerializedName("kategori")
    var productCategory: String,
    @SerializedName("fiyat")
    var productPrice: Int,
    @SerializedName("marka")
    var productBrand: String
) : Serializable