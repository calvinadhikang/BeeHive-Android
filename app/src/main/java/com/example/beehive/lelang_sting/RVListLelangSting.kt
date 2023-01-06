package com.example.beehive.lelang_sting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.beehive.CurrencyUtils.toRupiah
import com.example.beehive.R
import com.example.beehive.data.ComplainData
import com.example.beehive.data.LelangStingData
import com.example.beehive.transaction_sting.RVComplainAdapter

class RVListLelangSting (
    var data: List<LelangStingData>,
    var detail : (lelang:LelangStingData)->Unit
) : RecyclerView.Adapter<RVListLelangSting.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val lblStatus: TextView = view.findViewById(R.id.lblStatus)
        val lblHarga: TextView = view.findViewById(R.id.lblHarga)
        val lblDeskripsi: TextView = view.findViewById(R.id.lblDeskripsi)
        val lblTanggal: TextView = view.findViewById(R.id.lblTanggal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_list_lelang, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var obj = data[position]
        when(obj.STATUS){
           " -1"-> holder.itemView.setBackgroundResource(R.drawable.background_pink_curved_full)
            "0"-> holder.itemView.setBackgroundResource(R.drawable.background_blue_curved_full)
            "1"-> holder.itemView.setBackgroundResource(R.drawable.background_orange_curved_full)
            "2"-> holder.itemView.setBackgroundResource(R.drawable.background_lightgreen_curved_full)
        }
        holder.lblStatus.text = obj.statusString
        holder.lblHarga.text = obj.COMMISION!!.toInt().toRupiah()
        holder.lblTanggal.text = obj.CREATED_AT
        var requirement:String = obj.REQUIREMENT_PROJECT!!
        if(requirement.length>50){
            requirement = requirement.substring(0,49)+"..."
        }
        holder.lblDeskripsi.text = requirement
        holder.itemView.setOnClickListener {
            detail(obj)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}