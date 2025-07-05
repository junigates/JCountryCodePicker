package com.servicemarket.app.utils.jCountryData

sealed class JCountryListType {
    data class JCountry(val alpha2: String,  val alpha3: String,  val countryCode: String, val countryName: String, val flagUrl: String) : JCountryListType()
    data class JCountryFirstAlphabet(val alphabet: String) : JCountryListType()
}