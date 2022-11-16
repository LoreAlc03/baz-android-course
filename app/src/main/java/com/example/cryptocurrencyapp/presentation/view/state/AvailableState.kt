package com.example.cryptocurrencyapp.presentation.view.state

import com.example.cryptocurrencyapp.domain.entity.WCCryptoBookDTO

data class AvailableState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val dataAvailable: List<WCCryptoBookDTO> = emptyList()
)
