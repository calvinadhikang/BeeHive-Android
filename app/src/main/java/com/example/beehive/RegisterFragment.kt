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
        lateinit var txtLinkToLogin:TextView
        lateinit var btnNextRegister:Button
        lateinit var btnBackRegister:FloatingActionButton
        lateinit var cbProceedContinue:CheckBox
        lateinit var rbBeeworker:RadioButton
        lateinit var rbFarmer:RadioButton
        btnNextRegister = view.findViewById(R.id.btnNextRegister)
        btnBackRegister = view.findViewById(R.id.btnBackRegister)
        txtLinkToLogin = view.findViewById(R.id.txtLinkToLogin)
        cbProceedContinue = view.findViewById(R.id.cbProceedContinue)
        rbBeeworker = view.findViewById(R.id.rbBeeworker)
        rbFarmer = view.findViewById(R.id.rbFarmer)

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
            if(cbProceedContinue.isChecked){
                parentFragmentManager.beginTransaction()
                    .replace(R.id.frMain,RegisterFinalFragment())
                    .commit()
            }else{
                Toast.makeText(requireContext(),
                    "You have to check proceed to continue", Toast.LENGTH_SHORT).show()
            }
        }
    }
}