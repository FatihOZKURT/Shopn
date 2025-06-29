package com.example.shopn.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Products(
    @SerializedName("id")
    var id: Int,
    @SerializedName("ad")
    var ad: String,
    @SerializedName("resim")
    var resim: String,
    @SerializedName("kategori")
    var kategori: String,
    @SerializedName("fiyat")
    var fiyat: Int,
    @SerializedName("marka")
    var marka: String
) : Serializable