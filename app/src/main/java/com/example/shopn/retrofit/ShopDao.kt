package com.example.shopn.retrofit


import com.example.shopn.data.entity.ProductsResponse
import retrofit2.http.GET

interface ShopDao {

    @GET("urunler/tumUrunleriGetir.php")
    suspend fun loadProducts(): ProductsResponse


}