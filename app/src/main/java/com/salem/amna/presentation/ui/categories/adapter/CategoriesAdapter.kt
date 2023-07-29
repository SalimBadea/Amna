package com.salem.amna.presentation.ui.categories.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.salem.amna.R
import com.salem.amna.data.models.common.CategoriesModel
import com.salem.amna.databinding.ItemCategoryBinding
import com.salem.amna.util.loadImageFromInternet

class CategoriesAdapter(
    private var context: Context,
    private var itemClicked: OnItemClick? = null
) :
    RecyclerView.Adapter<CategoriesAdapter.OrderViewHolder>() {

    var list: MutableList<CategoriesModel> = arrayListOf()
        set(newlist) {
            field = newlist
            notifyDataSetChanged()
        }

    inner class OrderViewHolder(var binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindDataToView(model: CategoriesModel) {
            binding.model = model

            binding.ivCategory.loadImageFromInternet(
                model.image,
                ContextCompat.getDrawable(context, R.drawable.logo)
            )

            binding.tvTitle.text = "${model.name}"
            binding.tvContent.text = "${model.description}"

            binding.root.setOnClickListener {
                itemClicked?.orderClicked(model)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemCategoryBinding.inflate(
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
        fun orderClicked(item: CategoriesModel)
    }
}