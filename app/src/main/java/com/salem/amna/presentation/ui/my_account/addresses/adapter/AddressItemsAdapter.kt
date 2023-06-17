package com.salem.amna.presentation.ui.my_account.addresses.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.salem.amna.data.models.common.AddressModel
import com.salem.amna.databinding.ItemAddressBinding

class AddressItemsAdapter(private val mContext: Context, private val mList: MutableList<AddressModel?>) :
    RecyclerView.Adapter<AddressItemsViewHolder>() {
    private var onEditAddressClickListener: ((item: AddressModel) -> Unit)? = null
    private var onDeleteClicked: ((item: AddressModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressItemsViewHolder {
        val binding =
            ItemAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return AddressItemsViewHolder(binding,parent.context, onEditAddressClickListener,onDeleteClicked)
    }

    override fun onBindViewHolder(holder: AddressItemsViewHolder, position: Int) {
        holder.bind(mList[position]!!)

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    private fun setList(list: MutableList<AddressModel>){
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
    }

    fun setOnEditAddressClickListener(onEditAddressClickListener: (item: AddressModel) -> Unit) {
        this.onEditAddressClickListener = onEditAddressClickListener
    }
    fun setOnDeleteClickedListener(onDeleteClicked: (item: AddressModel) -> Unit) {
        this.onDeleteClicked = onDeleteClicked
    }


    private val callBack = object : DiffUtil.ItemCallback<AddressModel>() {
        override fun areItemsTheSame(oldItem: AddressModel, newItem: AddressModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AddressModel, newItem: AddressModel): Boolean {
            return oldItem == newItem
        }
    }
    var differ = AsyncListDiffer(this, callBack)
}