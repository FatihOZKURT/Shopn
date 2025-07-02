package com.example.shopn.data.repo

import com.example.shopn.data.datasource.ShopDataSource
import com.example.shopn.data.entity.CRUDResponse
import com.example.shopn.data.entity.Products

class ShopRepository ( var shopDataSource: ShopDataSource) {

    suspend fun loadProducts(): List<Products> = shopDataSource.loadProducts()

    suspend fun addToCart(
        ad: String,
        resim: String,
        kategori: String,
        fiyat: Int,
        marka: String,
        siparisAdeti: Int,
        kullaniciAdi: String
    ): CRUDResponse {
        return shopDataSource.addToCart(ad, resim, kategori, fiyat, marka, siparisAdeti, kullaniciAdi)
    }


}