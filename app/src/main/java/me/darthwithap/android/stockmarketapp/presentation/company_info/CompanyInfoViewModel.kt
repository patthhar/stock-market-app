package me.darthwithap.android.stockmarketapp.presentation.company_info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import me.darthwithap.android.stockmarketapp.domain.repository.StockRepository
import me.darthwithap.android.stockmarketapp.util.Result
import javax.inject.Inject

@HiltViewModel
class CompanyInfoViewModel @Inject constructor(
  private val savedStateHandle: SavedStateHandle,
  private val repository: StockRepository
) : ViewModel() {

  var state by mutableStateOf(CompanyInfoState())

  init {
    viewModelScope.launch {
      val symbol = savedStateHandle.get<String>("symbol") ?: return@launch
      state = state.copy(isLoading = true)
      val companyInfoResult = async { repository.getCompanyInfo(symbol) }
      val intradayInfoResult = async { repository.getIntradayInfo(symbol) }

      when (val result = companyInfoResult.await()) {
        is Result.Success -> {
          state = state.copy(company = result.data, isLoading = false, errorMsg = null)
        }
        is Result.Error -> {
          state = state.copy(errorMsg = result.message, isLoading = false, company = null)
        }
        else -> Unit
      }
      when (val result = intradayInfoResult.await()) {
        is Result.Success -> {
          state =
            state.copy(
              stockInfoList = result.data ?: emptyList(),
              isLoading = false,
              errorMsg = null
            )
        }
        is Result.Error -> {
          state =
            state.copy(
              errorMsg = result.message,
              isLoading = false,
              company = null,
              stockInfoList = emptyList()
            )
        }
        else -> Unit
      }
    }
  }
}