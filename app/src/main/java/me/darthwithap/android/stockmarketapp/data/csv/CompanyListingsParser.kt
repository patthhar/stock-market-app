package me.darthwithap.android.stockmarketapp.data.csv

import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.darthwithap.android.stockmarketapp.domain.model.CompanyListing
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompanyListingsParser @Inject constructor() : CsvParser<CompanyListing> {

  override suspend fun parse(stream: InputStream): List<CompanyListing> {
    val csvReader = CSVReader(InputStreamReader(stream))

    return withContext(Dispatchers.IO) {

      csvReader.readAll().drop(1).mapNotNull { element ->
        val symbol = element.getOrNull(0)
        val name = element.getOrNull(1)
        val exchange = element.getOrNull(2)

        CompanyListing(
          name = name ?: return@mapNotNull null,
          symbol = symbol ?: return@mapNotNull null,
          exchange = exchange ?: return@mapNotNull null
        )
      }.also { csvReader.close() }
    }
  }
}