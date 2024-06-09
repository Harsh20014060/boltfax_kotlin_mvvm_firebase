package com.app.boltfax.mainModule.presentation

import android.os.Bundle
import android.view.View
import com.app.boltfax.base.BannerAndLogoModel
import com.app.boltfax.base.BaseFragment
import com.app.boltfax.base.ItemModel
import com.app.boltfax.base.Resource
import com.app.boltfax.databinding.FragmentHomeBinding
import com.app.boltfax.mainModule.adapter.BannerAdapter
import com.app.boltfax.mainModule.adapter.ShopByBrandAdapter
import com.app.boltfax.mainModule.adapter.TrendingAndDayDealsAdapter
import com.app.boltfax.mainModule.viewModel.MainViewModel


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val mainViewModel by lazy {
        MainViewModel()
    }

    private var bannerList = ArrayList<BannerAndLogoModel>()
    private var trendingAndDayDealsList = ArrayList<ItemModel>()
    private var shopByBrandList = ArrayList<BannerAndLogoModel>()
    private val trendingDealsAdapter by lazy {
        TrendingAndDayDealsAdapter(
            trendingAndDayDealsList
        )
    }
    private val dealsOfTheDayAdapter by lazy {
        TrendingAndDayDealsAdapter(
            trendingAndDayDealsList
        )
    }
    private val shopByBrandAdapter by lazy { ShopByBrandAdapter(shopByBrandList) }
    private val bannerAdapter by lazy { BannerAdapter(bannerList) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObserver()


        rootView.apply {
            vpBanner.adapter = bannerAdapter
            vpDotIndicator.attachTo(vpBanner)
            rcvDealsOfDay.adapter = dealsOfTheDayAdapter
            rcvTrendingDeals.adapter = trendingDealsAdapter
            rcvBrandLogos.adapter = shopByBrandAdapter
        }

        mainViewModel.getHomeScreenBanners()
        mainViewModel.getTrendingAndDayDealsData()
        mainViewModel.getBrandLogoData()
    }

    private fun setUpObserver() {
        mainViewModel.showLoader.observe(viewLifecycleOwner) {
            when (it) {
                true -> {
                    showLoader()
                }

                false -> {
                    hideLoader()
                }
            }
        }

        mainViewModel.homeBannerObserver.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.DataNotFound -> {

                }

                is Resource.Error -> {

                }

                is Resource.Success -> {
                    it.data?.let { banners ->
                        bannerList.addAll(banners)

                        bannerAdapter.notifyDataSetChanged()
                    }
                }
            }
        }

        mainViewModel.trendingAndDayDealObserver.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.DataNotFound -> {

                }

                is Resource.Error -> {

                }

                is Resource.Success -> {
                    it.data?.let { dealsList ->
                        trendingAndDayDealsList.addAll(dealsList.shuffled())


                    }
                }
            }
        }

        mainViewModel.brandLogoObserver.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.DataNotFound -> {

                }

                is Resource.Error -> {

                }

                is Resource.Success -> {
                    it.data?.let { brandList ->
                        shopByBrandList.addAll(brandList)

                        shopByBrandAdapter.notifyItemRangeInserted(0, shopByBrandList.size)

                    }
                }
            }
        }
    }

}