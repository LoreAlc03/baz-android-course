package com.example.cryptocurrencyapp.data.remote.entity

import com.example.cryptocurrencyapp.domain.entity.OrderBookDTO
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CryptoOrderBook(
    @SerializedName("book")
    @Expose
    val coin: String = "",

    @SerializedName("price")
    @Expose
    val priceCoin: String = "",

    @SerializedName("amount")
    @Expose
    val amountCoin: String = ""
) {
    fun toOrderDTO(type: String): OrderBookDTO {
        return OrderBookDTO(
            book = coin,
            price = priceCoin,
            amount = amountCoin,
            type = type
        )
    }
}
