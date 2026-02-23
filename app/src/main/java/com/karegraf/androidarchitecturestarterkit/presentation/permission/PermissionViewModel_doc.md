# PermissionViewModel.kt — Satır Satır Açıklama

Dosya: `app/src/main/java/com/karegraf/androidarchitecturestarterkit/presentation/permission/PermissionViewModel.kt`

Aşağıda dosyanın orijinal kodu ve her satır için kısa açıklamalar bulunmaktadır.

```kotlin
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
```

Satır satır açıklamalar (kod içindeki satır numaraları 1'den başlar):

1: package bildirimi.
2-13: importlar — ViewModel, permission manager'lar, PermissionStatus, AppPermissions, Hilt ve StateFlow için gereken importlar.

15: @HiltViewModel anotasyonu — sınıfın Hilt ile sağlanmasını sağlar.

16-22: PermissionViewModel sınıfı — üç manager (camera/gallery/location) constructor ile inject edilir.

23-25: _cameraStatus MutableStateFlow ve dışarıya StateFlow olarak expose edilmesi; UI tarafından gözlemlenebilir.

27-29: _galleryStatus benzeri durum alanı.

32-34: _locationStatus benzeri durum alanı.

37-50: checkAndReturn fonksiyonu — verilen permission stringine göre ilgili manager'ın check() metodunu çağırır ve sonucu updateStatus ile günceller; fonksiyon Android Tiramisu gereksinimi ile anotasyonlu (muhtemelen check metotları yeni API ile ilişkilidir).

53-62: updateStatus fonksiyonu — gelen permission stringine göre ilgili StateFlow'u günceller.

65-74: helper fonksiyonlar (checkCamera, checkGallery, checkLocation) — manager'ların check() metodlarını doğrudan çağırıp state'i günceller.

Notlar / İyileştirme önerileri:
- `@RequiresApi(Build.VERSION_CODES.TIRAMISU)` annotasyonu `checkAndReturn` ve `updateStatus` üzerinde; updateStatus'ın API 33'e neden ihtiyaç duyduğunu kontrol edin (aslında burada doğrudan bir API-33 özelliği yok gibi duruyor). Eğer check() metodları API 33'e bağlı ise annotasyon uygun; değilse kaldırılmalı.
- StateFlow'lar UI ile reaktif bağlantı için uygun seçilmiş.
- PermissionManager implementasyonlarının `check()` metodlarının manifest ve runtime izinlerini doğru okuyup okumadığını test edin (özellikle emulator/ci ortamındaki farklı API seviyeleri için).

