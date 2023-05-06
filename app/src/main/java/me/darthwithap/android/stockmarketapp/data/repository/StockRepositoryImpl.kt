package me.darthwithap.android.stockmarketapp.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.darthwithap.android.stockmarketapp.data.csv.CsvParser
import me.darthwithap.android.stockmarketapp.data.local.StockDatabase
import me.darthwithap.android.stockmarketapp.data.mapper.toCompanyInfo
import me.darthwithap.android.stockmarketapp.data.mapper.toCompanyListing
import me.darthwithap.android.stockmarketapp.data.mapper.toEntity
import me.darthwithap.android.stockmarketapp.data.remote.StockApi
import me.darthwithap.android.stockmarketapp.domain.model.CompanyInfo
import me.darthwithap.android.stockmarketapp.domain.model.CompanyListing
import me.darthwithap.android.stockmarketapp.domain.model.IntradayInfo
import me.darthwithap.android.stockmarketapp.domain.repository.StockRepository
import me.darthwithap.android.stockmarketapp.util.Result
import retrofit2.HttpException
import java.io.IOException
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
  val api: StockApi,
  val db: StockDatabase,
  val companyListingParser: CsvParser<CompanyListing>,
  val intradayParser: CsvParser<IntradayInfo>
) : StockRepository {

  private val stockDao = db.dao

  override suspend fun getCompanyListings(
    fetchFromRemote: Boolean,
    query: String
  ): Flow<Result<List<CompanyListing>>> {
    return flow {

      // Set loading = true
      emit(Result.Loading(true))

      // Always load from cache first
      val localListings = stockDao.searchCompanyListing(query)
      emit(Result.Success(localListings.map { it.toCompanyListing() }))

      //check if we only have to fetch from db
      val isDbEmpty = localListings.isEmpty() && query.isBlank()
      val onlyFetchFromCache = !isDbEmpty && !fetchFromRemote

      if (onlyFetchFromCache) {
        // done with fetching from repositories so return
        emit(Result.Loading(false))
        return@flow
      }

      // If not onlyFromCache, fetchFromApi
      val remoteListings = try {
        val apiResponse = api.getListings()
        companyListingParser.parse(apiResponse.byteStream())
      } catch (e: IOException) {
        e.printStackTrace()
        emit(Result.Error("Couldn't load data"))
        null
      } catch (e: HttpException) {
        emit(Result.Error("Couldn't load data"))
        null
      }

      remoteListings?.let { listings ->
        //Clear Cache before inserting newly fetched data
        stockDao.clearCompanyListings()
        stockDao.insertCompanyListings(
          listings.map { it.toEntity() })

        //Single source of truth for our UI, always emit data from the cache
        emit(Result.Success(
          data = stockDao.searchCompanyListing("")
            .map { it.toCompanyListing() }
        ))
        emit(Result.Loading(false))
      }
    }
  }

  @RequiresApi(Build.VERSION_CODES.O)
  override suspend fun getIntradayInfo(symbol: String): Result<List<IntradayInfo>> {
    return try {
      val apiResponse = api.getIntradayInfo(symbol = symbol)
      val intradayInfo = intradayParser.parse(apiResponse.byteStream())
        .filter {
          it.date.dayOfMonth == LocalDateTime.now().minusDays(1).dayOfMonth
        }
        .sortedBy { it.date.hour }
      Result.Success(intradayInfo)
    } catch (e: IOException) {
      e.printStackTrace()
      Result.Error("Couldn't load intraday info")
    } catch (e: HttpException) {
      e.printStackTrace()
      Result.Error("Couldn't load intraday info")
    }
  }

  override suspend fun getCompanyInfo(symbol: String): Result<CompanyInfo> {
    return try {
      val result = api.getCompanyInfo(symbol = symbol)
      Result.Success(result.toCompanyInfo())
    } catch (e: IOException) {
      e.printStackTrace()
      Result.Error("Couldn't load intraday info")
    } catch (e: HttpException) {
      e.printStackTrace()
      Result.Error("Couldn't load intraday info")
    }
  }
}