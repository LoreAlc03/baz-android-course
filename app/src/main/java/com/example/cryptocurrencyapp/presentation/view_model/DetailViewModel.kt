package com.example.cryptocurrencyapp.presentation.view_model

import androidx.lifecycle.*
import com.example.cryptocurrencyapp.domain.entity.OrderListDTO
import com.example.cryptocurrencyapp.domain.entity.TickerDTO
import com.example.cryptocurrencyapp.domain.use_case.DetailUseCase
import com.example.cryptocurrencyapp.presentation.view.state.DetailCoinState
import com.example.cryptocurrencyapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val detailUseCase: DetailUseCase) : ViewModel() {

    private val _state = MutableStateFlow(DetailCoinState(isLoading = true))
    val state: StateFlow<DetailCoinState> = _state

    fun getTicker(book: String) {
        viewModelScope.launch {
            val response = detailUseCase.ticker(book)
            response.onEach { ticker ->
                when (ticker) {
                    is Resource.Loading -> {
                        _state.update {
                            it.copy(isLoading = true)
                        }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                dataTicker = ticker.data ?: TickerDTO()
                            )
                        }
                    }
                    is Resource.Error ->
                        _state.update {
                            it.copy(isLoading = false, errorMessage = ticker.uiText.toString())
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
                        _state.update {
                            it.copy(isLoading = true)
                        }
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                dataOrder = order.data ?: OrderListDTO()
                            )
                        }
                    }
                    is Resource.Error ->
                        _state.update {
                            it.copy(isLoading = false, errorMessage = order.uiText.toString())
                        }
                }
            }.launchIn(viewModelScope)
        }
    }
}
