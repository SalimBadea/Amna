package com.salem.amna.presentation.ui.home.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.salem.amna.R
import com.salem.amna.data.models.response.home.Slider
import com.salem.amna.util.loadImageFromInternet
import com.smarteist.autoimageslider.SliderViewAdapter

class ItemSliderAdapter(private val context: Context) :
    SliderViewAdapter<ItemSliderAdapter.HomeItemVH>() {
    private var mList = mutableListOf<Slider>()
    private var item: Slider? = null
    fun addList(list: MutableList<Slider>) {
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
    }


    override fun getCount(): Int {
        return mList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?): HomeItemVH {
        val inflate =
            LayoutInflater.from(parent?.context).inflate(R.layout.home_item_slider, null)
        return HomeItemVH(inflate)
    }

    override fun onBindViewHolder(viewHolder: HomeItemVH, position: Int) {
        item = mList[position]

        Log.e("Home", "item >> ${item}")

        viewHolder.image.loadImageFromInternet(item?.image, ContextCompat.getDrawable(context, R.drawable.logo))

    }

    inner class HomeItemVH(itemview: View) : ViewHolder(itemview) {

        var image: ImageView = itemview.findViewById(R.id.imgViewHome)

    }

}