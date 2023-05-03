package me.darthwithap.android.stockmarketapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.darthwithap.android.stockmarketapp.data.local.StockDatabase
import me.darthwithap.android.stockmarketapp.data.mapper.toCompanyListing
import me.darthwithap.android.stockmarketapp.data.remote.StockApi
import me.darthwithap.android.stockmarketapp.domain.model.CompanyListing
import me.darthwithap.android.stockmarketapp.domain.repository.StockRepository
import me.darthwithap.android.stockmarketapp.util.Result
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
  val api: StockApi,
  val db: StockDatabase
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
      emit(Result.Success(
        data = localListings.map { it.toCompanyListing() }
      ))

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
      } catch (e: IOException) {
        e.printStackTrace()
        emit(Result.Error("Couldn't load data"))
      } catch (e: HttpException) {
        emit(Result.Error("Couldn't load data"))
      }
    }
  }
}