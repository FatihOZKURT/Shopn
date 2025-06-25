package com.example.shopn.di

import com.example.shopn.data.datasource.ShopDataSource
import com.example.shopn.data.repo.ShopRepository
import com.example.shopn.retrofit.ApiUtils
import com.example.shopn.retrofit.ShopDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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


}