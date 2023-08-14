package com.salem.amna.presentation.ui.notifications.adapters.old_adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.salem.amna.data.models.common.NotificationsModel
import com.salem.amna.databinding.ItemNewNotificationBinding
import com.salem.amna.databinding.ItemOldNotificationBinding

class OldNotificationsAdapter(private val mList: MutableList<NotificationsModel?> = mutableListOf()) :
    RecyclerView.Adapter<OldNotificationsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OldNotificationsViewHolder {
        val binding =
            ItemOldNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return OldNotificationsViewHolder(binding,parent.context)
    }

    override fun onBindViewHolder(holder: OldNotificationsViewHolder, position: Int) {
        holder.bind(mList[position]!!)

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setList(list: MutableList<NotificationsModel>){
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
    }

    private val callBack = object : DiffUtil.ItemCallback<NotificationsModel>() {
        override fun areItemsTheSame(oldItem: NotificationsModel, newItem: NotificationsModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NotificationsModel, newItem: NotificationsModel): Boolean {
            return oldItem == newItem
        }
    }
    var differ = AsyncListDiffer(this, callBack)
}