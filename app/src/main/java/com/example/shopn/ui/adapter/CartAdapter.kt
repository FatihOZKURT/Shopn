package com.example.shopn.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopn.data.entity.CartItem
import com.example.shopn.databinding.CartItemBinding
import com.example.shopn.ui.screen.MainScreenDirections
import com.example.shopn.ui.viewmodel.CartViewModel


class CartAdapter(
    var mContext: Context,
    var cartItemsList: List<CartItem>,
    var viewModel: CartViewModel
) : RecyclerView.Adapter<CartAdapter.CartDesignHolder>(){

    inner class CartDesignHolder(var binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartDesignHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return CartDesignHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: CartDesignHolder,
        position: Int
    ) {
        val cartItem = cartItemsList[position]
        val design = holder.binding

        design.textViewName.text = cartItem.productName
        design.textViewBrand.text = cartItem.productBrand
        design.textViewPrice.text = "₺${cartItem.productPrice}"
        design.textViewQuantity.text = "Adet: ${cartItem.orderQuantity}"
        design.textViewTotalPrice.text = "₺${cartItem.productPrice * cartItem.orderQuantity}"

        val imageUrl = "http://kasimadalan.pe.hu/urunler/resimler/${cartItem.productImage}"
        Glide.with(mContext)
            .load(imageUrl)
            .into(design.imageViewProduct)

        design.imageButtonRemove.setOnClickListener {
            if (cartItem.orderQuantity > 1) {
                // Adeti 1 azaltmak için: önce sil, sonra yeni adetle tekrar ekle
                val newQuantity = cartItem.orderQuantity - 1
                viewModel.removeAndReAddItemWithQuantity(
                    cartItem.cartId,
                    cartItem.productName,
                    cartItem.productImage,
                    cartItem.productCategory,
                    cartItem.productPrice,
                    cartItem.productBrand,
                    newQuantity,
                    cartItem.username
                )
            } else {
                // Ürün adedi zaten 1'di → direkt sil
                viewModel.deleteFromCart(cartItem.cartId, cartItem.username)
            }
        }
        design.cardViewCartItem.setOnClickListener {

        }

    }

    override fun getItemCount(): Int {
        return cartItemsList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateCartItems(newList: List<CartItem>) {
        cartItemsList = newList
        notifyDataSetChanged()
    }
}
