package com.example.nexus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
installSplashScreen()
        setContent {
            val viewModel= hiltViewModel<NexusViewModel>()
            val darkTheme = viewModel.isDarkTheme.collectAsState()
            customTheme(useDarkTheme = darkTheme.value) {
nexusUi(viewModel,darkTheme.value)
            }
        }
    }
}

