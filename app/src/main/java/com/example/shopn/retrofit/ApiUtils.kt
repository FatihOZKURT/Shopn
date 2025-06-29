package com.example.shopn.retrofit

import com.example.shopn.util.Constants.BASE_URL

class ApiUtils {

    companion object{
        fun getShopDao() : ShopDao {
            return RetrofitClient.getClient(BASE_URL).create(ShopDao::class.java)
        }

    }


}