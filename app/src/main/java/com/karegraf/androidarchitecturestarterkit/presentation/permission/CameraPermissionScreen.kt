package com.karegraf.androidarchitecturestarterkit.presentation.permission

import android.Manifest
import android.content.Intent
import android.provider.MediaStore
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
import com.karegraf.androidarchitecturestarterkit.utils.AppPermissions
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Kamera izniyle ilgili kullanıcıya bilgi veren ve izin isteyen ekran.
 * Artık `PermissionViewModel` içindeki `cameraStatus` StateFlow'u kullanır.
 */
@Composable
fun CameraPermissionScreen(
    onBack: () -> Unit = {},
    viewModel: PermissionViewModel = hiltViewModel()
) {
    val context = LocalContext.current as androidx.activity.ComponentActivity

    // ViewModel'den akışı al
    val cameraStatus by viewModel.cameraStatus.collectAsState()
    var showCameraAction by remember { mutableStateOf(false) }

    // PermissionHandler ile izin sonucu yönetimi
    val permissionHandler = remember {
        PermissionHandler(context) { permission, status ->
            // ViewModel'den durumu güncelle (kod basit tutularak check fonksiyonu çağrılıyor)
            when (permission) {
                AppPermissions.CAMERA -> viewModel.checkCamera()
            }
            showCameraAction = (status == PermissionStatus.Granted)
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        // Kamera ile fotoğraf çekildiğinde yapılacak işlemler
    }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        permissionHandler.handleResult(isGranted)
        // ViewModel'i güncelle
        // kullan: checkAndReturn ile hem durumu al hem güncelle
        val status = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            viewModel.checkAndReturn(AppPermissions.CAMERA)
        } else {
            // eski sürümlerde checkAndReturn annotation sınırlaması olabilir; fallback
            viewModel.cameraStatus.value ?: PermissionStatus.Denied
        }

        if (status == PermissionStatus.Granted) {
            showCameraAction = true
            cameraLauncher.launch(null)
        } else {
            showCameraAction = false
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
            text = "Kamera İzni",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Uygulamanın kamera kullanabilmesi için izin vermelisiniz.",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = {
            // önce hangi iznin istendiğini kaydet
            permissionHandler.request(AppPermissions.CAMERA)
            launcher.launch(AppPermissions.CAMERA)
        }) {
            Text("Kamera İzni İste")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Durum: ${cameraStatus ?: "Bilinmiyor"}")
        Spacer(modifier = Modifier.height(32.dp))
        if (showCameraAction) {
            Text("Kamera açıldı! (Kamera intenti başlatıldı)", color = MaterialTheme.colorScheme.primary)
        }
        OutlinedButton(onClick = onBack) {
            Text("Geri Dön")
        }
    }
}