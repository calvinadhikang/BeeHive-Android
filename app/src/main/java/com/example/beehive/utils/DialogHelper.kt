package com.example.beehive.utils

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.Button
import android.widget.TextView
import com.example.beehive.R

class DialogHelper {
    //class ini digunakan untuk membantu menampilkan modal dan confirmation
    companion object{
        fun showModal(
            activity: Activity,
            message:String,
            btnOkText:String = "OK",
            callbackFun:()->Unit
        ){
            val dialogBinding = activity.layoutInflater
                .inflate(R.layout.dialog_layout,null)
            val myDialog = Dialog(activity)
            myDialog.setContentView(dialogBinding)
            myDialog.setCancelable(true)
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            myDialog.show()
            val txtHeader: TextView = dialogBinding.findViewById(R.id.txtHeaderModal)
            txtHeader.text = message

            val btnOk: Button = dialogBinding.findViewById(R.id.btnOkModal)
            btnOk.text = btnOkText
            btnOk.setOnClickListener{
                myDialog.dismiss()
                callbackFun()
            }
        }
        fun showConfirmation(
            activity: Activity,
            message:String,
            btnSuccessText:String = "Yes",
            btnFailText:String = "No",
            callbackSuccess:()->Unit,
            callbackFail:()->Unit,){

            val dialogBinding = activity.layoutInflater
                .inflate(R.layout.dialog_confirmation_layout,null)
            val myDialog = Dialog(activity)
            myDialog.setContentView(dialogBinding)
            myDialog.setCancelable(true)
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            myDialog.show()
            val txtHeader: TextView = dialogBinding.findViewById(R.id.txtHeaderModal)
            txtHeader.text = message

            val btnYes: Button = dialogBinding.findViewById(R.id.btnYesModal)
            btnYes.text = btnSuccessText
            btnYes.setOnClickListener{
                myDialog.dismiss()
                callbackSuccess()
            }
            val btnNo: Button = dialogBinding.findViewById(R.id.btnNoModal)
            btnNo.text = btnFailText
            btnNo.setOnClickListener{
                myDialog.dismiss()
                callbackFail()
            }
        }
    }
}