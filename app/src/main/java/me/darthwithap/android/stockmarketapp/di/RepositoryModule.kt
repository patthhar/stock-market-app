package me.darthwithap.android.stockmarketapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.darthwithap.android.stockmarketapp.data.csv.CompanyListingsParser
import me.darthwithap.android.stockmarketapp.data.csv.CsvParser
import me.darthwithap.android.stockmarketapp.data.repository.StockRepositoryImpl
import me.darthwithap.android.stockmarketapp.domain.model.CompanyListing
import me.darthwithap.android.stockmarketapp.domain.repository.StockRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

  @Binds
  @Singleton
  abstract fun bindsCompanyListingsParser(
    companyListingsParser: CompanyListingsParser
  ): CsvParser<CompanyListing>

  @Binds
  @Singleton
  abstract fun bindsStockRepository(
    stockRepositoryImpl: StockRepositoryImpl
  ): StockRepository
}