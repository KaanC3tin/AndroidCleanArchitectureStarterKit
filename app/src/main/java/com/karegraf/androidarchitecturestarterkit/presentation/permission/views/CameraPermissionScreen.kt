package com.karegraf.androidarchitecturestarterkit.presentation.permission.views

import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.karegraf.androidarchitecturestarterkit.domain.model.PermissionStatus
import com.karegraf.androidarchitecturestarterkit.utils.AppPermissions


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun CameraPermissionScreen(
    viewModel: PermissionViewModel = hiltViewModel()
) {

    //viewmodelden kamera izni durumunu al
    val status by viewModel.cameraStatus.collectAsState()
    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()
        ) { Bitemap ->
            Log.d("CameraPermissionScreen", "📸 Kamera içeriği seçildi: $Bitemap")
            viewModel.updateStatus(
                AppPermissions.CAMERA,
                if (Bitemap != null) PermissionStatus.Granted
                else PermissionStatus.Denied
            )
        }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        Log.d("CameraPermissionScreen", "📸 Kamera izni sonucu: $isGranted")
        viewModel.updateStatus(
            AppPermissions.CAMERA,
            if (isGranted) PermissionStatus.Granted
            else PermissionStatus.Denied
        )
        if(isGranted){
            cameraLauncher.launch()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Kamera İzni Durumu: ${status}")
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Kamera izni vermek için butona tıklayın")
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                permissionLauncher.launch(AppPermissions.CAMERA)
                Log.d("CameraPermissionScreen", "📸 Kamera izni talep edildi")
            }
        ) {
            Text("Kamera İzni Ver")
        }
        Spacer(Modifier.height(16.dp))

        Text(
            text = "Durum: ${status ?: "Bilinmiyor"}"
        )

        Spacer(Modifier.height(32.dp))

        // UI state artık derive ediliyor (local state yok)
        if (status == PermissionStatus.Granted) {
            Text(
                text = "Kamera açıldı!",
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(Modifier.height(24.dp))


    }
}