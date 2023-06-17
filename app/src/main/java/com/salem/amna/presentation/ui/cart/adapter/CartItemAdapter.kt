package com.salem.amna.presentation.ui.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.salem.amna.data.models.common.AddressModel
import com.salem.amna.data.models.common.CategoryItemModel
import com.salem.amna.databinding.ItemProductBinding

class CartItemAdapter:
    RecyclerView.Adapter<CartItemViewHolder>() {

    private var onDeleteClicked: ((item: CategoryItemModel) -> Unit)? = null

    var list: MutableList<CategoryItemModel> = arrayListOf()
        set(newlist) {
            field = newlist
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val binding =
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CartItemViewHolder(binding,parent.context,onDeleteClicked)
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setOnDeleteClickedListener(onDeleteClicked: (item: CategoryItemModel) -> Unit) {
        this.onDeleteClicked = onDeleteClicked
    }


}