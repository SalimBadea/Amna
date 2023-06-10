package com.salem.amna.presentation.ui.categories

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.salem.amna.R
import com.salem.amna.base.BaseFragment
import com.salem.amna.data.models.common.CategoriesModel
import com.salem.amna.data.models.common.CategoryItemModel
import com.salem.amna.databinding.FragmentCategoriesBinding
import com.salem.amna.presentation.common.UiEffect
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFragment : BaseFragment(), CategoriesAdapter.OnItemClick,
    ProductsAdapter.OnItemClick {

    private val binding: FragmentCategoriesBinding by lazy {
        FragmentCategoriesBinding.inflate(layoutInflater)
    }

    private val viewModel: CategoriesViewModel by viewModels()

    private val categoriesAdapter by lazy {
        CategoriesAdapter(requireContext(), this)
    }

    private val productsAdapter by lazy {
        ProductsAdapter(requireContext(), this)
    }

    override fun getRootView(): View {
        return binding.root
    }

    override fun initVar() {
    }

    override fun onEvent() {
        binding.backIv.setOnClickListener {
            if (binding.tvTitle.text.toString() == getString(R.string.categories))
                baseActivity.onBackPressed()
            else {
                viewModel.onEvent(CategoriesEvent.LoadCategories)
                binding.tvTitle.text = getText(R.string.categories)
            }
        }
    }

    override fun render() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { state ->
                if (state.isSubCategory) {
                    initItemsData(state)
                    hideLoadingDialog()
                }else if (state.isSuccess) {
                    initData(state)
                    hideLoadingDialog()
                } else if (state.isLoading) {
                    showLoadingDialog()
                } else if (state.error.isNotBlank()) {
                    hideLoadingDialog()
                }
            }
        }
    }

    private fun initItemsData(state: CategoriesState) {
        binding.rvCategories.visibility = View.VISIBLE

        productsAdapter.list = state.categoryItemsResult?.items!!

        binding.listSize = productsAdapter.itemCount

        binding.rvCategories.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productsAdapter
        }
    }

    private fun initData(state: CategoriesState) {

        categoriesAdapter.list = state.result?.categories!!

        binding.listSize = categoriesAdapter.itemCount

        binding.rvCategories.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = categoriesAdapter
        }
    }

    override fun navigate() {
    }

    override fun showEffect() {
        lifecycleScope.launchWhenStarted {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is UiEffect.ShowToast -> {
//                        Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }

            }
        }
    }

    override fun orderClicked(item: CategoriesModel) {
        binding.rvCategories.visibility = View.GONE
        viewModel.onEvent(CategoriesEvent.GetCategoryItems(item.id!!))
        binding.tvTitle.text = item.name
    }

    companion object {
        @JvmStatic
        fun newInstance() = CategoriesFragment().apply {
            arguments = Bundle().apply {
            }
        }
    }

    override fun orderClicked(item: CategoryItemModel) {

    }


}