package com.example.cryptocurrencyapp.presentation.view_model

import android.util.Log
import androidx.lifecycle.*
import com.example.cryptocurrencyapp.domain.entity.WCCOrdeRDTO
import com.example.cryptocurrencyapp.domain.entity.WCCTickerDTO
import com.example.cryptocurrencyapp.domain.use_case.DetailUseCase
import com.example.cryptocurrencyapp.presentation.view.state.DetailCoinState
import com.example.cryptocurrencyapp.utils.Resource
import com.example.cryptocurrencyapp.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val detailUseCase: DetailUseCase) : ViewModel() {

    private val _tickerBook = MutableLiveData<WCCTickerDTO>()
    val resumeTicker: LiveData<WCCTickerDTO> get() = _tickerBook

    val state = MutableStateFlow(DetailCoinState(isLoading = true))
    //val isLoading : State<DetailCoinState> = _isLoading


    private val _orderBok = MutableLiveData<WCCOrdeRDTO>()
    val resumeOrder: LiveData<WCCOrdeRDTO> get() = _orderBok

    fun getTicker(book: String) {
        viewModelScope.launch {
            val response = detailUseCase.ticker(book)
            response.onEach { ticker ->
                when (ticker) {
                    is Resource.Loading -> {
                        state.update {
                            it.copy(isLoading = true, errorMessage = null)
                        }
                    }

                    is Resource.Success -> {
                        //_tickerBook.value = ticker.data ?: WCCTickerDTO()
                        state.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = null,
                                dataTicker = ticker.data ?: WCCTickerDTO()
                            )
                        }
                        Log.i("datos", "$_tickerBook")
                    }
                    is Resource.Error ->
                        state.update {
                            it.copy(isLoading = false, errorMessage = ticker.uiText)
                        }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun getOrderBook(book: String) {
        viewModelScope.launch {
            val response = detailUseCase.order(book)
            response.onEach { order ->
                when (order) {
                    is Resource.Loading ->
                        state.update {
                            it.copy(isLoading = true, errorMessage = null)
                        }
                    is Resource.Success -> {
                        // _orderBok.value = order.data ?: WCCOrdeRDTO()
                        state.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = null,
                                dataOrder = order.data ?: WCCOrdeRDTO()
                            )
                        }
                        Log.i("data", "$_orderBok")
                    }
                    is Resource.Error ->
                        state.update {
                            it.copy(isLoading = false, errorMessage = order.uiText)
                        }
                }
            }.launchIn(viewModelScope)
        }
    }
}
