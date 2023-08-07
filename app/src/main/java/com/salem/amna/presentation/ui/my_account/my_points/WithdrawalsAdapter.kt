package com.salem.amna.presentation.ui.my_account.my_points

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.salem.amna.R
import com.salem.amna.data.models.response.points.Withdrawals
import kotlinx.android.synthetic.main.item_withdrawals.view.*

class WithdrawalsAdapter(private var list: MutableList<Withdrawals> = mutableListOf()): RecyclerView.Adapter<WithdrawalsAdapter.WithdrawalsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WithdrawalsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_withdrawals, parent, false)
        return WithdrawalsViewHolder(view)
    }

    override fun onBindViewHolder(holder: WithdrawalsViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    class WithdrawalsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(withdrawals: Withdrawals){
            itemView.tvPoints.text = "${withdrawals.points}"
            itemView.tvValue.text = "${withdrawals.money}"
            itemView.tvDate.text = "${withdrawals.date}"

            if (withdrawals.type == "wallet" || withdrawals.type == "bank_card"){
                itemView.ivType.setImageResource(R.drawable.ic_wallet)
            }else {
                itemView.ivType.setImageResource(R.drawable.ic_bank)
            }
        }
    }

}