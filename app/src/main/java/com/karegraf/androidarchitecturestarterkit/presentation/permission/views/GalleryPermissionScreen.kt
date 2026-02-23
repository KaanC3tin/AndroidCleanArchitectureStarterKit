package com.karegraf.androidarchitecturestarterkit.presentation.permission.views

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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

@Composable
fun GalleryPermissionScreen(
    viewModel: PermissionViewModel = hiltViewModel()
) {

    val status by viewModel.galleryStatus.collectAsState()

    val galleryLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri -> }
    val permissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            // sonucu bildiriir
            viewModel.onGalleryPermissionResult(isGranted = isGranted)
            // sonucu bildirince galeriyi acar
            if (isGranted) {
                galleryLauncher.launch("image/*")

            }
        }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Galeri İzni Durumu: ${status}")
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Galeri izni vermek için butona tıklayın")
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                permissionLauncher.launch(AppPermissions.GALLERY)
                Log.d( "GalleryPermissionScreen", "📸 Kamera izni talep edildi")
            }
        ) {
            Text("Galeri İzni Ver")
        }
        Spacer(Modifier.height(16.dp))

        Text(
            text = "Durum: ${status ?: "Bilinmiyor"}"
        )

        Spacer(Modifier.height(32.dp))

        // UI state artık derive ediliyor (local state yok)
        if (status == PermissionStatus.Granted) {
            Text(
                text = "Galeri açıldı!",
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(Modifier.height(24.dp))



    }//viewmodelden galeri acilisi

}

