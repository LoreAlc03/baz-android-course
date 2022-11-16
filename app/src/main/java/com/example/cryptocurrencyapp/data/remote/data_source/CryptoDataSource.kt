package com.example.cryptocurrencyapp.data.remote.data_source

import com.example.cryptocurrencyapp.data.remote.api.CryptoApi
import com.example.cryptocurrencyapp.data.remote.entity.response.WCCryptoAvailableResponse
import com.example.cryptocurrencyapp.data.remote.entity.toBitsoTicker
import com.example.cryptocurrencyapp.domain.entity.WCCOrdeRDTO
import com.example.cryptocurrencyapp.domain.entity.WCCTickerDTO
import com.example.cryptocurrencyapp.domain.entity.WCCryptoBookDTO
import com.example.cryptocurrencyapp.domain.repository.WCCryptoRepository
import io.reactivex.Single
import javax.inject.Inject

class WCCryptoRepositoryImp @Inject constructor(private val api: CryptoApi) : WCCryptoRepository {

    override suspend fun getTickerBook(book: String): WCCTickerDTO =
        api.getTickerBook(book = book).tickerCoin?.toBitsoTicker() ?: WCCTickerDTO()

    override suspend fun getOrderBook(book: String): WCCOrdeRDTO =
        api.getOrderBook(book = book).orderCoin?.toOrder() ?: WCCOrdeRDTO()

    override fun getAvailableRx(): Single<List<WCCryptoBookDTO>> =
        api.getExchangeBooksRx().map {
            it.toListWCCryptoBookDTO()
        }
}

fun WCCryptoAvailableResponse.toListWCCryptoBookDTO() =
    this.coins?.map {
        it.toBook()
    }
