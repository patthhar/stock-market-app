package me.darthwithap.android.stockmarketapp.data.csv

import android.os.Build
import androidx.annotation.RequiresApi
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.darthwithap.android.stockmarketapp.data.mapper.toIntradayInfo
import me.darthwithap.android.stockmarketapp.data.remote.dto.IntradayInfoDto
import me.darthwithap.android.stockmarketapp.domain.model.IntradayInfo
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntradayInfoParser @Inject constructor() : CsvParser<IntradayInfo> {

  @RequiresApi(Build.VERSION_CODES.O)
  override suspend fun parse(stream: InputStream): List<IntradayInfo> {
    val csvReader = CSVReader(InputStreamReader(stream))

    return withContext(Dispatchers.IO) {

      csvReader.readAll().drop(1).mapNotNull { element ->
        val timestamp = element.getOrNull(0) ?: return@mapNotNull null
        val close = element.getOrNull(4) ?: return@mapNotNull null

        IntradayInfoDto(timestamp, close.toDouble()).toIntradayInfo()
      }.also { csvReader.close() }
    }
  }
}