package com.karegraf.androidarchitecturestarterkit.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.karegraf.androidarchitecturestarterkit.presentation.navigation.AppNavGraph
import com.karegraf.androidarchitecturestarterkit.presentation.ui.theme.AndroidArchitectureStarterKitTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Navigation graph ile başlat, startDestination olarak izin menüsü
            AndroidArchitectureStarterKitTheme {
                AppNavGraph(startDestinationOverride = Screen.permission.route)
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidArchitectureStarterKitTheme {
    }
}