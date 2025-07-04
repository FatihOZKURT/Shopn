package com.example.shopn.ui.screen

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopn.databinding.CartScreenBinding
import com.example.shopn.ui.adapter.CartAdapter
import com.example.shopn.ui.viewmodel.CartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartScreen : Fragment() {
    private lateinit var binding: CartScreenBinding
    private val viewModel: CartViewModel by viewModels()
    private lateinit var cartAdapter: CartAdapter

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CartScreenBinding.inflate(inflater, container, false)

        val sharedPrefs = requireContext().getSharedPreferences("user_info", Context.MODE_PRIVATE)
        val userEmail = sharedPrefs.getString("email", "")

        if (userEmail != null) {
            viewModel.getCartItems(userEmail)
        } else {
            Toast.makeText(requireContext(), "Lütfen hesabınıza giriş yapın !", Toast.LENGTH_SHORT).show()
        }

        binding.recyclerViewCartItems.layoutManager = LinearLayoutManager(requireContext())
        cartAdapter = CartAdapter(requireContext(), emptyList(), viewModel)
        binding.recyclerViewCartItems.adapter = cartAdapter

        viewModel.cartItems.observe(viewLifecycleOwner) { cartItems ->
            if (cartItems.isNullOrEmpty()) {
                binding.recyclerViewCartItems.visibility = View.GONE
                binding.textViewEmptyCartMessage.visibility = View.VISIBLE
                binding.textViewTotalPrice.text = "Toplam Fiyat: ₺0"
            } else {
                binding.recyclerViewCartItems.visibility = View.VISIBLE
                binding.textViewEmptyCartMessage.visibility = View.GONE
                cartAdapter.updateCartItems(cartItems)

                val totalPrice = cartItems.sumOf { it.productPrice * it.orderQuantity }
                binding.textViewTotalPrice.text = "Toplam Fiyat: ₺$totalPrice"
            }
        }

        return binding.root
    }
}
