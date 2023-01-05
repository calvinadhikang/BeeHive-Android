package com.example.beehive.transaction_sting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beehive.activities.MainActivity
import com.example.beehive.R
import com.example.beehive.data.ComplainData
import com.example.beehive.data.LelangStingData
import com.example.beehive.data.TransactionStingData

class ListComplainsFragment(
    var mode:String = "transaction", //transaction atau lelang
    var transaction: TransactionStingData? = null,
    var lelang: LelangStingData? = null,
    var complains:List<ComplainData>
    ) : Fragment() {
    lateinit var rvComplain:RecyclerView
    lateinit var btnBack:ImageButton
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
        return inflater.inflate(R.layout.fragment_list_complains, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val acti = activity as MainActivity
        acti.supportActionBar!!.hide()
        acti.title = "List Complain Sting"
        var adapter:RVComplainAdapter = RVComplainAdapter(complains)
        rvComplain = view.findViewById(R.id.rvComplain)
        btnBack = view.findViewById(R.id.btnBack)
        rvComplain.adapter = adapter
        rvComplain.layoutManager =
            GridLayoutManager(requireContext(), 1, GridLayoutManager.VERTICAL, true)

        btnBack.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.frMain, DetailOrderedStingInProgressFragment(mode,transaction,lelang))
                .commit()
        }
    }
}