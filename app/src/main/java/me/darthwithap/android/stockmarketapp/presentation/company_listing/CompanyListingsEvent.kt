package me.darthwithap.android.stockmarketapp.presentation.company_listing

sealed class CompanyListingsEvent {
  object Refresh : CompanyListingsEvent()
  data class onSearchQueryChange(val query: String) : CompanyListingsEvent()
}