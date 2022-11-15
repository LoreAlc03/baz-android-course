package com.example.cryptocurrencyapp.presentation.view_model

import androidx.lifecycle.*
import com.example.cryptocurrencyapp.domain.entity.WCCryptoBookDTO
import com.example.cryptocurrencyapp.domain.entity.toFilterWCCryptoBookDTO
import com.example.cryptocurrencyapp.domain.use_case.GetAvailableRxUseCase
import com.example.cryptocurrencyapp.domain.use_case.WCCAvailableUseCase
import com.example.cryptocurrencyapp.presentation.view.state.DetailCoinState
import com.example.cryptocurrencyapp.utils.CryptoConstants
import com.example.cryptocurrencyapp.utils.Resource
import com.example.cryptocurrencyapp.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AvailableViewModel @Inject constructor(private val availableUseCase: GetAvailableRxUseCase) :
    ViewModel() {

    private val _cryptoBook = MutableLiveData<List<WCCryptoBookDTO>>()
    val coins: LiveData<List<WCCryptoBookDTO>> get() = _cryptoBook

    val state = MutableStateFlow(DetailCoinState(isLoading = true))

    private var _isLoading = MutableLiveData<Boolean>(true)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private var _isError = MutableLiveData<Boolean>(true)
    val isError: LiveData<Boolean> get() = _isError

    val disposable = CompositeDisposable()
    /*private var _isError = MutableLiveData<String>
    val isError: LiveData<Boolean> get() = _isError*/

    init {
        getAvailableBook()
    }

    fun getAvailableBook() {
        availableUseCase.coin().subscribeOn(
            Schedulers.io()
        ).observeOn(
            AndroidSchedulers.mainThread()
        ).subscribe({ coins ->
            when (coins) {
                is Resource.Loading -> {
                    state.update {
                        it.copy(isLoading = true, errorMessage = null)
                    }
                    //_isLoading.value = true
                }
                is Resource.Success -> {
                    _isLoading.value = false
                    _cryptoBook.value = coins.data?.toFilterWCCryptoBookDTO() ?: coins.data

                }
                is Resource.Error ->
                    state.update {
                        it.copy(isLoading = false, errorMessage = coins.uiText)
                    }
                //Utils.showDialog()
            }
        },{
            state.update {
                it.copy(isLoading = false, errorMessage = it.errorMessage)
            }
        }).addTo(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
