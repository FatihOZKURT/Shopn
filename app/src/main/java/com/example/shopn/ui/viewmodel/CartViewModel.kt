package com.example.shopn.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopn.data.entity.CartItem
import com.example.shopn.data.repo.ShopRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val shopRepository: ShopRepository
) : ViewModel() {

    private val _cartItems = MutableLiveData<List<CartItem>>()
    val cartItems: LiveData<List<CartItem>> = _cartItems

    fun getCartItems(kullaniciAdi: String) {
        viewModelScope.launch {
            try {
                val items = shopRepository.getCartItems(kullaniciAdi)
                _cartItems.value = items
            } catch (e: Exception) {
                Log.e("CartViewModel", "Sepet verileri alınamadı", e)
                _cartItems.value = emptyList()
            }
        }
    }

    fun deleteFromCart(cartId: Int, userName: String) {
        viewModelScope.launch {
            try {
                shopRepository.deleteFromCart(cartId, userName)
                getCartItems(userName)
            } catch (e: Exception) {
                Log.e("CartViewModel", "Silme işlemi başarısız", e)
            }
        }
    }

    fun removeAndReAddItemWithQuantity(
        cartId: Int,
        ad: String,
        resim: String,
        kategori: String,
        fiyat: Int,
        marka: String,
        yeniAdet: Int,
        kullaniciAdi: String
    ) {
        viewModelScope.launch {
            try {

                shopRepository.deleteFromCart(cartId, kullaniciAdi)
                shopRepository.addToCart(ad, resim, kategori, fiyat, marka, yeniAdet, kullaniciAdi)
                getCartItems(kullaniciAdi)

            } catch (e: Exception) {
                Log.e("CartViewModel", "Güncelleme hatası", e)
            }
        }
    }

}