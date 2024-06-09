package com.app.boltfax.mainModule.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.app.boltfax.base.BannerAndLogoModel
import com.app.boltfax.databinding.RawBannerBinding
import com.bumptech.glide.Glide

class BannerAdapter(private val bannerList: ArrayList<BannerAndLogoModel>) : PagerAdapter() {
    override fun getCount(): Int {
        return bannerList.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean = view == obj

//    override fun getItemPosition(`object`: Any): Int {
//        return POSITION_NONE
//    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding =
            RawBannerBinding.inflate(LayoutInflater.from(container.context), container, false)


        Glide.with(container.context)
            .load(bannerList[position].imageUrl)
            .into(binding.root)


        container.addView(binding.root)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View?)
    }
}