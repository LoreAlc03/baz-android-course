package com.example.cryptocurrencyapp.domain.entity

data class OrderListDTO(
    val ask: List<OrderBookDTO> = mutableListOf(),
    val bids: List<OrderBookDTO> = mutableListOf(),
)
