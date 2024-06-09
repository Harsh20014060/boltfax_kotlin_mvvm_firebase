package com.app.boltfax.mainModule.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.boltfax.base.ItemModel
import com.app.boltfax.databinding.RawDealsBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener

class TrendingAndDayDealsAdapter(private val trendingAndDayDealsList: ArrayList<ItemModel>) :
    RecyclerView.Adapter<TrendingAndDayDealsAdapter.ViewHolder>() {

    private var counter = 0
    private lateinit var context: Context

    class ViewHolder(val binding: RawDealsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            RawDealsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return trendingAndDayDealsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val colors = arrayOf(
            "#E0F7FF",
            "#F0E4F7",
            "#F5E6BC",
            "#FFF2E6",
            "#B3FFFF",
            "#E0FFE9",
            "#F6FFDE",
            "#FFDEDF",
            "#E0EBEB",
            "#DEFFEA"
        )
        holder.binding.ivProductImage.setBackgroundColor(Color.parseColor(colors[counter]))
        counter = (counter + 1) % colors.size

        val product = trendingAndDayDealsList[position]

        holder.binding.tvProductName.text = product.productName
        holder.binding.tvProductPrice.text = product.sellingPrice
        holder.binding.tvProductDiscount.text = product.discount
        holder.binding.tvProductName.text = product.productName

        Glide.with(context)
            .load(product.imageUrl)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    holder.binding.progressBar.visibility = View.GONE
                    return false
                }

            })
            .into(holder.binding.ivProductImage)


    }
}