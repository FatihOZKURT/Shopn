package com.example.shopn.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.shopn.data.repo.ShopRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(var shopRepository: ShopRepository) : ViewModel() {


    init {

    }





}