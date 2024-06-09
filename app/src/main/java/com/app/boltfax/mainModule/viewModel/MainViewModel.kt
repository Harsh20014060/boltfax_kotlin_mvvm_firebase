package com.app.boltfax.mainModule.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.boltfax.base.BannerAndLogoModel
import com.app.boltfax.base.CategoryModel
import com.app.boltfax.base.ItemModel
import com.app.boltfax.base.Resource
import com.app.boltfax.mainModule.repository.MainRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val mainRepository by lazy {
        MainRepository()
    }
    val showLoader: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val homeBannerObserver: MutableLiveData<Resource<ArrayList<BannerAndLogoModel>>> by lazy {
        MutableLiveData<Resource<ArrayList<BannerAndLogoModel>>>()
    }

    fun getHomeScreenBanners() {
        synchronized(homeBannerObserver) {
            showLoader.postValue(true)
        }
        viewModelScope.launch {
            mainRepository.getHomeBanners().let {
                showLoader.postValue(false)
                homeBannerObserver.postValue(it)
            }
        }
    }

    val trendingAndDayDealObserver: MutableLiveData<Resource<ArrayList<ItemModel>>> by lazy {
        MutableLiveData<Resource<ArrayList<ItemModel>>>()
    }

    fun getTrendingAndDayDealsData() {
        synchronized(trendingAndDayDealObserver) {
            showLoader.postValue(true)
        }
        viewModelScope.launch {
            mainRepository.getTrendingAndDayDealsData().let {
                showLoader.postValue(false)
                trendingAndDayDealObserver.postValue(it)
            }
        }
    }

    val brandLogoObserver: MutableLiveData<Resource<ArrayList<BannerAndLogoModel>>> by lazy {
        MutableLiveData<Resource<ArrayList<BannerAndLogoModel>>>()
    }

    fun getBrandLogoData() {
        synchronized(brandLogoObserver) {
            showLoader.postValue(true)
        }
        viewModelScope.launch {
            mainRepository.getBrandLogos().let {
                showLoader.postValue(false)
                brandLogoObserver.postValue(it)
            }
        }
    }

    val categoryObserver: MutableLiveData<Resource<ArrayList<CategoryModel>>> by lazy {
        MutableLiveData<Resource<ArrayList<CategoryModel>>>()
    }

    fun getCategory() {
        synchronized(categoryObserver) {
            showLoader.postValue(true)
        }
        viewModelScope.launch {
            mainRepository.getCategories().let {
                showLoader.postValue(false)
                categoryObserver.postValue(it)
            }
        }
    }
}