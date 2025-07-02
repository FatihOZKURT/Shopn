package com.example.shopn.data.datasource

import com.example.shopn.data.entity.CRUDResponse
import com.example.shopn.data.entity.CartItem
import com.example.shopn.data.entity.Products
import com.example.shopn.retrofit.ShopDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ShopDataSource(var shopDao: ShopDao) {

    suspend fun loadProducts(): List<Products> = withContext(Dispatchers.IO) {
        return@withContext shopDao.loadProducts().products
    }

    suspend fun addToCart(
        ad: String,
        resim: String,
        kategori: String,
        fiyat: Int,
        marka: String,
        siparisAdeti: Int,
        kullaniciAdi: String
    ): CRUDResponse {
        return shopDao.addToCart(ad, resim, kategori, fiyat, marka, siparisAdeti, kullaniciAdi)
    }

    suspend fun getCartItems(kullaniciAdi: String): List<CartItem> = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = shopDao.getCartItems(kullaniciAdi)
            response.cartItems ?: emptyList()
        } catch (e: Exception) {
            // EOFException ya da başka hata varsa boş liste dön
            emptyList()
        }
    }


    suspend fun deleteFromCart(sepetId: Int, kullaniciAdi: String): CRUDResponse {
        return shopDao.deleteFromCart(sepetId, kullaniciAdi)
    }


}