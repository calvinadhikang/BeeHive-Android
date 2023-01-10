package com.example.beehive.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.beehive.R
import com.example.beehive.api_config.UserData
import com.example.beehive.data.Category
import com.example.beehive.env
import com.squareup.picasso.Picasso

class RVBeeworkerAdapter(
    var data: List<UserData>,
    var onClick: (position:Int)->Unit

): RecyclerView.Adapter<RVBeeworkerAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val lblNama: TextView = view.findViewById(R.id.lblNama)
        val lblDeskripsi: TextView = view.findViewById(R.id.lblDeskripsi)
        val lblRating: TextView = view.findViewById(R.id.lblRating)
        val lblFinished: TextView = view.findViewById(R.id.lblFinished)
        val imgBeeworker: ImageView = view.findViewById(R.id.imgBeeworker)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_beeworker, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var obj = data[position]

        holder.lblNama.text = obj.NAMA.toString()
        holder.lblDeskripsi.text = obj.BIO.toString()
        holder.lblRating.text = obj.RATING.toString()
        holder.lblFinished.text = obj.jumlahOrderDone.toString()
        Picasso.get()
            .load(env.URLIMAGE +"profile-pictures/${obj.PICTURE}")
            .fit()
            .centerCrop()
            .into(holder.imgBeeworker)

//            .onlyScaleDown()


        holder.imgBeeworker.clipToOutline = true
        holder.imgBeeworker.setBackgroundResource(R.drawable.rounded_corner_picture_1)
        holder.itemView.setOnClickListener {
            onClick(position)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}