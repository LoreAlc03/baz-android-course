package com.example.cryptocurrencyapp.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cryptocurrencyapp.domain.entity.OrderBookDTO

@Entity(tableName = "bid_table")
data class BidEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val book: String,
    val price: String = "",
    val amount: String = "",
    val type: String = ""
)

fun BidEntity.toWCCOrderBookDTO() =
    OrderBookDTO(
        book = this.book,
        price = this.price,
        amount = this.amount,
        type = this.type
    )

fun List<OrderBookDTO>?.toBidsEntityList() = mutableListOf<BidEntity>().apply {
    this@toBidsEntityList?.forEach {
        this.add(
            BidEntity(book = it.book, price = it.price, amount = it.amount)
        )
    }
}
