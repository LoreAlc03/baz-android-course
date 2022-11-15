package com.example.cryptocurrencyapp.domain.use_case

import android.util.Log
import com.example.cryptocurrencyapp.data.remote.entity.response.WCCryptoAvailableResponse
import com.example.cryptocurrencyapp.data.repository.CryptoRespository
import com.example.cryptocurrencyapp.domain.entity.WCCryptoBookDTO
import com.example.cryptocurrencyapp.utils.CryptoConstants
import com.example.cryptocurrencyapp.utils.Resource
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WCCAvailableUseCase @Inject constructor(private val repository: CryptoRespository) {
    suspend fun coin(): Flow<Resource<List<WCCryptoBookDTO>>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.getAvailableBooks()
            emit(Resource.Success(response))
        } catch (e: Exception) {
            Log.e("Execute Exception", "WCCAvailableUseCase Exception ${e.message} / ${e::class.java}")
            e.printStackTrace()
            emit(Resource.Error(e.localizedMessage ?: CryptoConstants.ERROR))
        }
    }
}

