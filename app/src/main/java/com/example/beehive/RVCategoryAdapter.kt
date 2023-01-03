package com.example.beehive

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.beehive.data.Category

class RVCategoryAdapter(
    var data: List<Category>,
    var cb: (pos: Int)->Unit
) : RecyclerView.Adapter<RVCategoryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama: TextView = view.findViewById(R.id.lblNamaCategory_card)
        val tvStingsCount: TextView = view.findViewById(R.id.lblStingsAvailable_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var obj = data[position]

        holder.tvNama.text = obj.NAMA_CATEGORY
        holder.tvStingsCount.text = obj.StingsRelatedCount.toString() + " Stings Available"
        holder.itemView.setOnClickListener {
            cb(position)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}