package com.example.shopn.util

import com.example.shopn.data.entity.CartItem
import com.example.shopn.data.entity.Products


fun CartItem.toProduct(): Products {
    return Products(
        productId = 0,
        productName = this.productName,
        productImage = this.productImage,
        productCategory = this.productCategory,
        productPrice = this.productPrice,
        productBrand = this.productBrand
    )
}