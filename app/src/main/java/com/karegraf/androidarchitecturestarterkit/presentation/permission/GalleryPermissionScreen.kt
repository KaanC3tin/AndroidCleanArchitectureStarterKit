package com.karegraf.androidarchitecturestarterkit.presentation.permission

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.karegraf.androidarchitecturestarterkit.domain.model.PermissionStatus
import androidx.hilt.navigation.compose.hiltViewModel
import com.karegraf.androidarchitecturestarterkit.utils.AppPermissions

/**
 * Galeri izniyle ilgili kullanıcıya bilgi veren ve izin isteyen ekran.
 * Artık `PermissionViewModel` içindeki `galleryStatus` StateFlow'u kullanır.
 */
@Composable
fun GalleryPermissionScreen(
    onBack: () -> Unit = {},
    viewModel: PermissionViewModel = hiltViewModel()
) {
    val context = LocalContext.current as androidx.activity.ComponentActivity

    val galleryStatus by viewModel.galleryStatus.collectAsState()
    var showGalleryAction by remember { mutableStateOf(false) }

    // Android sürümüne göre uygun izin
    val galleryPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        android.Manifest.permission.READ_MEDIA_IMAGES
    } else {
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    }

    // Galeri açma launcher'ı
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        // Galeriden seçilen görselin uri'si ile yapılacak işlemler
    }

    // İzin isteme launcher'ı
    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        // ViewModel'i güncelle
        if (isGranted) {
            showGalleryAction = true
        } else {
            showGalleryAction = false
        }
        // permissionHandler kullanılmayan bir durumda direkt viewModel'i güncellemek daha temiz
        // değiştir: checkAndReturn kullan
        val status = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            viewModel.checkAndReturn(AppPermissions.GALLERY)
        } else {
            viewModel.galleryStatus.value ?: PermissionStatus.Denied
        }
        if (status == PermissionStatus.Granted) {
            galleryLauncher.launch("image/*")
        }
    }

    // Basit permission handler kullanımı (hangi iznin isteneceğini kaydetmek için)
    val permissionHandler = remember {
        PermissionHandler(context) { permission, status ->
            when (permission) {
                AppPermissions.GALLERY -> viewModel.checkGallery()
            }
            showGalleryAction = (status == PermissionStatus.Granted)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Galeri İzni",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Uygulamanın galeriye erişebilmesi için izin vermelisiniz.",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = {
            permissionHandler.request(AppPermissions.GALLERY)
            permissionLauncher.launch(galleryPermission)
        }) {
            Text("Galeri İzni İste")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Durum: ${galleryStatus ?: "Bilinmiyor"}")
        Spacer(modifier = Modifier.height(32.dp))
        if (showGalleryAction) {
            Text("Galeriye erişim sağlandı! (Galeri açıldı)", color = MaterialTheme.colorScheme.primary)
        }
        OutlinedButton(onClick = onBack) {
            Text("Geri Dön")
        }
    }
}