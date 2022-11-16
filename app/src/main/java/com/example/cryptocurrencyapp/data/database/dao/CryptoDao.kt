package com.example.cryptocurrencyapp.data.database.dao

import androidx.room.*
import com.example.cryptocurrencyapp.data.database.entities.*
import com.example.cryptocurrencyapp.domain.entity.OrderListDTO

import io.reactivex.Single

@Dao
interface CryptoDao {

    @Query("SELECT * FROM available_table")
    fun getAllAvailableRXDB(): Single<List<AvailableBookEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAvailableRXDB(book: List<AvailableBookEntity>)

    @Update
    suspend fun updateAvailableBookDB(bookList: List<AvailableBookEntity>)

    // TICKER
    @Query("SELECT * FROM ticker_table WHERE book LIKE :book")
    suspend fun getTickerBD(book: String): TickerEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTickerBD(ticker: TickerEntity)

    // BID
    @Query("SELECT * FROM bid_table WHERE book LIKE :book")
    suspend fun getBidBookDB(book: String): List<BidEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBidDB(bid: List<BidEntity>)

    @Query("DELETE FROM bid_table WHERE book LIKE :book")
    fun deleteBidsListDB(book: String)

    // ASK
    @Query("SELECT * FROM ask_table WHERE book LIKE :book")
    suspend fun getAskBookDB(book: String): List<AskEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAskDB(ask: List<AskEntity>)

    @Query("DELETE FROM ask_table WHERE book LIKE :book")
    fun deleteAsksListDB(book: String)

    @Transaction
    suspend fun getOrderBookDB(book: String): OrderListDTO {
        val ask = getAskBookDB(book)
        val bid = getBidBookDB(book)

        return OrderListDTO(
            ask = ask.map { it.toWCCOrderBookDTO() },
            bids = bid.map { it.toWCCOrderBookDTO() }
        )
    }

    @Transaction
    suspend fun insertOrderBookFromDatabase(askList: List<AskEntity>, bidList: List<BidEntity>) {
        insertAskDB(askList)
        insertBidDB(bidList)
    }

    @Transaction
    suspend fun deleteOrderBookDB(book: String) {
        deleteBidsListDB(book)
        deleteAsksListDB(book)
    }
}
