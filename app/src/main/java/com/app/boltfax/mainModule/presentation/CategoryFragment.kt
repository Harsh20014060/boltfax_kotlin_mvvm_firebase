package com.app.boltfax.mainModule.presentation

import android.os.Bundle
import android.view.View
import com.app.boltfax.base.BaseFragment
import com.app.boltfax.base.CategoryModel
import com.app.boltfax.base.Resource
import com.app.boltfax.databinding.FragmentCategoryBinding
import com.app.boltfax.mainModule.adapter.CategoryAdapter
import com.app.boltfax.mainModule.viewModel.MainViewModel

class CategoryFragment : BaseFragment<FragmentCategoryBinding>(FragmentCategoryBinding::inflate),
    CategoryAdapter.OnCategoryClickListener {

    private val mainViewModel by lazy {
        MainViewModel()
    }
    private var categoryList = ArrayList<CategoryModel>()
    private val categoryAdapter by lazy {
        CategoryAdapter(categoryList, this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setUpObserver()
        rootView.rcvCategory.adapter = categoryAdapter
        rootView.rcvCategory.setHasFixedSize(true)
        mainViewModel.getCategory()


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

        mainViewModel.categoryObserver.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.DataNotFound -> {

                }

                is Resource.Error -> {

                }

                is Resource.Success -> {
                    it.data?.let { categories ->
                        categoryList.addAll(categories)
                        categoryAdapter.notifyItemRangeInserted(0, categoryList.size)

                    }
                }
            }
        }
    }

    override fun onCategoryClick(category: CategoryModel) {

    }

}