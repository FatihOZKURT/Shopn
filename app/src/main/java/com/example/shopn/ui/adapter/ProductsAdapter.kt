package com.example.shopn.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopn.data.entity.Products
import com.example.shopn.databinding.ProductDesignBinding
import com.example.shopn.ui.screen.MainScreenDirections
import com.example.shopn.R
import com.example.shopn.ui.viewmodel.FavoriteViewModel

class ProductsAdapter (
    var mContext: Context,
    var productsList: List<Products>,
    var viewModel: FavoriteViewModel
) : RecyclerView.Adapter<ProductsAdapter.ProductDesignHolder>() {

    inner class ProductDesignHolder(var binding: ProductDesignBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductDesignHolder {
        val binding = ProductDesignBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return ProductDesignHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: ProductDesignHolder,
        position: Int
    ) {
        val product = productsList[position]
        val design = holder.binding

        val imageUrl = "http://kasimadalan.pe.hu/urunler/resimler/${product.productImage}"
        Glide.with(mContext)
            .load(imageUrl)
            .into(design.imageViewProduct)

        design.textViewTitle.text = product.productName
        design.textViewBrand.text = product.productBrand
        design.textViewPrice.text = "₺${product.productPrice}"

        design.cardViewProduct.setOnClickListener {
            val toDetailScreen = MainScreenDirections.toDetailScreen(product = product)
            it.findNavController().navigate(toDetailScreen)
        }

        viewModel.checkFavorite(product.productId) { isFav ->
            design.imageViewFavorite.setImageResource(
                if (isFav) R.drawable.favorite else R.drawable.no_favorite
            )
        }

        design.imageViewFavorite.setOnClickListener {
            viewModel.toggleFavorite(product)
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return productsList.size
    }


}