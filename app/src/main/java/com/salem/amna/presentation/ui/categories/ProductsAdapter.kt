package com.salem.amna.presentation.ui.categories

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.salem.amna.R
import com.salem.amna.data.models.common.CategoriesModel
import com.salem.amna.data.models.common.CategoryItemModel
import com.salem.amna.databinding.ItemCategoryBinding
import com.salem.amna.databinding.ItemProductBinding
import com.salem.amna.util.loadImageFromInternet

class ProductsAdapter(
    private var context: Context,
    private var itemClicked: OnItemClick? = null
) :
    RecyclerView.Adapter<ProductsAdapter.OrderViewHolder>() {

    var list: MutableList<CategoryItemModel> = arrayListOf()
        set(newlist) {
            field = newlist
            notifyDataSetChanged()
        }

    inner class OrderViewHolder(var binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindDataToView(model: CategoryItemModel) {
            binding.model = model

            binding.ivProduct.loadImageFromInternet(
                model.image,
                ContextCompat.getDrawable(context, R.drawable.logo)
            )

            binding.tvPercent.text = "${model.name}"
//            binding.tvContent.text = "${model.points}"

            binding.root.setOnClickListener {
                itemClicked?.orderClicked(model)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        return OrderViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val model = list[position]
        holder.bindDataToView(model)
    }

    interface OnItemClick {
        fun orderClicked(item: CategoryItemModel)
    }
}