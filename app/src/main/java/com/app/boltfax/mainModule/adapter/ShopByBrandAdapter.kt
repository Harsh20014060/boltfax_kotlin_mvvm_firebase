package com.app.boltfax.mainModule.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.boltfax.base.BannerAndLogoModel
import com.app.boltfax.databinding.RawLogoCardsBinding
import com.bumptech.glide.Glide

class ShopByBrandAdapter(private val shopByBrandList: ArrayList<BannerAndLogoModel>) :
    RecyclerView.Adapter<ShopByBrandAdapter.ViewHolder>() {

    private lateinit var context: Context

    class ViewHolder(val binding: RawLogoCardsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            RawLogoCardsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return shopByBrandList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context)
            .load(shopByBrandList[position].imageUrl)
            .into(holder.binding.logoimages)
    }
}