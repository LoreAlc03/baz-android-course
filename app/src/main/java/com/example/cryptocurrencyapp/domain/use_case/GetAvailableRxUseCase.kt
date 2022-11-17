package com.example.cryptocurrencyapp.domain.use_case

import android.util.Log
import com.example.cryptocurrencyapp.data.repository.CryptoRepository
import com.example.cryptocurrencyapp.domain.entity.CryptoBookDTO
import com.example.cryptocurrencyapp.utils.CryptoConstants
import com.example.cryptocurrencyapp.utils.Resource
import io.reactivex.Observable
import javax.inject.Inject

class GetAvailableRxUseCase @Inject constructor(private val repository: CryptoRepository) {
    fun coin(): Observable<Resource<List<CryptoBookDTO>>> {
        return Observable.create {
            try {
                it.onNext(Resource.Loading())
                val response = repository.getAvailableRx().blockingGet()
                it.onNext(Resource.Success(response))
            } catch (e: Exception) {
                Log.e("Execute Exception", "WCCAvailableUseCase Exception ${e.message} / ${e::class.java}")
                e.printStackTrace()
                it.onNext(Resource.Error(e.localizedMessage ?: CryptoConstants.ERROR))
            }
        }
    }
}
