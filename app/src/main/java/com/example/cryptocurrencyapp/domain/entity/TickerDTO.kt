package com.example.cryptocurrencyapp.domain.entity

import com.example.cryptocurrencyapp.data.database.entities.TickerEntity

data class TickerDTO(
    val book: String = "",
    val high: String = "",
    val low: String = ""
)

fun TickerDTO.toTickerEntity() =
    TickerEntity(
        book = this.book,
        high = high,
        low = low
    )
