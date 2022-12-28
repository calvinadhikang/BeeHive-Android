package com.example.beehive

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.beehive.data.Category
import com.example.beehive.data.StingMost

class RVStingAdapter(
    var data: List<StingMost>
) : RecyclerView.Adapter<RVStingAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama: TextView = view.findViewById(R.id.lblNamaWorker_card)
        val tvRating: TextView = view.findViewById(R.id.lblRatingWorker_card)
        val tvDesc: TextView = view.findViewById(R.id.lblDeskripsiSting_card)
        val tvPrice: TextView = view.findViewById(R.id.lblHargaSting_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.sting_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var obj = data[position]

        holder.tvNama.text = obj.eMAILBEEWORKER
        holder.tvRating.text = obj.rATING
        holder.tvDesc.text = obj.dESKRIPSIBASIC
        holder.tvPrice.text = "Starting from Rp" + obj.pRICEBASIC
    }

    override fun getItemCount(): Int {
        return data.size
    }
}