package com.example.cryptocurrencyapp.data.remote.entity.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CryptoOrderResponse(
    @SerializedName("success")
    @Expose
    val success: Boolean = false,

    @SerializedName("payload")
    @Expose
    val orderCoin: OrderResponse? = null
)
