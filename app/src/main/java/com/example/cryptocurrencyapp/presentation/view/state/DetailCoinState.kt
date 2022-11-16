package com.example.cryptocurrencyapp.presentation.view.state

import com.example.cryptocurrencyapp.domain.entity.WCCOrdeRDTO
import com.example.cryptocurrencyapp.domain.entity.WCCTickerDTO

data class DetailCoinState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val dataTicker: WCCTickerDTO? = null,
    val dataOrder: WCCOrdeRDTO? = null,
)
