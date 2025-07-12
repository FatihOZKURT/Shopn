package com.example.shopn.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shopn.R
import com.example.shopn.data.entity.SpecialOffer

class SpecialOffersAdapter(private val items: List<SpecialOffer>) :
    RecyclerView.Adapter<SpecialOffersAdapter.SpecialOfferViewHolder>() {

    class SpecialOfferViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.imageSpecial)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialOfferViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_special_offer_card, parent, false)
        return SpecialOfferViewHolder(view)
    }

    override fun onBindViewHolder(holder: SpecialOfferViewHolder, position: Int) {
        val item = items[position]
        holder.image.setImageResource(item.imageResId)
    }

    override fun getItemCount(): Int = items.size
}
