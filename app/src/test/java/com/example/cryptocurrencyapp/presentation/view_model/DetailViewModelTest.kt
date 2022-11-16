package com.example.cryptocurrencyapp.presentation.view_model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cryptocurrencyapp.domain.entity.WCCOrdeRDTO
import com.example.cryptocurrencyapp.domain.entity.WCCTickerDTO
import com.example.cryptocurrencyapp.domain.use_case.DetailUseCase
import com.example.cryptocurrencyapp.utils.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailViewModelTest {
    private var getDetailUseCase: DetailUseCase = mockk()
    private lateinit var detailCryptoViewModel: DetailViewModel

    @get:Rule
    val rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        detailCryptoViewModel = DetailViewModel(getDetailUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun default_format_value_order() = runTest {
        // Given
        val idBookMock = "btc_mxn"
        val mockData = WCCOrdeRDTO()
        coEvery { getDetailUseCase.order(idBookMock) } returns flow { emit(Resource.Success(mockData)) }

        // When
        detailCryptoViewModel.getOrderBook(idBookMock)

        // Then
        assert(detailCryptoViewModel.state.value.dataOrder == mockData)
    }

    @Test
    fun default_format_value_ticker() = runTest {
        // Given
        val idBookMock = "btc_mx"
        val mockData = WCCTickerDTO()
        coEvery { getDetailUseCase.ticker(idBookMock) } returns flow { emit(Resource.Success(mockData)) }

        // When
        detailCryptoViewModel.getTicker(idBookMock)

        // Then
        assert(detailCryptoViewModel.state.value.dataTicker == mockData)
    }

    @Test
    fun value_getDetailTicker() = runTest {
        // Given
        val idBookMock = "btc_mxn"
        val mockData = WCCTickerDTO(low = "1", high = "34")
        coEvery { getDetailUseCase.ticker(idBookMock) } returns flow { emit(Resource.Success(mockData)) }

        // When
        detailCryptoViewModel.getTicker(idBookMock)

        // Then
        assert(detailCryptoViewModel.state.value.dataTicker == mockData)
    }
}
