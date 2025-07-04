package com.example.shopn.ui.viewmodel

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
class DetailViewModel @Inject constructor(var shopRepository: ShopRepository) : ViewModel() {

    private val _addToCartResult = MutableLiveData<Boolean>()
    val addToCartResult: LiveData<Boolean> get() = _addToCartResult

    fun addOrUpdateCartItem(
        ad: String,
        resim: String,
        kategori: String,
        fiyat: Int,
        marka: String,
        siparisAdeti: Int,
        kullaniciAdi: String
    ) {
        viewModelScope.launch {
            try {
                val cartItems = try {
                    shopRepository.getCartItems(kullaniciAdi)
                } catch (e: Exception) {
                    emptyList<CartItem>()
                }

                val existingItem = cartItems.find {
                    it.productName == ad && it.productBrand == marka
                }

                if (existingItem != null) {
                    val newQuantity = existingItem.orderQuantity + siparisAdeti
                    shopRepository.deleteFromCart(existingItem.cartId, kullaniciAdi)
                    shopRepository.addToCart(ad, resim, kategori, fiyat, marka, newQuantity, kullaniciAdi)
                } else {
                    shopRepository.addToCart(ad, resim, kategori, fiyat, marka, siparisAdeti, kullaniciAdi)
                }
                _addToCartResult.value = true
            } catch (e: Exception) {
                _addToCartResult.value = false
            }
        }
    }

}