package com.example.cryptocurrencyapp.data.remote.entity.response

import com.example.cryptocurrencyapp.data.remote.entity.CryptoTicker
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CryptoTickerResponse(
    @SerializedName("success")
    @Expose
    val success: Boolean = false,

    @SerializedName("payload")
    @Expose
    val tickerCoin: CryptoTicker? = null
)
