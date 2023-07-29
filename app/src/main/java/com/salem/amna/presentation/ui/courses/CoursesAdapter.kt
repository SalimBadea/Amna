package com.salem.amna.presentation.ui.courses

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.salem.amna.R
import com.salem.amna.data.models.common.CategoriesModel
import com.salem.amna.data.models.common.CoursesModel
import com.salem.amna.databinding.ItemCategoryBinding
import com.salem.amna.databinding.ItemCourseBinding
import com.salem.amna.util.loadImageFromInternet

class CoursesAdapter(
    private var context: Context,
    private var itemShowClicked: OnItemClick? = null
) :
    RecyclerView.Adapter<CoursesAdapter.OrderViewHolder>() {

    var list: MutableList<CoursesModel> = arrayListOf()
        set(newlist) {
            field = newlist
            notifyDataSetChanged()
        }

    inner class OrderViewHolder(var binding: ItemCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindDataToView(model: CoursesModel) {

            binding.ivCourse.loadImageFromInternet(
                model.image,
                ContextCompat.getDrawable(context, R.drawable.logo)
            )

            binding.tvTitle.text = "${model.title}"

            binding.watchBtn.setOnClickListener {
                itemShowClicked?.itemShowClicked(model)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemCourseBinding.inflate(
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
        fun itemShowClicked(item: CoursesModel)
    }
}