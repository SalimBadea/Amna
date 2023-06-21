package com.salem.amna.presentation.ui.home.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.salem.amna.R
import com.salem.amna.data.models.common.OrderModel
import com.salem.amna.data.models.response.home.Slider
import com.salem.amna.util.loadImageFromInternet
import com.smarteist.autoimageslider.SliderViewAdapter

class ItemOrderAdapter(private val context: Context) :
    RecyclerView.Adapter<ItemOrderAdapter.OrderItemVH>() {
    private var mList = mutableListOf<OrderModel>()

    fun addList(list: MutableList<OrderModel>) {
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(viewHolder: OrderItemVH, position: Int) {
       val item = mList[position]

        viewHolder.number.text = "${item.number}"
        viewHolder.date.text = "${item.date}"
        when(item.status){
            1 ->{
                viewHolder.status.text = "${context.getString(R.string.pending)}"
                viewHolder.status.setTextColor(ContextCompat.getColor(context, R.color.orange))
                viewHolder.status.background = ContextCompat.getDrawable(context, R.drawable.bg_orange_status)
                viewHolder.ivPending.setImageResource(R.drawable.ic_radio_checked)
                viewHolder.ivAccepted.setImageResource(R.drawable.ic_radio_semi_checked)
                viewHolder.ivDelivery.setImageResource(R.drawable.ic_radio_unchecked)
                viewHolder.ivCoupon.setImageResource(R.drawable.ic_radio_unchecked)
                viewHolder.view1.background = ContextCompat.getDrawable(context, R.drawable.view_background_green)
                viewHolder.view2.background = ContextCompat.getDrawable(context, R.drawable.view_background_gray)
                viewHolder.view3.background = ContextCompat.getDrawable(context, R.drawable.view_background_gray)
            }
            2 -> {
                viewHolder.status.text = "${context.getString(R.string.accepted)}"
                viewHolder.status.setTextColor(ContextCompat.getColor(context, R.color.blue))
                viewHolder.status.background = ContextCompat.getDrawable(context, R.drawable.bg_blue_status)
                viewHolder.ivPending.setImageResource(R.drawable.ic_radio_checked)
                viewHolder.ivAccepted.setImageResource(R.drawable.ic_radio_checked)
                viewHolder.ivDelivery.setImageResource(R.drawable.ic_radio_semi_checked)
                viewHolder.ivCoupon.setImageResource(R.drawable.ic_radio_unchecked)
                viewHolder.view1.background = ContextCompat.getDrawable(context, R.drawable.view_background_green)
                viewHolder.view2.background = ContextCompat.getDrawable(context, R.drawable.view_background_green)
                viewHolder.view3.background = ContextCompat.getDrawable(context, R.drawable.view_background_gray)
            }
            3 -> {
                viewHolder.status.text = "${context.getString(R.string.finished)}"
                viewHolder.status.setTextColor(ContextCompat.getColor(context, R.color.mainGreen))
                viewHolder.status.background = ContextCompat.getDrawable(context, R.drawable.bg_green_status)
                viewHolder.ivPending.setImageResource(R.drawable.ic_radio_checked)
                viewHolder.ivAccepted.setImageResource(R.drawable.ic_radio_checked)
                viewHolder.ivDelivery.setImageResource(R.drawable.ic_radio_checked)
                viewHolder.ivCoupon.setImageResource(R.drawable.ic_radio_semi_checked)
                viewHolder.view1.background = ContextCompat.getDrawable(context, R.drawable.view_background_green)
                viewHolder.view2.background = ContextCompat.getDrawable(context, R.drawable.view_background_green)
                viewHolder.view3.background = ContextCompat.getDrawable(context, R.drawable.view_background_green)
            }
            4 -> {
                viewHolder.status.text = "${context.getString(R.string.rejected)}"
                viewHolder.status.setTextColor(ContextCompat.getColor(context, R.color.red))
                viewHolder.status.background = ContextCompat.getDrawable(context, R.drawable.bg_red_status)
                viewHolder.ivPending.setImageResource(R.drawable.ic_radio_unchecked)
                viewHolder.ivAccepted.setImageResource(R.drawable.ic_radio_unchecked)
                viewHolder.ivDelivery.setImageResource(R.drawable.ic_radio_unchecked)
                viewHolder.ivCoupon.setImageResource(R.drawable.ic_radio_unchecked)
                viewHolder.view1.background = ContextCompat.getDrawable(context, R.drawable.view_background_gray)
                viewHolder.view2.background = ContextCompat.getDrawable(context, R.drawable.view_background_gray)
                viewHolder.view3.background = ContextCompat.getDrawable(context, R.drawable.view_background_gray)
            }
        }
    }

    inner class OrderItemVH(itemview: View) : RecyclerView.ViewHolder(itemview) {
        var number: TextView = itemview.findViewById(R.id.tvOrderNumber)
        var date: TextView = itemview.findViewById(R.id.tvOrderDate)
        var status: TextView = itemview.findViewById(R.id.tvOrderStatus)
        var ivPending: ImageView = itemview.findViewById(R.id.ivPending)
        var ivAccepted: ImageView = itemview.findViewById(R.id.ivAccepted)
        var ivDelivery: ImageView = itemview.findViewById(R.id.ivDelivery)
        var ivCoupon: ImageView = itemview.findViewById(R.id.ivCoupon)
        var view1: View = itemview.findViewById(R.id.pendingView)
        var view2: View = itemview.findViewById(R.id.acceptView)
        var view3: View = itemview.findViewById(R.id.deliveryView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemVH {
        val inflate =
            LayoutInflater.from(parent.context).inflate(R.layout.item_home_order, parent, false)
        return OrderItemVH(inflate)
    }

    override fun getItemCount(): Int = mList.size

}