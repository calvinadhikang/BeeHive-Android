package com.example.beehive

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.beehive.data.StingData
import com.squareup.picasso.Picasso

class RVStingAdapter(
    var data: List<StingData>
) : RecyclerView.Adapter<RVStingAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama: TextView = view.findViewById(R.id.lblNamaWorker_card)
        val tvRating: TextView = view.findViewById(R.id.lblRatingWorker_card)
        val tvDesc: TextView = view.findViewById(R.id.lblDeskripsiSting_card)
        val tvPrice: TextView = view.findViewById(R.id.lblHargaSting_card)
        val imgThumbnail: ImageView = view.findViewById(R.id.stingThumbnail)
        val workerThumbnail: ImageView = view.findViewById(R.id.workerThumbnail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.sting_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var obj = data[position]

        holder.tvNama.text = obj.author!!.NAMA
        holder.tvRating.text = obj.RATING
        holder.tvDesc.text = obj.DESKRIPSI_BASIC
        holder.tvPrice.text = "Starting from Rp" + obj.PRICE_BASIC

        //Set WorkerThumbnail
        Picasso.get()
            .load(env.URLIMAGE+"profile-pictures/${obj.author!!.PICTURE}")
            .resize(50,50)
            .into(holder.workerThumbnail)

        //Set StingThumbnail
        Picasso.get()
            .load(env.URLIMAGE+"sting-thumbnails/${obj.NAMA_THUMBNAIL}")
            .resize(50,50)
            .into(holder.imgThumbnail)

        holder.workerThumbnail.clipToOutline = true
        holder.workerThumbnail.setBackgroundResource(R.drawable.full_rounded_picture)
        holder.imgThumbnail.clipToOutline = true
        holder.imgThumbnail.setBackgroundResource(R.drawable.full_rounded_picture)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}