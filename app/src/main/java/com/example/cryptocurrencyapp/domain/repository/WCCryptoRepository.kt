package com.example.cryptocurrencyapp.domain.repository

import com.example.cryptocurrencyapp.domain.entity.WCCOrdeRDTO
import com.example.cryptocurrencyapp.domain.entity.WCCTickerDTO
import com.example.cryptocurrencyapp.domain.entity.WCCryptoBookDTO
import io.reactivex.Single

interface WCCryptoRepository {
    suspend fun getTickerBook(book: String): WCCTickerDTO
    suspend fun getOrderBook(book: String): WCCOrdeRDTO
    fun getAvailableRx(): Single<List<WCCryptoBookDTO>>
}
