package deps.dependencies

import deps.Versions

object AndroidX {

  const val lifecycleRuntimeKtx =
    "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntimeKtx}"

  //Support libraries
  const val appcompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
  const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.4"

  //Compose
  const val composeUi = "androidx.compose.ui:ui:${Versions.compose}"
  const val composeNavigation = "androidx.navigation:navigation-compose:${Versions.composeNavigation}"
  const val composeMaterial = "androidx.compose.material:material:${Versions.compose}"
  const val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
  const val composeUiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
  const val composeMaterialIconsExtended =
    "androidx.compose.material:material-icons-extended:${Versions.compose}"
  const val composePaging = "androidx.paging:paging-compose:${Versions.composePaging}"
  const val composeLifecycleViewModel =
    "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.composeLifecycleViewModel}"
  const val composeActivity = "androidx.activity:activity-compose:${Versions.activityCompose}"


  //Dagger - Hilt
  const val hiltCompiler = "androidx.hilt:hilt-compiler:${Versions.hiltAndroid}"
  const val hiltNavigationCompose = "androidx.hilt:hilt-navigation-compose:${Versions.hiltAndroid}"

  // Kotlin Extensions and Coroutines support for Room
  const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
}