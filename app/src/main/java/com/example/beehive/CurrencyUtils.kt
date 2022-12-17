package com.example.beehive

import java.text.NumberFormat
import java.util.*

object CurrencyUtils {

    fun Int.toRupiah():String{
        val numberFormat = NumberFormat.getCurrencyInstance(Locale("in","ID"))
        return numberFormat.format(this)
    }
}