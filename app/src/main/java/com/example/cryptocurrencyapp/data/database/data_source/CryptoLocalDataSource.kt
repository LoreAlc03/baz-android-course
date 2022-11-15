package com.example.cryptocurrencyapp.data.database.data_source

import com.example.cryptocurrencyapp.data.database.dao.CryptoDao
import com.example.cryptocurrencyapp.data.database.entities.AskEntity
import com.example.cryptocurrencyapp.data.database.entities.AvailableBookEntity
import com.example.cryptocurrencyapp.data.database.entities.BidEntity
import com.example.cryptocurrencyapp.data.database.entities.TickerEntity
import io.reactivex.Single
import javax.inject.Inject

class CryptoLocalDataSource @Inject constructor(
    private val cryptoDB: CryptoDao

) {

    // Available
    /*suspend fun getAllAvailableFromDB():  List<AvailableBookEntity> =
        cryptoDB.getAllAvailableBookDB()

    suspend fun insertAvailableBookToDB(bookList: List<AvailableBookEntity>) =
        cryptoDB.insertAvailableBooDB(bookList)*/

 fun getAllAvailableRxFromDB(): Single<List<AvailableBookEntity>> =
        cryptoDB.getAllAvailableRXDB()

    fun insertAvailableRxBookToDB(bookList: List<AvailableBookEntity>) =
        cryptoDB.insertAvailableRXDB(bookList)

    // Ticker
    suspend fun getTickerFromDB(book: String): TickerEntity =
        cryptoDB.getickerBD(book)

    suspend fun insertTickerToDB(tickerEntity: TickerEntity) =
        cryptoDB.insertTickerBD(tickerEntity)

    // Order
    suspend fun getOrderBookFromDB(book: String) =
        cryptoDB.getOrderBookDB(book)

    suspend fun insertOrderBookDB(askList: List<AskEntity>, bidList: List<BidEntity>) =
        cryptoDB.insertOrderBookFromDatabase(askList, bidList)

    suspend fun deleteOrderBook(book: String) =
        cryptoDB.deleteOrderBookDB(book)
}
