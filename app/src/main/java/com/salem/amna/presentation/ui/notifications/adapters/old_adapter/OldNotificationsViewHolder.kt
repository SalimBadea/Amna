package com.salem.amna.presentation.ui.notifications.adapters.old_adapter

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.salem.amna.data.models.common.AddressModel
import com.salem.amna.data.models.common.NotificationsModel
import com.salem.amna.databinding.ItemAddressBinding
import com.salem.amna.databinding.ItemNewNotificationBinding
import com.salem.amna.databinding.ItemOldNotificationBinding

class OldNotificationsViewHolder(
    private val binding: ItemOldNotificationBinding,
    private val context: Context,
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: NotificationsModel) {
        binding.tvContent.text = item.content
        binding.tvDate.text = "${item.date} ${item.time}"
    }
}