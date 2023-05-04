package deps.dependencies

import deps.Versions

object AndroidTest {
  const val instrumentationTest = "androidx.test.runner.AndroidJUnitRunner"
  const val composeUiTest = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
  const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
  const val junit = "androidx.test.ext:junit:${Versions.junit}"
}