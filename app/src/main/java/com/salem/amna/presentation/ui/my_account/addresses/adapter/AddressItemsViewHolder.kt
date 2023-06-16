package com.salem.amna.presentation.ui.my_account.addresses.adapter

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.salem.amna.data.models.common.AddressModel
import com.salem.amna.databinding.ItemAddressBinding

class AddressItemsViewHolder(
    private val binding: ItemAddressBinding,
    private val context: Context,
    private val onAddressClickListener: ((item: AddressModel) -> Unit)?,
    private val onDeleteClicked: ((item: AddressModel) -> Unit)?
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: AddressModel) {
        binding.tvAddressTitle.text = item.city?.name
        binding.tvAddressDesc.text = item.address

        Log.e("AddressItemsViewHolder", "addresses >> $item")
//        when(item.type){
//            "home" -> {
//            binding.addressImage.setImageResource(R.drawable.ic_address)
//            }
//            "work" -> {
//            binding.addressImage.setImageResource(R.drawable.ic_work)
//            }
//            "friend" -> {
//            binding.addressImage.setImageResource(R.drawable.ic_address)
//            }
//            "rest" -> {
//            binding.addressImage.setImageResource(R.drawable.ic_rest)
//            }
//        }
//        if (item.isDefault == true) {
//            binding.addressDetails.setTextColor(ContextCompat.getColor(context, R.color.main_black))
//            binding.phoneNumber.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
//            binding.addressImage.setTint(R.color.colorPrimary)
//            binding.acceptButton.setTint(R.color.accept_color_text)
//        }
        binding.deleteBtn.setOnClickListener {
            onDeleteClicked?.let { it1 -> it1(item) }
        }

        binding.root.setOnClickListener {
            onAddressClickListener?.let { it1 -> it1(item) }
        }
    }
}