package com.example.beehive

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class UserBeforeLoginFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_user_before_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lateinit var btnToLogin: Button
        lateinit var btnToRegister: Button
        val acti = activity as MainActivity
        btnToLogin = view.findViewById(R.id.btnToLogin)
        btnToRegister = view.findViewById(R.id.btnToRegister)
        btnToLogin.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frMain,LoginFragment())
                .commit()
        }
        btnToRegister.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frMain,RegisterFragment())
                .commit()
        }
    }
}