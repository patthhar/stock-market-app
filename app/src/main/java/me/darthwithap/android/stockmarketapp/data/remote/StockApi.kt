package me.darthwithap.android.stockmarketapp.data.remote

import me.darthwithap.android.stockmarketapp.data.remote.dto.CompanyInfoDto
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {

  @GET("query?function=LISTING_STATUS")
  suspend fun getListings(
    @Query("apikey") apiKey: String = API_KEY
  ): ResponseBody

  @GET("query?function=TIME_SERIES_INTRADAY&interval=60min&datatype=csv")
  suspend fun getIntradayInfo(
    @Query("apikey") apiKey: String = API_KEY,
    @Query("symbol") symbol: String
  ): ResponseBody

  @GET("query?function=OVERVIEW")
  suspend fun getCompanyInfo(
    @Query("apikey") apiKey: String = API_KEY,
    @Query("symbol") symbol: String
  ): CompanyInfoDto

  companion object {
    const val API_KEY = "OB808E09TKSABKTS"
    const val BASE_URL = "https://alphavantage.co"
  }
}