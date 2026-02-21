package com.karegraf.androidarchitecturestarterkit.presentation


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val icon: ImageVector? = null,
    val label: String? = null,
    val contentDescription: String? = null,
) {
    object login : Screen(
        route = "login_screen",
        label = "Giris yap",
        contentDescription = "Daha fazlasi icin giris yap",
        icon = Icons.Filled.ExitToApp,

        )

    object ihbarList : Screen(
        route = "ihbar_list_screen",
        icon = Icons.Default.Home,
        label = "İhbarlar",
        contentDescription = "İhbar Listesi",
    )

    object ihbarDetail : Screen(
        route = "ihbar_detail_screen/{id}",
        icon = Icons.Filled.Add,
        label = "Detay",
        contentDescription = "İhbar Detayı",
    ) {
        fun createRoute(id: Int): String = "ihbar_detail_screen/$id"
    }

    object landing : Screen(
        route = "landing_screen",
        icon = Icons.Filled.Menu,
        label = "Menu",
        contentDescription = "Menu",
    )

    object profile : Screen(
        route = "profile_screen",
        icon = Icons.Filled.AccountCircle,
        label = "Profil",
        contentDescription = "Profil",
    )


    object contact : Screen(
        route = "contact_screen",
        icon = Icons.Filled.Call,
        label = "iletisim",
        contentDescription = "Bizimle iletisime gecin",
    )

    object about : Screen(
        route = "about_screen",
        icon = Icons.Filled.Star,
        label = "Hakkimizda",
        contentDescription = "Hakkimizda",
    )

    object help : Screen(
        route = "help_screen",
        icon = Icons.Filled.Info,
        label = "Yardim",
        contentDescription = "Yardima mi ihtiyaciniz var?",
    )

    object sks : Screen(
        route = "sks_screen",
        icon = Icons.Filled.Warning,
        label = "Sikca Karsilasilan Sorunlar",
        contentDescription = "Sikca Karsilasilan Sorunlar",
    )


    companion object {
        fun getBottomBarScreens(): List<Screen> {
            return listOf(ihbarList, profile, landing, contact)
        }
    }
}
