package me.darthwithap.android.stockmarketapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import me.darthwithap.android.stockmarketapp.navigation.Screens
import me.darthwithap.android.stockmarketapp.presentation.company_listing.CompanyListingsScreen
import me.darthwithap.android.stockmarketapp.ui.theme.StockMarketAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      StockMarketAppTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colors.background
        ) {
          val navController = rememberNavController()
          NavHost(
            navController = navController,
            startDestination = Screens.CompanyListings.route,
            modifier = Modifier.padding(4.dp)
          ) {
            composable(route = Screens.CompanyListings.route) {
              CompanyListingsScreen(navController = navController)
            }
            composable(route = Screens.CompanyInfo.route) {
              // TODO: CompanyInfoScreen composable implementation
              //CompanyInfoScreen()
            }
          }
          //DestinationsNavHost(navGraph = )
        }
      }
    }
  }
}