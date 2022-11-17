package com.example.cryptocurrencyapp.presentation.view.state

import com.example.cryptocurrencyapp.domain.entity.CryptoBookDTO

data class AvailableState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val dataAvailable: List<CryptoBookDTO> = emptyList()
)
