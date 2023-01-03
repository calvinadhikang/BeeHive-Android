package com.example.beehive

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.beehive.CurrencyUtils.toRupiah

class BuyStingFragment (
    var judul: String, var tipe: String, var desc: String, var harga: String
): Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buy_sting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val acti = activity as MainActivity
        acti.supportActionBar!!.show()
        acti.title = "Buy Sting"

        val txtNamaBuy: TextView = view.findViewById(R.id.txtNamaBuy)
        val txtBalanceBuy: TextView = view.findViewById(R.id.txtBalanceBuy)
        val txtTipeBuy: TextView = view.findViewById(R.id.txtTipeBuy)
        val txtDescBuy: TextView = view.findViewById(R.id.txtDescBuy)
        val txtHargaBuy: TextView = view.findViewById(R.id.txtHargaBuy)
        val txtRequirementBuy: TextView = view.findViewById(R.id.txtRequirementBuy)
        val btnBuyBuy: Button = view.findViewById(R.id.btnBuyBuy)

        txtBalanceBuy.text = acti.userLogin!!.BALANCE!!.toBigDecimal().toInt().toRupiah()
        txtNamaBuy.text = judul
        txtTipeBuy.text = tipe
        txtDescBuy.text = desc
        txtHargaBuy.text = harga

    }
}