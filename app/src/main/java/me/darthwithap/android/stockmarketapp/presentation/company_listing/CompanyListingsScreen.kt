package me.darthwithap.android.stockmarketapp.presentation.company_listing

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph(start = true)
@Destination
@Composable
fun CompanyListingsScreen(
  navController: NavController,
  viewModel: CompanyListingsViewModel = hiltViewModel()
) {
  val swipeRefreshState = rememberSwipeRefreshState(
    isRefreshing = viewModel.state.isRefreshing
  )
  val state = viewModel.state

  Column(modifier = Modifier.fillMaxSize()) {
    OutlinedTextField(
      value = state.query, onValueChange = {
        viewModel.onEvent(CompanyListingsEvent.onSearchQueryChange(it))
      },
      modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth(),
      placeholder = { Text(text = "Search") },
      maxLines = 1,
      singleLine = true
    )
    SwipeRefresh(state = swipeRefreshState, onRefresh = {
      viewModel.onEvent(CompanyListingsEvent.Refresh)
    }) {
      LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(state.companies.size) { index ->
          CompanyItem(company = state.companies[index],
            modifier = Modifier
              .fillMaxWidth()
              .clickable {
                // TODO: Improve this navigation with navArgs
                //navController.navigate(Screens.CompanyInfo.route)
              }
              .padding(16.dp)
          )
          if (index < state.companies.size) {
            Divider(modifier = Modifier.padding(horizontal = 16.dp))
          }
        }
      }
    }
  }
}