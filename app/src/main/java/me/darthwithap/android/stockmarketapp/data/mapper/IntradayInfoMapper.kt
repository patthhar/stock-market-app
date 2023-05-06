package me.darthwithap.android.stockmarketapp.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import me.darthwithap.android.stockmarketapp.data.remote.dto.IntradayInfoDto
import me.darthwithap.android.stockmarketapp.domain.model.IntradayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
fun IntradayInfoDto.toIntradayInfo(): IntradayInfo {
  val pattern = "yyyy-MM-dd HH:mm:ss"
  val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
  val localDateTime = LocalDateTime.parse(timestamp, formatter)
  return IntradayInfo(localDateTime, close)
}