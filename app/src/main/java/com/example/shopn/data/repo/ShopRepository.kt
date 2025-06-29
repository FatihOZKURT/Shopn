package com.example.shopn.data.repo

import com.example.shopn.data.datasource.ShopDataSource
import com.example.shopn.data.entity.Products

class ShopRepository ( var shopDataSource: ShopDataSource) {

    suspend fun loadProducts(): List<Products> = shopDataSource.loadProducts()



}