package com.karegraf.androidarchitecturestarterkit.data.di

import android.content.Context
import com.karegraf.androidarchitecturestarterkit.data.manager.CameraPermissionManager
import com.karegraf.androidarchitecturestarterkit.data.manager.GalleryPermissionManager
import com.karegraf.androidarchitecturestarterkit.data.manager.LocationPermissionManager
import com.karegraf.androidarchitecturestarterkit.data.remote.Imp.PermissionRepositoryImp
import com.karegraf.androidarchitecturestarterkit.domain.repository.PermissionRepository
import com.karegraf.androidarchitecturestarterkit.domain.use_case.permission.CheckCameraPermissionUseCase
import com.karegraf.androidarchitecturestarterkit.domain.use_case.permission.CheckGalleryPermissionUseCase
import com.karegraf.androidarchitecturestarterkit.domain.use_case.permission.CheckLocationPermissionUseCase
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.Provides
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PermissionModule {
    @Singleton
    @Provides
    fun providePermissionRepository(@ApplicationContext context: Context): PermissionRepository {
        return PermissionRepositoryImp(context)
    }

    @Singleton
    @Provides
    fun provideCameraPermissionManager(
        useCase: CheckCameraPermissionUseCase
    ): CameraPermissionManager{
        return CameraPermissionManager(useCase)
    }

    @Singleton
    @Provides
    fun provideGalleryPermissionManager(
        useCase: CheckGalleryPermissionUseCase
    ): GalleryPermissionManager{
        return GalleryPermissionManager(useCase)
    }

    @Singleton
    @Provides
    fun provideLocationPermissionManager(
        useCase: CheckLocationPermissionUseCase
    ): LocationPermissionManager{
        return LocationPermissionManager(useCase)
    }
}