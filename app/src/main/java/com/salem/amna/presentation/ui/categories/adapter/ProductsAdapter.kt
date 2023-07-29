package com.salem.amna.presentation.ui.categories.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.salem.amna.R
import com.salem.amna.data.models.common.CategoryItemModel
import com.salem.amna.databinding.ItemProductBinding
import com.salem.amna.util.loadImageFromInternet

class ProductsAdapter(
    private var context: Context,
    private var itemClicked: OnItemClick? = null
) :
    RecyclerView.Adapter<ProductsAdapter.OrderViewHolder>() {

    private var onAddClicked: ((item: CategoryItemModel) -> Unit)? = null

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

            binding.tvProduct.text = "${model.name}"
            binding.tvCategory.text = "${model.category?.name}"
            binding.tvContent.text = "${model.points}"

            binding.ibAdd.visibility = View.VISIBLE

            binding.ibAdd.setOnClickListener {
                onAddClicked?.let { it1 -> it1(model) }
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

    fun setOnAddClickedListener(onAddClicked: (item: CategoryItemModel) -> Unit) {
        this.onAddClicked = onAddClicked
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val model = list[position]
        holder.bindDataToView(model)
    }

    interface OnItemClick {
        fun onAddClicked(item: CategoryItemModel)
    }
}