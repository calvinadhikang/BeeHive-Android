package com.example.beehive.data

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ConvertionRatesDRO(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("time_next_update_unix")
	val timeNextUpdateUnix: Int? = null,

	@field:SerializedName("conversion_rates")
	val conversionRates: ConversionRates? = null,

	@field:SerializedName("time_next_update_utc")
	val timeNextUpdateUtc: String? = null,

	@field:SerializedName("documentation")
	val documentation: String? = null,

	@field:SerializedName("time_last_update_unix")
	val timeLastUpdateUnix: Int? = null,

	@field:SerializedName("base_code")
	val baseCode: String? = null,

	@field:SerializedName("time_last_update_utc")
	val timeLastUpdateUtc: String? = null,

	@field:SerializedName("terms_of_use")
	val termsOfUse: String? = null
) : Parcelable

@Parcelize
data class ConversionRates(

	@field:SerializedName("FJD")
	val FJD: String? = null,

	@field:SerializedName("MXN")
	val MXN: String? = null,

	@field:SerializedName("SCR")
	val SCR: String? = null,

	@field:SerializedName("TVD")
	val TVD: String? = null,

	@field:SerializedName("CDF")
	val CDF: String? = null,

	@field:SerializedName("BBD")
	val BBD: String? = null,

	@field:SerializedName("GTQ")
	val GTQ: String? = null,

	@field:SerializedName("CLP")
	val CLP: String? = null,

	@field:SerializedName("HNL")
	val HNL: String? = null,

	@field:SerializedName("UGX")
	val UGX: String? = null,

	@field:SerializedName("ZAR")
	val ZAR: String? = null,

	@field:SerializedName("TND")
	val TND: String? = null,

	@field:SerializedName("STN")
	val STN: String? = null,

	@field:SerializedName("SLE")
	val SLE: String? = null,

	@field:SerializedName("BSD")
	val BSD: String? = null,

	@field:SerializedName("SLL")
	val SLL: String? = null,

	@field:SerializedName("SDG")
	val SDG: String? = null,

	@field:SerializedName("IQD")
	val IQD: String? = null,

	@field:SerializedName("CUP")
	val CUP: String? = null,

	@field:SerializedName("GMD")
	val GMD: String? = null,

	@field:SerializedName("TWD")
	val TWD: String? = null,

	@field:SerializedName("RSD")
	val RSD: String? = null,

	@field:SerializedName("DOP")
	val DOP: String? = null,

	@field:SerializedName("KMF")
	val KMF: String? = null,

	@field:SerializedName("MYR")
	val MYR: String? = null,

	@field:SerializedName("FKP")
	val FKP: String? = null,

	@field:SerializedName("XOF")
	val XOF: String? = null,

	@field:SerializedName("GEL")
	val GEL: String? = null,

	@field:SerializedName("UYU")
	val UYU: String? = null,

	@field:SerializedName("MAD")
	val MAD: String? = null,

	@field:SerializedName("CVE")
	val CVE: String? = null,

	@field:SerializedName("TOP")
	val TOP: String? = null,

	@field:SerializedName("AZN")
	val AZN: String? = null,

	@field:SerializedName("OMR")
	val OMR: String? = null,

	@field:SerializedName("PGK")
	val PGK: String? = null,

	@field:SerializedName("KES")
	val KES: String? = null,

	@field:SerializedName("SEK")
	val SEK: String? = null,

	@field:SerializedName("BTN")
	val BTN: String? = null,

	@field:SerializedName("UAH")
	val UAH: String? = null,

	@field:SerializedName("GNF")
	val GNF: String? = null,

	@field:SerializedName("ERN")
	val ERN: String? = null,

	@field:SerializedName("MZN")
	val MZN: String? = null,

	@field:SerializedName("ARS")
	val ARS: String? = null,

	@field:SerializedName("QAR")
	val QAR: String? = null,

	@field:SerializedName("IRR")
	val IRR: String? = null,

	@field:SerializedName("CNY")
	val CNY: String? = null,

	@field:SerializedName("THB")
	val THB: String? = null,

	@field:SerializedName("UZS")
	val UZS: String? = null,

	@field:SerializedName("XPF")
	val XPF: String? = null,

	@field:SerializedName("MRU")
	val MRU: String? = null,

	@field:SerializedName("BDT")
	val BDT: String? = null,

	@field:SerializedName("LYD")
	val LYD: String? = null,

	@field:SerializedName("BMD")
	val BMD: String? = null,

	@field:SerializedName("KWD")
	val KWD: String? = null,

	@field:SerializedName("PHP")
	val PHP: String? = null,

	@field:SerializedName("RUB")
	val RUB: String? = null,

	@field:SerializedName("PYG")
	val PYG: String? = null,

	@field:SerializedName("ISK")
	val ISK: String? = null,

	@field:SerializedName("JMD")
	val JMD: String? = null,

	@field:SerializedName("COP")
	val COP: String? = null,

	@field:SerializedName("MKD")
	val MKD: String? = null,

	@field:SerializedName("USD")
	val USD: String? = null,

	@field:SerializedName("DZD")
	val DZD: String? = null,

	@field:SerializedName("PAB")
	val PAB: String? = null,

	@field:SerializedName("GGP")
	val GGP: String? = null,

	@field:SerializedName("SGD")
	val SGD: String? = null,

	@field:SerializedName("ETB")
	val ETB: String? = null,

	@field:SerializedName("JEP")
	val JEP: String? = null,

	@field:SerializedName("KGS")
	val KGS: String? = null,

	@field:SerializedName("SOS")
	val SOS: String? = null,

	@field:SerializedName("VUV")
	val VUV: String? = null,

	@field:SerializedName("LAK")
	val LAK: String? = null,

	@field:SerializedName("BND")
	val BND: String? = null,

	@field:SerializedName("XAF")
	val XAF: String? = null,

	@field:SerializedName("LRD")
	val LRD: String? = null,

	@field:SerializedName("CHF")
	val CHF: String? = null,

	@field:SerializedName("HRK")
	val HRK: String? = null,

	@field:SerializedName("ALL")
	val ALL: String? = null,

	@field:SerializedName("DJF")
	val DJF: String? = null,

	@field:SerializedName("VES")
	val VES: String? = null,

	@field:SerializedName("ZMW")
	val ZMW: String? = null,

	@field:SerializedName("TZS")
	val TZS: String? = null,

	@field:SerializedName("VND")
	val VND: String? = null,

	@field:SerializedName("AUD")
	val AUD: String? = null,

	@field:SerializedName("ILS")
	val ILS: String? = null,

	@field:SerializedName("GHS")
	val GHS: String? = null,

	@field:SerializedName("GYD")
	val GYD: String? = null,

	@field:SerializedName("BOB")
	val BOB: String? = null,

	@field:SerializedName("KHR")
	val KHR: String? = null,

	@field:SerializedName("MDL")
	val MDL: String? = null,

	@field:SerializedName("IDR")
	val IDR: String? = null,

	@field:SerializedName("KYD")
	val KYD: String? = null,

	@field:SerializedName("AMD")
	val AMD: String? = null,

	@field:SerializedName("BWP")
	val BWP: String? = null,

	@field:SerializedName("SHP")
	val SHP: String? = null,

	@field:SerializedName("TRY")
	val TRY: String? = null,

	@field:SerializedName("LBP")
	val LBP: String? = null,

	@field:SerializedName("TJS")
	val TJS: String? = null,

	@field:SerializedName("JOD")
	val JOD: String? = null,

	@field:SerializedName("AED")
	val AED: String? = null,

	@field:SerializedName("HKD")
	val HKD: String? = null,

	@field:SerializedName("RWF")
	val RWF: String? = null,

	@field:SerializedName("EUR")
	val EUR: String? = null,

	@field:SerializedName("FOK")
	val FOK: String? = null,

	@field:SerializedName("LSL")
	val LSL: String? = null,

	@field:SerializedName("DKK")
	val DKK: String? = null,

	@field:SerializedName("CAD")
	val CAD: String? = null,

	@field:SerializedName("KID")
	val KID: String? = null,

	@field:SerializedName("BGN")
	val BGN: String? = null,

	@field:SerializedName("MMK")
	val MMK: String? = null,

	@field:SerializedName("MUR")
	val MUR: String? = null,

	@field:SerializedName("NOK")
	val NOK: String? = null,

	@field:SerializedName("SYP")
	val SYP: String? = null,

	@field:SerializedName("IMP")
	val IMP: String? = null,

	@field:SerializedName("ZWL")
	val ZWL: String? = null,

	@field:SerializedName("GIP")
	val GIP: String? = null,

	@field:SerializedName("RON")
	val RON: String? = null,

	@field:SerializedName("LKR")
	val LKR: String? = null,

	@field:SerializedName("NGN")
	val NGN: String? = null,

	@field:SerializedName("CRC")
	val CRC: String? = null,

	@field:SerializedName("CZK")
	val CZK: String? = null,

	@field:SerializedName("PKR")
	val PKR: String? = null,

	@field:SerializedName("XCD")
	val XCD: String? = null,

	@field:SerializedName("ANG")
	val ANG: String? = null,

	@field:SerializedName("HTG")
	val HTG: String? = null,

	@field:SerializedName("BHD")
	val BHD: String? = null,

	@field:SerializedName("KZT")
	val KZT: String? = null,

	@field:SerializedName("SRD")
	val SRD: String? = null,

	@field:SerializedName("SZL")
	val SZL: String? = null,

	@field:SerializedName("SAR")
	val SAR: String? = null,

	@field:SerializedName("TTD")
	val TTD: String? = null,

	@field:SerializedName("YER")
	val YER: String? = null,

	@field:SerializedName("MVR")
	val MVR: String? = null,

	@field:SerializedName("AFN")
	val AFN: String? = null,

	@field:SerializedName("INR")
	val INR: String? = null,

	@field:SerializedName("AWG")
	val AWG: String? = null,

	@field:SerializedName("KRW")
	val KRW: String? = null,

	@field:SerializedName("NPR")
	val NPR: String? = null,

	@field:SerializedName("JPY")
	val JPY: String? = null,

	@field:SerializedName("MNT")
	val MNT: String? = null,

	@field:SerializedName("AOA")
	val AOA: String? = null,

	@field:SerializedName("PLN")
	val PLN: String? = null,

	@field:SerializedName("GBP")
	val GBP: String? = null,

	@field:SerializedName("SBD")
	val SBD: String? = null,

	@field:SerializedName("BYN")
	val BYN: String? = null,

	@field:SerializedName("HUF")
	val HUF: String? = null,

	@field:SerializedName("BIF")
	val BIF: String? = null,

	@field:SerializedName("MWK")
	val MWK: String? = null,

	@field:SerializedName("MGA")
	val MGA: String? = null,

	@field:SerializedName("XDR")
	val XDR: String? = null,

	@field:SerializedName("BZD")
	val BZD: String? = null,

	@field:SerializedName("BAM")
	val BAM: String? = null,

	@field:SerializedName("EGP")
	val EGP: String? = null,

	@field:SerializedName("MOP")
	val MOP: String? = null,

	@field:SerializedName("NAD")
	val NAD: String? = null,

	@field:SerializedName("SSP")
	val SSP: String? = null,

	@field:SerializedName("NIO")
	val NIO: String? = null,

	@field:SerializedName("PEN")
	val PEN: String? = null,

	@field:SerializedName("NZD")
	val NZD: String? = null,

	@field:SerializedName("WST")
	val WST: String? = null,

	@field:SerializedName("TMT")
	val TMT: String? = null,

	@field:SerializedName("BRL")
	val BRL: String? = null
) : Parcelable
