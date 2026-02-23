package com.karegraf.androidarchitecturestarterkit.data.remote.Imp

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.karegraf.androidarchitecturestarterkit.domain.model.PermissionStatus
import com.karegraf.androidarchitecturestarterkit.domain.repository.PermissionRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PermissionRepositoryImp @Inject constructor(@param:ApplicationContext private val context: Context) :
    PermissionRepository {
    override fun checkPermission(permission: String): PermissionStatus {
        return if (
            ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        ){
            PermissionStatus.Granted
        }else {
            PermissionStatus.Denied
        }
    }
}