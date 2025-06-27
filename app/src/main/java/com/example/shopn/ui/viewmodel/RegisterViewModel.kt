package com.example.shopn.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.shopn.data.repo.ShopRepository
import javax.inject.Inject

class RegisterViewModel@Inject constructor(var shopRepository: ShopRepository) : ViewModel() {

    init {

    }


}