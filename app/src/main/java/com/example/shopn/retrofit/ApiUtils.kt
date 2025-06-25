package com.example.shopn.retrofit

class ApiUtils {

    companion object{
        val BASE_URL = "http://kasimadalan.pe.hu/"

        fun getShopDao() : ShopDao {
            return RetrofitClient.getClient(BASE_URL).create(ShopDao::class.java)
        }

    }


}