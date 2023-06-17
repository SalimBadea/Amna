package com.salem.amna.presentation.ui.add_product.adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.salem.amna.databinding.EmptyMediaBinding
import com.salem.amna.databinding.MediaRowBinding

class ItemImagesAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var list = ArrayList<Uri?>()
    val EMPTY_IMAGE = 1
    val NEW_IAMGE = 2

    interface OnClick {
        fun remove(position: Int)
    }

    fun setListener(listener: OnClick) {
        mListener = listener
    }

    private var mListener: OnClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ImageViewHolder(
            MediaRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val imageHolder = holder as ImageViewHolder
        imageHolder.itemImageView.setImageURI(list[position])
        imageHolder.ivCancel.setOnClickListener {
            Log.d(TAG, "position >> : $position")
//            removeItem(position)
            mListener!!.remove(position)
        }
    }

    override fun getItemCount(): Int {
        return (list.size)
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }

    fun addItem(it: Uri?) {
        list.add(it)
        Log.d(TAG, "addItem: ${list.size}")
        Log.d(TAG, "addItemList: ${list}")
        notifyDataSetChanged();
    }

    fun removeItem(position: Int) {
        list.removeAt(position)
        Log.d(TAG, "removeItem: ${list.size}")
        notifyItemRemoved(position)
    }


    inner class EmptyViewHolder(itemView: EmptyMediaBinding) :
        RecyclerView.ViewHolder(itemView.root) {

        val emptyImageView = itemView.emptyImageView
    }

    inner class ImageViewHolder(itemView: MediaRowBinding) :
        RecyclerView.ViewHolder(itemView.root) {

        val itemImageView = itemView.ivImage
        val ivCancel = itemView.ivImage

    }

    companion object {
        private const val TAG = "ReportImageAdapterAfter"
    }
}