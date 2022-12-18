package com.example.beehive.lelang_sting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.beehive.R
import com.example.beehive.user_auth.RegisterFragment

class CreateLelangStingFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_create_lelang_sting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnNavListLelangSting: Button = view.findViewById(R.id.btnNavListLelangSting)
        btnNavListLelangSting.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frMain, ListLelangStingFragment())
                .commit()
        }
    }
}