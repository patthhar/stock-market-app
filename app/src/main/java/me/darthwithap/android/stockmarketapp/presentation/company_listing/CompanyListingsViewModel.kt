package me.darthwithap.android.stockmarketapp.presentation.company_listing

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.darthwithap.android.stockmarketapp.domain.repository.StockRepository
import me.darthwithap.android.stockmarketapp.util.Result
import javax.inject.Inject

@HiltViewModel
class CompanyListingsViewModel @Inject constructor(
  private val repository: StockRepository
) : ViewModel() {

  private var state by mutableStateOf(CompanyListingsState())
  private var searchJob: Job? = null

  fun onEvent(event: CompanyListingsEvent) {
    when (event) {
      is CompanyListingsEvent.Refresh -> {
        // Force fetching from remote true on swipe to refresh event
        getCompanyListings(fetchFromRemote = true)
      }
      is CompanyListingsEvent.onSearchQueryChange -> {
        state = state.copy(query = event.query)
        searchJob?.cancel()
        // Start searchJob when the delay between character change in query is greater than 0.5s
        searchJob = viewModelScope.launch {
          delay(500L)
          // Search doesn't begin until we don't type anything for 500ms
          getCompanyListings()
        }
      }
    }
  }

  private fun getCompanyListings(
    query: String = state.query.lowercase(),
    fetchFromRemote: Boolean = false
  ) {
    viewModelScope.launch {
      repository.getCompanyListings(fetchFromRemote, query)
        .collect { result ->
          when (result) {
            is Result.Success -> {
              result.data?.let { listings ->
                state = state.copy(companies = listings)
              }
            }
            is Result.Error -> {
              // TODO: Error Handling
            }
            is Result.Loading -> {
              state = state.copy(isLoading = result.isLoading)
            }
          }
        }
    }
  }
}