package com.example.shopn.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class Favorite(
    @PrimaryKey val productId: Int,
    val productName: String,
    val productImage: String,
    val productCategory: String,
    val productPrice: Int,
    val productBrand: String
)
