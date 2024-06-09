package com.app.boltfax.mainModule.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.boltfax.base.CategoryModel
import com.app.boltfax.databinding.RawCategoryBinding
import com.bumptech.glide.Glide

class CategoryAdapter(
    private val categoryList: ArrayList<CategoryModel>,
    private val onCategoryClickListener: OnCategoryClickListener
) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private lateinit var context: Context

    class ViewHolder(val binding: RawCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            RawCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val category = categoryList[position]

        Glide.with(context)
            .load(category.categoryImageUrl)
            .into(holder.binding.ivCategoryImage)

        holder.binding.apply {
            tvCategoryName.text = category.categoryName
            tvCategoryDescription.text = category.categoryDescription
        }

        holder.itemView.setOnClickListener {
            onCategoryClickListener.onCategoryClick(category)
        }
    }

    interface OnCategoryClickListener {
        fun onCategoryClick(category: CategoryModel)
    }
}