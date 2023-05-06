package me.darthwithap.android.stockmarketapp.presentation.company_info

import me.darthwithap.android.stockmarketapp.domain.model.CompanyInfo
import me.darthwithap.android.stockmarketapp.domain.model.IntradayInfo

data class CompanyInfoState(
  val stockInfoList: List<IntradayInfo> = emptyList(),
  val company: CompanyInfo? = null,
  val isLoading: Boolean = false,
  val errorMsg: String? = null
)