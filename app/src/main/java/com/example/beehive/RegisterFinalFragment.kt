package com.example.beehive

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RegisterFinalFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_register_final, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lateinit var txtLinkToLogin2:TextView
        lateinit var btnBackRegisterFinal:FloatingActionButton
        lateinit var btnRegister:Button
        txtLinkToLogin2 = view.findViewById(R.id.txtLinkToLogin2)
        btnBackRegisterFinal = view.findViewById(R.id.btnBackRegisterFinal)
        btnRegister = view.findViewById(R.id.btnRegister)

        txtLinkToLogin2.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.frMain,LoginFragment())
                .commit()
        }
        btnBackRegisterFinal.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.frMain,RegisterFragment())
                .commit()
        }
        btnRegister.setOnClickListener{
//            parentFragmentManager.beginTransaction()
//                .replace(R.id.frMain,LoginFragment())
//                .commit()
        }
    }
}