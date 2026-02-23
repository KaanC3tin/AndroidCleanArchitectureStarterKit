package com.karegraf.androidarchitecturestarterkit.presentation.permission

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.karegraf.androidarchitecturestarterkit.presentation.Screen

/**
 * İzinler menüsü ekranı. PermissionHandler kullanılmaz, sadece navigation ve PermissionItem ile sade menü.
 * NavController parametresi zorunludur, navigation işlemleri üstteki NavHostController ile yapılır.
 */
@Composable
fun PermissionMenuScreen(
    navController: NavController
) {
    // Menüde gösterilecek izinler ve ilgili route'lar
    val permissionItems = listOf(
        PermissionItem(
            title = "Kamera İzni",
            description = "Kamera kullanımı için izin verin",
            route = (Screen.cameraPermission.route)
        ),
        PermissionItem(
            title = "Konum İzni",
            description = "Konum servisleri için izin verin",
            route = (Screen.locationPermission.route)
        ),
        PermissionItem(
            title = "Galeri İzni",
            description = "Galeriye erişim için izin verin",
            route = (Screen.galleryPermission.route)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "İzinler Menüsü",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(32.dp))
        // Her izin için bir buton oluştur
        permissionItems.forEach { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = item.title, style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = item.description, style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { navController.navigate(item.route) },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Git")
                    }
                }
            }
        }
    }
}