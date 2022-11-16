package com.example.cryptocurrencyapp.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.example.cryptocurrencyapp.data.database.data_source.CryptoLocalDataSource
import com.example.cryptocurrencyapp.data.database.entities.*
import com.example.cryptocurrencyapp.data.remote.data_source.CryptoDataSource
import com.example.cryptocurrencyapp.domain.entity.OrderListDTO
import com.example.cryptocurrencyapp.domain.entity.TickerDTO
import com.example.cryptocurrencyapp.domain.entity.CryptoBookDTO
import com.example.cryptocurrencyapp.domain.entity.toTickerEntity
import com.example.cryptocurrencyapp.domain.repository.CryptoRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.Single
import javax.inject.Inject

class CryptoRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val localDataSource: CryptoLocalDataSource,
    private val remoteDataSource: CryptoDataSource,
) : CryptoRepository {
    override suspend fun getTickerBook(book: String): TickerDTO {
        return if (isInternet(context)) {
            val ticker: TickerDTO = remoteDataSource.getTickerBook(book)

            if (ticker.book != "") {
                localDataSource.insertTickerToDB(ticker.toTickerEntity())
                ticker.toTickerEntity().toWCCTickerDTO()
            } else
                localDataSource.getTickerFromDB(book).toWCCTickerDTO()
        } else
            try {
                localDataSource.getTickerFromDB(book).toWCCTickerDTO()
            } catch (e: Exception) {
                e.printStackTrace()
                return TickerDTO()
            }
    }

    override suspend fun getOrderBook(book: String): OrderListDTO {
        if (isInternet(context)) {
            val order = remoteDataSource.getOrderBook(book)
            if (order.ask.isNotEmpty() && order.bids.isNotEmpty()) {
                localDataSource.deleteOrderBook(book)
                localDataSource.insertOrderBookDB(
                    order.ask.toAskEntityList(),
                    order.bids.toBidsEntityList()
                )
            }
            return order
        } else {
            return try {
                localDataSource.getOrderBookFromDB(book)
            } catch (e: Exception) {
                e.printStackTrace()
                OrderListDTO()
            }
        }
    }

    override fun getAvailableRx(): Single<List<CryptoBookDTO>> {
        if (isInternet(context)) {
            return remoteDataSource.getAvailableRx().map { coins ->
                val cryptoList = coins.toMutableList()
                if (coins.isNotEmpty()) {
                    localDataSource.insertAvailableRxBookToDB(coins.toAvailableEntity())
                } else {
                    cryptoList.addAll(
                        localDataSource.getAllAvailableRxFromDB().blockingGet().map {
                            it.toWCCryptoBookDTO()
                        }
                    )
                }
                cryptoList
            }
        } else {
            return localDataSource.getAllAvailableRxFromDB().map { books ->
                books.map {
                    it.toWCCryptoBookDTO()
                }
            }
        }
    }
}

fun isInternet(context: Context): Boolean {
    var result = false
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val network =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            network.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            network.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            network.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    } else {
        connectivityManager.run {
            connectivityManager.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
    }
    return result
}
