package com.example.beehive.user_auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.beehive.MainActivity
import com.example.beehive.R

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
        val acti = activity as MainActivity
        var btnToLogin: Button = view.findViewById(R.id.btnToLogin)
        var btnToRegister: Button = view.findViewById(R.id.btnToRegister)
        btnToLogin.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frMain, LoginFragment())
                .commit()
        }
        btnToRegister.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frMain, RegisterFragment())
                .commit()
        }
    }
}