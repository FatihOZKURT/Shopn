package com.example.shopn.data.entity

import com.google.gson.annotations.SerializedName

data class ProductsResponse(
    @SerializedName("urunler")
    var products : List<Products>,
    @SerializedName("success")
    var success : Int
)