package com.example.beehive

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beehive.activities.MainActivity
import com.example.beehive.adapters.RVCategoryAdapter
import com.example.beehive.adapters.RVCategoryLogoAdapter
import com.example.beehive.data.Category

class SearchFragment(
    var listCategory: List<Category>,
    var searcKey: String = ""
) : Fragment() {

    lateinit var rv: RecyclerView
    lateinit var edtSearch: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment a
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val acti = activity as MainActivity
        acti.supportActionBar!!.hide()
        acti.title = "Categories Available"

        rv = view.findViewById(R.id.rvSearch)
        edtSearch = view.findViewById(R.id.edtSearch)

        //initialize list
        //kalau ada searchKey yang dikirim lewat parameter, maka data ditampilkan sesuai searchKey
        //searchKey default value = ""
        var listShow = listOf<Category>()
        if (searcKey == ""){
            listShow = listCategory
        }else{
            edtSearch.setText(searcKey)
            listShow = listCategory.filter { it.NAMA_CATEGORY.lowercase().contains(searcKey.lowercase()) }
        }
        //initialize Adapter
        rv.adapter = RVCategoryLogoAdapter(listShow, { pos ->
            var key = listCategory[pos].ID_CATEGORY.toString()
            (activity as MainActivity).detailCategory(key, listShow[pos].NAMA_CATEGORY)
        })
        rv.layoutManager = LinearLayoutManager(view.context)

        edtSearch.addTextChangedListener { obj ->
            var text = edtSearch.text.toString()

            //Re-Initialize Adapter
            listShow = listCategory.filter { it.NAMA_CATEGORY.lowercase().contains(text.lowercase()) }
            rv.adapter = RVCategoryLogoAdapter(listShow, { pos ->
                var key = listCategory[pos].ID_CATEGORY.toString()
                (activity as MainActivity).detailCategory(key, listShow[pos].NAMA_CATEGORY)
            })
        }
    }

}