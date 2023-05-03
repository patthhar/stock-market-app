package me.darthwithap.android.stockmarketapp.data.mapper

import me.darthwithap.android.stockmarketapp.data.local.CompanyListingEntity
import me.darthwithap.android.stockmarketapp.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing() = CompanyListing(name, symbol, exchange)
fun CompanyListing.toEntity() = CompanyListingEntity(name, symbol, exchange)