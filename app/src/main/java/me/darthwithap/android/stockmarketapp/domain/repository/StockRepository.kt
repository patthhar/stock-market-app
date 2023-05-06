package me.darthwithap.android.stockmarketapp.domain.repository

import kotlinx.coroutines.flow.Flow
import me.darthwithap.android.stockmarketapp.domain.model.CompanyInfo
import me.darthwithap.android.stockmarketapp.domain.model.CompanyListing
import me.darthwithap.android.stockmarketapp.domain.model.IntradayInfo
import me.darthwithap.android.stockmarketapp.util.Result

interface StockRepository {
  suspend fun getCompanyListings(
    fetchFromRemote: Boolean = false,
    query: String
  ): Flow<Result<List<CompanyListing>>>

  suspend fun getIntradayInfo(
    symbol: String
  ): Result<List<IntradayInfo>>

  suspend fun getCompanyInfo(
    symbol: String
  ): Result<CompanyInfo>
}