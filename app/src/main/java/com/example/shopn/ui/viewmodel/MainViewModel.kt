package com.example.shopn.ui.viewmodel

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

    init {
        loadProducts()
    }


    fun loadProducts() {
        CoroutineScope(Dispatchers.Main).launch {
            productsList.value = shopRepository.loadProducts()
        }
    }


}