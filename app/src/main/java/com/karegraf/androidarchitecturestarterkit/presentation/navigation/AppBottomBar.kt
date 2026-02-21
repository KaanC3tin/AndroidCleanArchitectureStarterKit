package com.karegraf.evrakapp.presentation.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.karegraf.androidarchitecturestarterkit.presentation.Screen

/**
 * BottomBar - Alt navigasyon çubuğu
 * Profil, Ayarlar ve İhbarlar itemlerini gösterir
 */
@Composable
fun AppBottomBar(
    currentRoute: String,
    onNavigate: (Screen) -> Unit
) {
    val bottomBarScreens = Screen.getBottomBarScreens()

    NavigationBar(
        windowInsets = NavigationBarDefaults.windowInsets
    ) {
        bottomBarScreens.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = { onNavigate(screen) },
                icon = {
                    screen.icon?.let { icon ->
                        Icon(
                            imageVector = icon,
                            contentDescription = screen.contentDescription
                        )
                    }
                },
                label = {
                    screen.label?.let { Text(it) }
                }
            )
        }
    }
}

@Composable
@Preview

fun BottomBarPreview() {
    AppBottomBar(
        currentRoute = Screen.profile.route,
        onNavigate = {}
    )
}

