package com.example.shopn.ui.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopn.data.entity.Products
import com.example.shopn.databinding.FavoriteScreenBinding
import com.example.shopn.ui.adapter.FavoriteAdapter
import com.example.shopn.ui.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteScreen : Fragment() {

    private lateinit var binding: FavoriteScreenBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: FavoriteViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FavoriteScreenBinding.inflate(inflater, container, false)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.favorites.observe(viewLifecycleOwner) { favList ->
            if (favList.isEmpty()) {
                binding.textViewEmptyFavoritesMessage.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            } else {
                binding.textViewEmptyFavoritesMessage.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE

                adapter = FavoriteAdapter(
                    requireContext(), favList, onRemoveClick = { selectedItem ->
                        viewModel.removeFavorite(selectedItem.productId)
                    },
                    onItemClick = { selectedItem ->
                        // Favorite -> Products dönüşümü
                        val product = Products(
                            productId = selectedItem.productId,
                            productName = selectedItem.productName,
                            productImage = selectedItem.productImage,
                            productCategory = selectedItem.productCategory,
                            productPrice = selectedItem.productPrice,
                            productBrand = selectedItem.productBrand
                        )
                        val toDetailScreen = FavoriteScreenDirections.actionFavoriteScreenToDetailScreen(product)
                        findNavController().navigate(toDetailScreen)
                    })
                binding.recyclerView.adapter = adapter
            }
        }

        return binding.root
    }


}