package com.example.cryptocurrencyapp.presentation.view_model

import androidx.lifecycle.*
import com.example.cryptocurrencyapp.domain.entity.WCCryptoBookDTO
import com.example.cryptocurrencyapp.domain.entity.toFilterWCCryptoBookDTO
import com.example.cryptocurrencyapp.domain.use_case.WCCAvailableUseCase
import com.example.cryptocurrencyapp.utils.CryptoConstants
import com.example.cryptocurrencyapp.utils.Resource
import com.example.cryptocurrencyapp.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AvailableViewModel @Inject constructor(private val availableUseCase: WCCAvailableUseCase) :
    ViewModel() {

    private val _cryptoBook = MutableLiveData<List<WCCryptoBookDTO>>()
    val coins: LiveData<List<WCCryptoBookDTO>> get() = _cryptoBook

    private var _isLoading = MutableLiveData<Boolean>(true)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private var _isError = MutableLiveData<Boolean>(true)
    val isError: LiveData<Boolean> get() = _isError

    /*private var _isError = MutableLiveData<String>
    val isError: LiveData<Boolean> get() = _isError*/

    init {
        getAvailableBook()
    }

    fun getAvailableBook() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val response = availableUseCase.coin()
                response.onEach { coins ->
                    when (coins) {
                        is Resource.Loading -> {
                            _isLoading.value = true
                        }
                        is Resource.Success -> {
                            _isLoading.value = false
                            _cryptoBook.value = coins.data?.toFilterWCCryptoBookDTO() ?: coins.data

                        }
                        is Resource.Error ->
                            Utils.showDialog()
                    }
                }.launchIn(viewModelScope)
            }
        }
    }
}
