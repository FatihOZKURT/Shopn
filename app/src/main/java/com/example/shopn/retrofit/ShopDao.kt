package com.example.shopn.retrofit


import com.example.shopn.data.entity.ProductsResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ShopDao {

    @GET("urunler/tumUrunleriGetir.php")
    suspend fun loadProducts(): ProductsResponse



}