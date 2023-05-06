package me.darthwithap.android.stockmarketapp.data.mapper

import me.darthwithap.android.stockmarketapp.data.local.CompanyListingEntity
import me.darthwithap.android.stockmarketapp.data.remote.dto.CompanyInfoDto
import me.darthwithap.android.stockmarketapp.domain.model.CompanyInfo
import me.darthwithap.android.stockmarketapp.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing() = CompanyListing(name, symbol, exchange)
fun CompanyListing.toEntity() = CompanyListingEntity(name, symbol, exchange)

fun CompanyInfoDto.toCompanyInfo() = CompanyInfo(
  name = name ?: "",
  description = description ?: "",
  symbol = symbol ?: "",
  country = country ?: "",
  industry = industry ?: ""
)

fun CompanyInfo.toDto() = CompanyInfoDto(name, description, symbol, country, industry)