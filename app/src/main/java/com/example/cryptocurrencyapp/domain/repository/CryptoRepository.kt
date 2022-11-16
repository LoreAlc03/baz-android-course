package com.example.cryptocurrencyapp.domain.repository

import com.example.cryptocurrencyapp.domain.entity.OrderListDTO
import com.example.cryptocurrencyapp.domain.entity.TickerDTO
import com.example.cryptocurrencyapp.domain.entity.CryptoBookDTO
import io.reactivex.Single

interface CryptoRepository {
    suspend fun getTickerBook(book: String): TickerDTO
    suspend fun getOrderBook(book: String): OrderListDTO
    fun getAvailableRx(): Single<List<CryptoBookDTO>>
}
