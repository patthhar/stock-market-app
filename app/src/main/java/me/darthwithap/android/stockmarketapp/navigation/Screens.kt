package me.darthwithap.android.stockmarketapp.navigation

sealed class Screens(var route: String) {
  object CompanyListings : Screens("companyListings")
  object CompanyInfo : Screens("companyInfo")
}
