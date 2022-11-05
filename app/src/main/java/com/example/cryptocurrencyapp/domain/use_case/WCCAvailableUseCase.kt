package com.example.cryptocurrencyapp.domain.use_case

import android.util.Log
import com.example.cryptocurrencyapp.data.remote.data_source.WCCryptoRepositoryImp
import com.example.cryptocurrencyapp.data.repository.CryptoRespository
import com.example.cryptocurrencyapp.domain.entity.WCCryptoBookDTO
import com.example.cryptocurrencyapp.utils.CryptoConstants
import com.example.cryptocurrencyapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class WCCAvailableUseCase @Inject constructor(private val repository: CryptoRespository) {
    suspend fun coin(): Flow<Resource<List<WCCryptoBookDTO>>> = channelFlow {
        try {
            send(Resource.Loading())
            val response = repository.getAvailableBooks()
            send(Resource.Success(response))
        }catch (e: Exception){
            Log.e("Alfredo", "WCCAvailableUseCase Exception ${e.message} / ${e::class.java}")
            e.printStackTrace()
            send(Resource.Error(e.localizedMessage ?: CryptoConstants.ERROR))
        }
    }
}