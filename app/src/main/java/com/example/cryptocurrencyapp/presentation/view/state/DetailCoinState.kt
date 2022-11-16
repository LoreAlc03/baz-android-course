package com.example.cryptocurrencyapp.presentation.view.state


import com.example.cryptocurrencyapp.domain.entity.OrderListDTO
import com.example.cryptocurrencyapp.domain.entity.TickerDTO

data class DetailCoinState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val dataTicker: TickerDTO = TickerDTO(),
    val dataOrder: OrderListDTO = OrderListDTO(),
)
