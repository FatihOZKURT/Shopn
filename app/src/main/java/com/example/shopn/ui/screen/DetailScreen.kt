package com.example.shopn.ui.screen

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.shopn.R
import com.example.shopn.databinding.DetailScreenBinding
import com.example.shopn.ui.viewmodel.DetailViewModel
import com.example.shopn.ui.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailScreen : Fragment() {

    private lateinit var binding: DetailScreenBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var favViewModel: FavoriteViewModel

    private var quantity = 1
    private var unitPrice = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: DetailViewModel by viewModels()
        val tempFavViewModel: FavoriteViewModel by viewModels()
        viewModel = tempViewModel
        favViewModel = tempFavViewModel

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

        binding.buttonAddToCart.setOnClickListener {
            val sharedPreferences = requireContext().getSharedPreferences("user_info", Context.MODE_PRIVATE)
            val userName = sharedPreferences.getString("email", "") ?: ""

            val ad = product.productName
            val resim = product.productImage
            val kategori = product.productCategory
            val fiyat = product.productPrice
            val marka = product.productBrand
            val siparisAdeti = quantity
            val kullaniciAdi = userName

            // Sepette aynı ürün varsa önce silip sonra yeni adetle ekle
            viewModel.addOrUpdateCartItem(ad, resim, kategori, fiyat, marka, siparisAdeti, kullaniciAdi)
        }


        viewModel.addToCartResult.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(requireContext(), "Ürün sepete eklendi", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Sepete eklenirken bir hata oluştu.", Toast.LENGTH_SHORT).show()
            }
        }

        favViewModel.checkFavorite(product.productId) { isFav ->
            val iconRes = if (isFav) R.drawable.favorite else R.drawable.no_favorite
            binding.imageButtonFavorite.setImageResource(iconRes)
        }

        binding.imageButtonFavorite.setOnClickListener {
            favViewModel.toggleFavorite(product) { isNowFavorite ->
                val iconRes = if (isNowFavorite) R.drawable.favorite else R.drawable.no_favorite
                binding.imageButtonFavorite.setImageResource(iconRes)
            }
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
