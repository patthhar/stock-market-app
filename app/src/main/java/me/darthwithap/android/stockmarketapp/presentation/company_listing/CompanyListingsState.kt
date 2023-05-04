package me.darthwithap.android.stockmarketapp.presentation.company_listing

import me.darthwithap.android.stockmarketapp.domain.model.CompanyListing

data class CompanyListingsState(
  val companies: List<CompanyListing> = emptyList(),
  val isLoading: Boolean = false,
  val isRefreshing: Boolean = false,
  val query: String = ""
)
