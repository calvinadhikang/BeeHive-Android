package com.example.beehive

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class LoginFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lateinit var txtLinkToRegister: TextView
        lateinit var btnLogin: Button
        btnLogin = view.findViewById(R.id.btnLogin)
        txtLinkToRegister = view.findViewById(R.id.txtLinkToRegister)
        txtLinkToRegister.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.frMain,RegisterFragment())
                .commit()
        }
    }
}