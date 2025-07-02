package com.example.shopn.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopn.data.repo.ShopRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(var shopRepository: ShopRepository) : ViewModel() {

    private val _addToCartResult = MutableLiveData<Boolean>()
    val addToCartResult: LiveData<Boolean> get() = _addToCartResult

    fun addToCart(
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
                val response = shopRepository.addToCart(ad, resim, kategori, fiyat, marka, siparisAdeti, kullaniciAdi)
                _addToCartResult.value = response.success == 1
            } catch (e: Exception) {
                _addToCartResult.value = false
            }
        }
    }

}