package com.example.cryptocurrencyapp.data.remote.entity.response

import com.example.cryptocurrencyapp.data.remote.entity.CryptoOrderBook
import com.example.cryptocurrencyapp.domain.entity.OrderListDTO
import com.example.cryptocurrencyapp.utils.CryptoConstants
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OrderResponse(
    @SerializedName("asks")
    @Expose
    val ask: List<CryptoOrderBook>,

    @SerializedName("bids")
    @Expose
    val bid: List<CryptoOrderBook>,

    @SerializedName("updated_at")
    @Expose
    val updatedAt: String = "",

    @SerializedName("sequence")
    @Expose
    val sequence: String = ""
) {
    fun toOrder(): OrderListDTO {
        return OrderListDTO(
            ask = ask.map { ask ->
                ask.toOrderDTO(CryptoConstants.ASK)
            }.toMutableList(),
            bids = bid.map { bid ->
                bid.toOrderDTO(CryptoConstants.BID)
            }.toMutableList()
        )
    }
}
