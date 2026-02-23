package com.karegraf.androidarchitecturestarterkit.presentation.permission

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.karegraf.androidarchitecturestarterkit.data.manager.CameraPermissionManager
import com.karegraf.androidarchitecturestarterkit.data.manager.GalleryPermissionManager
import com.karegraf.androidarchitecturestarterkit.data.manager.LocationPermissionManager
import com.karegraf.androidarchitecturestarterkit.domain.model.PermissionStatus
import com.karegraf.androidarchitecturestarterkit.utils.AppPermissions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PermissionViewModel @Inject constructor(
    private val cameraPermissionManager: CameraPermissionManager,
    private val galleryPermissionManager: GalleryPermissionManager,
    private val locationPermissionManager: LocationPermissionManager
) : ViewModel() {
    private val _cameraStatus = MutableStateFlow<PermissionStatus?>(null)
    val cameraStatus: StateFlow<PermissionStatus?> = _cameraStatus.asStateFlow()

    private val _galleryStatus = MutableStateFlow<PermissionStatus?>(null)
    val galleryStatus: StateFlow<PermissionStatus?> = _galleryStatus.asStateFlow()


    private val _locationStatus = MutableStateFlow<PermissionStatus?>(null)
    val locationStatus: StateFlow<PermissionStatus?> = _locationStatus.asStateFlow()


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun checkAndReturn(permission: String): PermissionStatus {
        val status = when (permission) {
            AppPermissions.CAMERA -> cameraPermissionManager.check()
            AppPermissions.GALLERY -> galleryPermissionManager.check()
            AppPermissions.LOCATION -> locationPermissionManager.check()
            else -> PermissionStatus.Denied
        }
        updateStatus(permission, status)
        return status
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun updateStatus(permission: String, status: PermissionStatus?) {
        when (permission) {
            AppPermissions.CAMERA -> _cameraStatus.value = status
            AppPermissions.GALLERY -> _galleryStatus.value = status
            AppPermissions.LOCATION -> _locationStatus.value = status
            else -> {
                // Unknown permission key: don't change any state. (Previously returned a value mistakenly.)
            }
        }


    }


    fun checkCamera() {
        _cameraStatus.value = cameraPermissionManager.check()
    }

    fun checkGallery() {
        _galleryStatus.value = galleryPermissionManager.check()
    }

    fun checkLocation() {
        _locationStatus.value = locationPermissionManager.check()
    }
    // helper check functions removed (not used) to keep ViewModel minimal


}