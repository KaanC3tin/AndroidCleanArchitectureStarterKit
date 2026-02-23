package com.karegraf.androidarchitecturestarterkit.presentation.permission

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
 * Konum izniyle ilgili kullanıcıya bilgi veren ve izin isteyen ekran.
 * Artık `PermissionViewModel` içindeki `locationStatus` StateFlow'u kullanır.
 */
@Composable
fun LocationPermissionScreen(
    onBack: () -> Unit = {},
    viewModel: PermissionViewModel = hiltViewModel()
) {
    val context = LocalContext.current as androidx.activity.ComponentActivity
    val locationStatus by viewModel.locationStatus.collectAsState()
    var showLocationAction by remember { mutableStateOf(false) }

    // PermissionHandler ile izin sonucu yönetimi
    val permissionHandler = remember {
        PermissionHandler(context) { permission, status ->
            when (permission) {
                AppPermissions.LOCATION -> viewModel.checkLocation()
            }
            showLocationAction = (status == PermissionStatus.Granted)
        }
    }

    // Sadece launcher ile izin iste
    val locationLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        permissionHandler.handleResult(isGranted)
        // değiştir: checkAndReturn kullan
        val status = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            viewModel.checkAndReturn(AppPermissions.LOCATION)
        } else {
            viewModel.locationStatus.value ?: PermissionStatus.Denied
        }
        if (status == PermissionStatus.Granted) {
            showLocationAction = true
            // Burada konum işlemleri başlatılabilir
        } else {
            showLocationAction = false
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
            text = "Konum İzni",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Uygulamanın konum servislerini kullanabilmesi için izin vermelisiniz.",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = {
            // Sadece launcher ile izin iste
            permissionHandler.request(AppPermissions.LOCATION)
            locationLauncher.launch(AppPermissions.LOCATION)
        }) {
            Text("Konum İzni İste")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Durum: ${locationStatus ?: "Bilinmiyor"}")
        Spacer(modifier = Modifier.height(32.dp))
        if (showLocationAction) {
            Text("Konum erişimi sağlandı! (Konum işlemleri başlatıldı)", color = MaterialTheme.colorScheme.primary)
        }
        OutlinedButton(onClick = onBack) {
            Text("Geri Dön")
        }
    }
}