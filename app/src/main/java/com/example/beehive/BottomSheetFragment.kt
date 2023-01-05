package com.example.beehive

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment: BottomSheetDialogFragment (){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottomsheet_fragment, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var txtNamaBilling: TextView = view.findViewById(R.id.txtNamaBilling)
        var txtStingBilling: TextView = view.findViewById(R.id.txtStingBilling)
        var txtTipeBilling: TextView = view.findViewById(R.id.txtTipeBilling)
        var txtTanggalBilling: TextView = view.findViewById(R.id.txtTanggalBilling)
        var txtNominalBilling: TextView = view.findViewById(R.id.txtNominalBilling)
    }

}