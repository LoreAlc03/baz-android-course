package com.example.cryptocurrencyapp.data.remote.entity

import com.example.cryptocurrencyapp.domain.entity.TickerDTO
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CryptoTicker(
    @SerializedName("book")
    @Expose
    val coin: String = "",

    @SerializedName("volume")
    @Expose
    val volumeCoin: String = "",

    @SerializedName("high")
    @Expose
    val highCoin: String = "",

    @SerializedName("last")
    @Expose
    val lastCoin: String = "",

    @SerializedName("low")
    @Expose
    val lowCoin: String = "",

    @SerializedName("vwap")
    @Expose
    val vwapCoin: String = "",

    @SerializedName("ask")
    @Expose
    val askCoin: String = "",

    @SerializedName("bid")
    @Expose
    val bidCoin: String = "",

    @SerializedName("created_at")
    @Expose
    val createdAt: String = ""
)

fun CryptoTicker.toBitsoTicker(): TickerDTO {
    return TickerDTO(
        book = this.coin,
        high = highCoin,
        low = lowCoin,
    )
}
