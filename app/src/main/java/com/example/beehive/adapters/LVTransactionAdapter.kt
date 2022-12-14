package com.example.beehive.adapters

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.beehive.R
import com.example.beehive.activities.MainActivity
import com.example.beehive.api_config.UserData
import com.example.beehive.data.TransactionStingData
import com.example.beehive.env
import com.squareup.picasso.Picasso

class LVTransactionAdapter (
    private val context: Activity,
    private val listTransaction: ArrayList<TransactionStingData>,
    var userLogin: UserData,
    val onClick: (namaBeeworker: String, requirement: String, harga: String, tglMulai: String, tglSelesai: String, trans: TransactionStingData) -> Unit,
    val cancelTrans: (id: String) -> Unit
): ArrayAdapter<TransactionStingData>(context, R.layout.card_transacrtion, listTransaction){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = context.layoutInflater.inflate(R.layout.card_transacrtion, null, true)
        val txtNamaWorkerNotif = view.findViewById<TextView>(R.id.txtNamaWorkerNotif)
        val txtTglTransNotif = view.findViewById<TextView>(R.id.txtTglTransNotif)
        val txtTipeNotif = view.findViewById<TextView>(R.id.txtTipeNotif)
        val txtReqNotif = view.findViewById<TextView>(R.id.txtReqNotif)
        val imgStingNotif = view.findViewById<ImageView>(R.id.imgStingNotif)
        val acti = context as MainActivity
        view.setOnClickListener {
            if (listTransaction[position].STATUS == "0"){
                val popUp = PopupMenu(context,it)
                popUp.menuInflater.inflate(R.menu.menu_cancel,popUp.menu)

                popUp.setOnMenuItemClickListener {
                    return@setOnMenuItemClickListener when(it.itemId){
                        R.id.cancel_menu ->{
                            acti.showConfirmation("Apakah anda yakin untuk membatalkan order ini?","Yes",
                            "No",{
                                    cancelTrans(listTransaction[position].ID_TRANSACTION!!)
                                },{})
                            true
                        }
                        else ->{
                            false
                        }
                    }
                }
                popUp.show()
            }
            else{
                var temp = "Kosong"
                if (listTransaction[position].DATE_END != null){
                    temp = listTransaction[position].DATE_END!!
                }
                onClick(listTransaction[position].sting!!.author!!.NAMA!!,
                    listTransaction[position].REQUIREMENT_PROJECT!!,
                    listTransaction[position].COMMISION!!,
                    listTransaction[position].DATE_START.toString(),
                    temp,
                    listTransaction[position]
                )
            }
        }

//        Log.e("ERROR AUTHOR GAN", "${listTransaction[position].sting.author.}")

        Picasso.get()
            .load(env.URLIMAGE +"profile-pictures/${listTransaction[position].sting!!.author!!.PICTURE}")
            .resize(250,250)
            .into(imgStingNotif)

        imgStingNotif.clipToOutline = true
        imgStingNotif.setBackgroundResource(R.drawable.full_rounded_picture)

        txtNamaWorkerNotif.text = listTransaction[position].sting!!.author!!.NAMA.toString()
        txtTglTransNotif.text = listTransaction[position].CREATED_AT.toString()
        txtTipeNotif.text = listTransaction[position].IS_PREMIUM
        if (listTransaction[position].IS_PREMIUM.toString().toInt() == 1){
            txtTipeNotif.text = "Premium Plan"
        }
        else{
            txtTipeNotif.text = "Basic Plan"
        }
        if (listTransaction[position].REQUIREMENT_PROJECT.toString().length > 35){
            txtReqNotif.text = listTransaction[position].REQUIREMENT_PROJECT.toString().substring(0,35) + "..."
        }
        else{
            txtReqNotif.text = listTransaction[position].REQUIREMENT_PROJECT.toString()
        }



        return view
    }
}