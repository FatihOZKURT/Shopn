package com.example.shopn.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shopn.data.entity.Products
import com.example.shopn.data.repo.ShopRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(var shopRepository: ShopRepository) : ViewModel() {

    var productsList = MutableLiveData<List<Products>>()
    private var allProducts = listOf<Products>()

    init {
        loadProducts()
    }

    fun loadProducts() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                allProducts = shopRepository.loadProducts()
                productsList.value = allProducts
            } catch (e: Exception) {
                Log.e("Error", e.toString())
            }
        }
    }

    fun search(query: String) {
        productsList.value = if (query.isEmpty()) {
            allProducts
        } else {
            allProducts.filter {
                it.productName.contains(query, ignoreCase = true) ||
                        it.productBrand.contains(query, ignoreCase = true) ||
                        it.productCategory.contains(query, ignoreCase = true)
            }
        }
    }

}