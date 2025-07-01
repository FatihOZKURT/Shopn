package com.example.shopn.ui.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.SearchView
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.shopn.R
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
        requireActivity().onBackPressedDispatcher.addCallback(this) {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = MainScreenBinding.inflate(inflater, container, false)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.search(newText.orEmpty())
                return true
            }
        })

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