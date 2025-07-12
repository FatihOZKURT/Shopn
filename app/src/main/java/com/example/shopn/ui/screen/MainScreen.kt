package com.example.shopn.ui.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.shopn.R
import com.example.shopn.data.entity.SpecialOffer
import com.example.shopn.databinding.MainScreenBinding
import com.example.shopn.ui.adapter.ProductsAdapter
import com.example.shopn.ui.adapter.SpecialOffersAdapter
import com.example.shopn.ui.viewmodel.FavoriteViewModel
import com.example.shopn.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainScreen : Fragment() {

    private lateinit var binding: MainScreenBinding
    private lateinit var viewModel : MainViewModel
    private lateinit var favViewModel: FavoriteViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : MainViewModel by viewModels()
        viewModel = tempViewModel
        val tempFavViewModel : FavoriteViewModel by viewModels()
        favViewModel = tempFavViewModel
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
            val productsAdapter = ProductsAdapter(requireContext(), productList, favViewModel)
            binding.recyclerViewProducts.adapter = productsAdapter
        }

        binding.recyclerViewProducts.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)


        val specialOffers = listOf(
            SpecialOffer(R.drawable.offer1),
            SpecialOffer( R.drawable.offer2),
            SpecialOffer( R.drawable.offer3),
        )

        val specialAdapter = SpecialOffersAdapter(specialOffers)
        binding.recyclerViewSpecialOffers.adapter = specialAdapter
        binding.recyclerViewSpecialOffers.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadProducts()
    }

}