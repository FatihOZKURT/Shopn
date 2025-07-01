package com.example.shopn.data.datasource

import com.example.shopn.data.entity.Products
import com.example.shopn.retrofit.ShopDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ShopDataSource (var shopDao: ShopDao){

    suspend fun loadProducts(): List<Products> = withContext(Dispatchers.IO){
        return@withContext shopDao.loadProducts().products
    }




}