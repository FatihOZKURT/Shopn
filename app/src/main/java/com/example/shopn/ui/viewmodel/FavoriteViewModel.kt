package com.example.shopn.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.shopn.data.entity.Products
import com.example.shopn.room.Favorite
import com.example.shopn.room.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favRepo: FavoriteRepository
) : ViewModel() {

    val favorites = favRepo.getFavorites().asLiveData()

    fun checkFavorite(productId: Int, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            val isFav = favRepo.isFavorite(productId)
            callback(isFav)
        }
    }

    fun toggleFavorite(product: Products, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            if (favRepo.isFavorite(product.productId)) {
                favRepo.removeFromFavorites(product.productId)
            } else {
                val favorite = Favorite(
                    productId = product.productId,
                    productName = product.productName,
                    productImage = product.productImage,
                    productCategory = product.productCategory,
                    productPrice = product.productPrice,
                    productBrand = product.productBrand
                )
                favRepo.addToFavorites(favorite)
                callback(true)
            }
        }
    }

    fun removeFavorite(productId: Int) {
        viewModelScope.launch {
            favRepo.removeFromFavorites(productId)
        }
    }

}