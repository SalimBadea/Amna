package com.salem.amna.presentation.ui.cart.adapter

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.salem.amna.R
import com.salem.amna.data.models.common.AddressModel
import com.salem.amna.data.models.common.CategoryItemModel
import com.salem.amna.databinding.ItemAddressBinding
import com.salem.amna.databinding.ItemProductBinding
import com.salem.amna.util.loadImageFromInternet

class CartItemViewHolder(
    private val binding: ItemProductBinding,
    private val context: Context,
    private val onDeleteClicked: ((item: CategoryItemModel) -> Unit)?
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CategoryItemModel){
        binding.model = item

        binding.ibAdd.visibility = View.GONE
        binding.ibDelete.visibility = View.VISIBLE
        binding.tvProduct.text = item.name
        binding.tvCategory.text = item.category?.name

        binding.ivProduct.loadImageFromInternet(item.image, ContextCompat.getDrawable(context, R.drawable.logo))

        binding.ibDelete.setOnClickListener {
            onDeleteClicked?.let { it1 -> it1(item) }
        }
    }
}