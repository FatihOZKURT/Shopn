package com.example.shopn.data.entity

import com.google.gson.annotations.SerializedName

data class CartResponse(
    @SerializedName("urunler_sepeti")
    val cartItems: List<CartItem>,
    @SerializedName("success")
    val success: Int
)
