package com.example.cryptocurrencyapp.data.remote.data_source

import com.example.cryptocurrencyapp.data.remote.api.CryptoApi
import com.example.cryptocurrencyapp.data.remote.entity.response.CryptoAvailableResponse
import com.example.cryptocurrencyapp.data.remote.entity.toBitsoTicker
import com.example.cryptocurrencyapp.domain.entity.OrderListDTO
import com.example.cryptocurrencyapp.domain.entity.TickerDTO
import com.example.cryptocurrencyapp.domain.entity.CryptoBookDTO
import com.example.cryptocurrencyapp.domain.repository.CryptoRepository
import io.reactivex.Single
import javax.inject.Inject

class CryptoDataSource @Inject constructor(private val api: CryptoApi) : CryptoRepository {

    override suspend fun getTickerBook(book: String): TickerDTO =
        api.getTickerBook(book = book).tickerCoin?.toBitsoTicker() ?: TickerDTO()

    override suspend fun getOrderBook(book: String): OrderListDTO =
        api.getOrderBook(book = book).orderCoin?.toOrder() ?: OrderListDTO()

    override fun getAvailableRx(): Single<List<CryptoBookDTO>> =
        api.getExchangeBooksRx().map {
            it.toListWCCryptoBookDTO()
        }
}

fun CryptoAvailableResponse.toListWCCryptoBookDTO() =
    this.coins?.map {
        it.toBook()
    }
