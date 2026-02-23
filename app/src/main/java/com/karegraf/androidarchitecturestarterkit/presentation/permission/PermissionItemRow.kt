package com.karegraf.androidarchitecturestarterkit.presentation.permission

import com.karegraf.androidarchitecturestarterkit.presentation.Screen

val permissionItems = listOf(

    PermissionItem(
        title = "Kamera İzni",
        description = "Kamera kullanımı için izin verin",
        route =Screen.cameraPermission.route),
    PermissionItem(
        title = "Konum İzni",
        description = "Konum servisleri için izin verin",
        route =  Screen.locationPermission.route
    ),
    PermissionItem(
        title = "Galeri İzni",
        description = "Galeriye erişim için izin verin",
        route = Screen.galleryPermission.route
    )
)