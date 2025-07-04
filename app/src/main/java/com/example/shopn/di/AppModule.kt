package com.example.shopn.di

import android.content.Context
import androidx.room.Room
import com.example.shopn.data.datasource.ShopDataSource
import com.example.shopn.data.repo.ShopRepository
import com.example.shopn.retrofit.ApiUtils
import com.example.shopn.retrofit.ShopDao
import com.example.shopn.room.AppDatabase
import com.example.shopn.room.FavoriteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideShopRepository(shopDataSource: ShopDataSource) : ShopRepository {
        return ShopRepository(shopDataSource)
    }

    @Provides
    @Singleton
    fun provideShopDataSource(shopDao: ShopDao) : ShopDataSource {
        return ShopDataSource(shopDao)
    }

    @Provides
    @Singleton
    fun provideShopDao() : ShopDao {
        return ApiUtils.getShopDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "shopn_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFavoriteDao(database: AppDatabase): FavoriteDao {
        return database.favoriteDao()
    }

}