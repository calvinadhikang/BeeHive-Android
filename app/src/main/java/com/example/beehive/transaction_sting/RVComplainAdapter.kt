package com.example.beehive.transaction_sting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.beehive.R
import com.example.beehive.data.ComplainData

class RVComplainAdapter (
    var data: List<ComplainData>
) : RecyclerView.Adapter<RVComplainAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val lblNomorRevision: TextView = view.findViewById(R.id.lblNomorRevision)
        val lblComplainedAt: TextView = view.findViewById(R.id.lblComplainedAt)
        val lblMessage: TextView = view.findViewById(R.id.lblMessage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_complain, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var obj = data[position]

        holder.lblNomorRevision.text = "Revision #$position"
        holder.lblMessage.text = "Message : ${obj.COMPLAIN}"
        holder.lblComplainedAt.text = "Complained at ${obj.CREATED_AT}"
    }

    override fun getItemCount(): Int {
        return data.size
    }
}