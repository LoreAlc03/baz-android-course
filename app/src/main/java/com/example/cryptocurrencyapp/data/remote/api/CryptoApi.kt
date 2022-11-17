package com.example.cryptocurrencyapp.data.remote.api

import com.example.cryptocurrencyapp.data.remote.entity.response.CryptoAvailableResponse
import com.example.cryptocurrencyapp.data.remote.entity.response.CryptoOrderResponse
import com.example.cryptocurrencyapp.data.remote.entity.response.CryptoTickerResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoApi {

    @GET(CryptoEndPoints.TICKER_BOOK)
    suspend fun getTickerBook(
        @Query("book") book: String
    ): CryptoTickerResponse

    @GET(CryptoEndPoints.ORDER_SPECIFIED_BOOK)
    suspend fun getOrderBook(
        @Query("book") book: String
    ): CryptoOrderResponse

    @GET(CryptoEndPoints.AVAILABLE_BOOKS)
    fun getExchangeBooksRx(): Single<CryptoAvailableResponse>
}
