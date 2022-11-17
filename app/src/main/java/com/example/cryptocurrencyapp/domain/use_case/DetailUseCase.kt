package com.example.cryptocurrencyapp.domain.use_case

import com.example.cryptocurrencyapp.data.repository.CryptoRepository
import com.example.cryptocurrencyapp.domain.entity.OrderListDTO
import com.example.cryptocurrencyapp.domain.entity.TickerDTO
import com.example.cryptocurrencyapp.utils.CryptoConstants
import com.example.cryptocurrencyapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetailUseCase @Inject constructor(private val repository: CryptoRepository) {
    suspend fun ticker(book: String): Flow<Resource<TickerDTO>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.getTickerBook(book)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: CryptoConstants.ERROR))
        }
    }
    suspend fun order(book: String): Flow<Resource<OrderListDTO>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.getOrderBook(book)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: CryptoConstants.ERROR))
        }
    }
}
