package me.darthwithap.android.stockmarketapp.util

sealed class Result<T>(val data: T? = null, val message: String? = null) {
  class Success<T>(data: T?, message: String? = null) : Result<T>(data, message)
  class Error<T>(message: String, data: T? = null) : Result<T>(data, message)
  class Loading<T>(val isLoading: Boolean = false) : Result<T>()
}
