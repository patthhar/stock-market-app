package deps

object Dependencies {
  const val material = "com.google.android.material:material:${Versions.material}"
  const val desugar = "com.android.tools:desugar_jdk_libs:${Versions.desugar}"
  const val opencsv = "com.opencsv:opencsv:${Versions.opencsv}"
  const val accompanistFlowLayout =
    "com.google.accompanist:accompanist-flowlayout:${Versions.accompanistFlowLayout}"
  const val accompanistSwipeRefresh =
    "com.google.accompanist:accompanist-swiperefresh:${Versions.accompanistSwipeRefresh}"

  // Retrofit
  const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
  const val moshiConvertor = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
  const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
  const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"

  //Coil
  const val coil = "io.coil-kt:coil-compose:${Versions.coil}"

  // Room
  const val room = "androidx.room:room-runtime:${Versions.room}"
  const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"

  //Dagger - Hilt
  const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hiltAndroid}"
  const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hiltAndroid}"

  // Compose Nav Destinations
  const val composeDestinationsCore =
    "io.github.raamcosta.compose-destinations:core:${Versions.ramcostaComposeDestinations}"
  const val composeDestinationsKsp =
    "io.github.raamcosta.compose-destinations:ksp:${Versions.ramcostaComposeDestinations}"
}