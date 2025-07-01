package com.example.shopn.ui.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.shopn.databinding.DetailScreenBinding
import com.example.shopn.ui.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailScreen : Fragment() {

    private lateinit var binding: DetailScreenBinding
    private lateinit var viewModel: DetailViewModel

    private var quantity = 1
    private var unitPrice = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: DetailViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DetailScreenBinding.inflate(inflater, container, false)

        val bundle: DetailScreenArgs by navArgs()
        val product = bundle.product

        val url = "http://kasimadalan.pe.hu/urunler/resimler/${product.productImage}"
        Glide.with(this).load(url).into(binding.imageViewProduct)

        binding.textViewName.text = product.productName
        binding.textViewBrand.text = "Marka: ${product.productBrand}"
        binding.textViewCategory.text = "Kategori: ${product.productCategory}"
        binding.textViewPrice.text = "Fiyat: ₺${product.productPrice}"

        binding.textViewShipping.visibility =
            if (product.productPrice < 5000) View.INVISIBLE else View.VISIBLE

        unitPrice = product.productPrice.toDouble()

        updateTotalPrice()

        binding.imageButtonClose.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.buttonDecrease.setOnClickListener {
            if (quantity > 1) {
                quantity--
                updateQuantityAndTotal()
            }
        }

        binding.buttonIncrease.setOnClickListener {
            quantity++
            updateQuantityAndTotal()
        }

        return binding.root
    }

    private fun updateQuantityAndTotal() {
        binding.textViewQuantity.text = quantity.toString()
        updateTotalPrice()
    }

    private fun updateTotalPrice() {
        val total = quantity * unitPrice
        binding.textViewTotalPrice.text = "Toplam: ₺%.2f".format(total)
    }
}
