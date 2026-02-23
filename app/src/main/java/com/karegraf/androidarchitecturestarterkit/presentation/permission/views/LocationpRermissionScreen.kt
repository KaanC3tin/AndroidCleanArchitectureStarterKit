package com.karegraf.androidarchitecturestarterkit.presentation.permission.views

import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.karegraf.androidarchitecturestarterkit.domain.model.PermissionStatus
import com.karegraf.androidarchitecturestarterkit.utils.AppPermissions

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun LocationPermissionScreen(
    viewModel: PermissionViewModel = hiltViewModel()
) {
    val status by viewModel.locationStatus.collectAsState()
    LaunchedEffect(Unit)
    {
        viewModel.checkLocation()
        //konum izni verilmis mi check ediyoruz
    }
    //Konum izni istemek icin launcher olusturuyoruz
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        viewModel.updateStatus(
            AppPermissions.LOCATION,
            if (isGranted) PermissionStatus.Granted
            else PermissionStatus.Denied
        )
    }
    Log.d("LocationPermissionScreen", "Konum izni durumu: $status")
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Konum İzni Durumu: ${status}")
        Button(onClick = {
            permissionLauncher.launch(AppPermissions.LOCATION)
            Log.d("LocationPermissionScreen", "Konum izni talebi başlatıldı")
        }

        ) {
            Text("Konum İzni Ver")
        }
        Text(text = "Konum izni, uygulamanın konum tabanlı özelliklerini kullanabilmesi için gereklidir. İzin vermek, uygulamanın size daha iyi hizmet sunmasını sağlar.")
    }

}

