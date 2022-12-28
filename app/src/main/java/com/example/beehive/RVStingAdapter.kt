package com.example.beehive

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.beehive.data.Category
import com.example.beehive.data.StingMost
import com.squareup.picasso.Picasso

class RVStingAdapter(
    var data: List<StingMost>
) : RecyclerView.Adapter<RVStingAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama: TextView = view.findViewById(R.id.lblNamaWorker_card)
        val tvRating: TextView = view.findViewById(R.id.lblRatingWorker_card)
        val tvDesc: TextView = view.findViewById(R.id.lblDeskripsiSting_card)
        val tvPrice: TextView = view.findViewById(R.id.lblHargaSting_card)
        val imgSting: ImageView = view.findViewById(R.id.imgSting)
        val imgBeeworker: ImageView = view.findViewById(R.id.imgBeeworker)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.sting_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var obj = data[position]

        holder.tvNama.text = obj.EMAIL_BEEWORKER
        holder.tvRating.text = obj.RATING
        holder.tvDesc.text = obj.DESKRIPSI_BASIC
        holder.tvPrice.text = "Starting from Rp" + obj.PRICE_BASIC

        Picasso.get()
            .load(env.URLIMAGE+"sting-thumbnails/${obj.NAMA_THUMBNAIL}")
            .resize(100,100)
            .into(holder.imgSting)

        Picasso.get()
            .load(env.URLIMAGE+"profile-pictures/${obj.author!!.PICTURE}")
            .resize(40,40)
            .into(holder.imgBeeworker)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}