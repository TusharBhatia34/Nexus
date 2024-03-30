package com.example.nexus


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.nexus.ui.theme.gray
import com.example.nexus.ui.theme.lightblack


private val LightColors = lightColorScheme(
    primary = Color.White,
    secondary = Color.Black,
    secondaryContainer = Color.Black,
    onPrimaryContainer = lightblack

)
private val DarkColors = darkColorScheme(
    primary = Color.Black,
    secondary = Color.White,
    secondaryContainer = lightblack,
    onPrimaryContainer = gray
)
@Composable
fun customTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {

    val colors = if (!useDarkTheme){
        LightColors
    }
    else{
        DarkColors
    }

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}