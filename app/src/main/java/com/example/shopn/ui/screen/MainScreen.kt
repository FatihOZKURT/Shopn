package com.example.shopn.ui.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.shopn.databinding.MainScreenBinding
import com.example.shopn.ui.adapter.ProductsAdapter
import com.example.shopn.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainScreen : Fragment() {

    private lateinit var binding: MainScreenBinding
    private lateinit var viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : MainViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = MainScreenBinding.inflate(inflater, container, false)

        viewModel.productsList.observe(viewLifecycleOwner){ productList ->
            val productsAdapter = ProductsAdapter(requireContext(), productList, viewModel)
            binding.recyclerViewProducts.adapter = productsAdapter
        }

        binding.recyclerViewProducts.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)


        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadProducts()

    }

}