package com.example.shopn.room

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteRepository @Inject constructor(private val dao: FavoriteDao) {

    suspend fun addToFavorites(favorite: Favorite) = dao.insertFavorite(favorite)

    suspend fun removeFromFavorites(productId: Int) {
       dao.removeFromFavorites(productId)
    }

    suspend fun isFavorite(productId: Int) = dao.isFavorite(productId)

    fun getFavorites(): Flow<List<Favorite>> = dao.getAllFavorites()

}