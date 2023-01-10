package com.example.beehive.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.beehive.R
import com.example.beehive.data.Category

class RVCategoryLogoAdapter(
    var data: List<Category>,
    var cb: (pos: Int)->Unit
) : RecyclerView.Adapter<RVCategoryLogoAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.lblTitleSting)
        val tvStingRelated: TextView = view.findViewById(R.id.lblStingRelated)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_category_with_logo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var obj = data[position]

        holder.tvTitle.text = obj.NAMA_CATEGORY
        holder.tvStingRelated.text = obj.StingsRelatedCount.toString() + " Stings Available"
        holder.itemView.setOnClickListener {
            cb(position)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}