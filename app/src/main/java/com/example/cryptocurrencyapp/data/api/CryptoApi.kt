package com.example.cryptocurrencyapp.data.api

import com.example.cryptocurrencyapp.data.entity.response.WCCryptoTickerResponse
import com.example.cryptocurrencyapp.data.entity.response.WCCryptoAvailableResponse
import com.example.cryptocurrencyapp.data.entity.response.WCCryptoOrderResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoApi {

    @GET (CryptoEndPoints.AVAILABLE_BOOKS)
    suspend fun getExchangeBooks(): WCCryptoAvailableResponse

    @GET (CryptoEndPoints.TICKER_BOOK)
    suspend fun getTickerBook(
        @Query("book") book: String
    ): WCCryptoTickerResponse

    @GET (CryptoEndPoints.ORDER_SPECIFIED_BOOK)
    suspend fun getOrderBook(
        @Query("book") book: String
    ): WCCryptoOrderResponse
}