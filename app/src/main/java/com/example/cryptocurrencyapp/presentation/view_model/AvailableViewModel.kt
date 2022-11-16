package com.example.cryptocurrencyapp.presentation.view_model

import androidx.lifecycle.*
import com.example.cryptocurrencyapp.domain.entity.toFilterWCCryptoBookDTO
import com.example.cryptocurrencyapp.domain.use_case.GetAvailableRxUseCase
import com.example.cryptocurrencyapp.presentation.view.state.AvailableState
import com.example.cryptocurrencyapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AvailableViewModel @Inject constructor(private val availableUseCase: GetAvailableRxUseCase) :
    ViewModel() {

    private val _stateAvailable = MutableStateFlow(AvailableState(isLoading = true))
    val state: StateFlow<AvailableState> = _stateAvailable

    fun getAvailableBook() {
        viewModelScope.launch {
            availableUseCase.coin().subscribeOn(
                Schedulers.io()
            ).observeOn(
                AndroidSchedulers.mainThread()
            ).subscribe({ coins ->
                when (coins) {
                    is Resource.Loading -> {
                        _stateAvailable.update {
                            it.copy(isLoading = true, errorMessage = null)
                        }
                    }
                    is Resource.Success -> {
                        _stateAvailable.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = null,
                                dataAvailable = coins.data?.toFilterWCCryptoBookDTO() ?: emptyList()
                            )
                        }
                    }
                    is Resource.Error ->
                        _stateAvailable.update {
                            it.copy(isLoading = false, errorMessage = coins.uiText)
                        }
                }
            }, {
                _stateAvailable.update {
                    it.copy(isLoading = false, errorMessage = it.errorMessage)
                }
            })
        }
    }
}
