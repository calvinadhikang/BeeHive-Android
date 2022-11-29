package com.example.beehive

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RegisterFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var btnNextRegister:Button = view.findViewById(R.id.btnNextRegister)
        var btnBackRegister:FloatingActionButton = view.findViewById(R.id.btnBackRegister)
        var txtLinkToLogin:TextView = view.findViewById(R.id.txtLinkToLogin)
        var cbProceedContinue:CheckBox = view.findViewById(R.id.cbProceedContinue)
        var rbBeeworker:RadioButton = view.findViewById(R.id.rbBeeworker)
        var rbFarmer:RadioButton = view.findViewById(R.id.rbFarmer)
        var txtEmailRegister:EditText = view.findViewById(R.id.txtEmailRegister)

        btnBackRegister.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.frMain,LoginFragment())
                .commit()
        }
        txtLinkToLogin.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.frMain,LoginFragment())
                .commit()
        }
        btnNextRegister.setOnClickListener {
            var email:String = txtEmailRegister.text.toString()
            var role:Int = 0
            if(cbProceedContinue.isChecked){
                if(email==""){
                    Toast.makeText(
                        requireContext(),
                        "Email harus diisi!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if(rbBeeworker.isChecked) role = 2
                else if(rbFarmer.isChecked) role = 1
                if(role==0){
                    Toast.makeText(
                        requireContext(),
                        "Harus memilih role!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.frMain,RegisterFinalFragment(email,role))
                    .commit()
            }else{
                Toast.makeText(requireContext(),
                    "You have to check proceed to continue", Toast.LENGTH_SHORT).show()
            }
        }
    }
}