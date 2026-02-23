# Tüm İzin Ekranları Dökümantasyonu

Aşağıda proje içindeki "Kamera", "Galeri" ve "Konum" izin ekranlarının kaynak kodları ve satır satır açıklamaları tek bir belgede toplanmıştır. Her bölümde önce orijinal kod bloğu, sonra satır satır açıklama yer alır.

---

## 1) CameraPermissionScreen.kt

```kotlin
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

/**
 * Kamera izniyle ilgili kullanıcıya bilgi veren ve izin isteyen ekran.
 * PermissionHandler ile gerçek izin isteme işlemi yapılır.
 * Eğer izin verildiyse kamerayı açacak bir işlem tetiklenir.
 */
@Composable
fun CameraPermissionScreen(
    onBack: () -> Unit = {}
) {
    val context = LocalContext.current as androidx.activity.ComponentActivity
    var cameraStatus by remember { mutableStateOf<PermissionStatus?>(null) }
    var showCameraAction by remember { mutableStateOf(false) }

    // PermissionHandler ile izin sonucu yönetimi
    val permissionHandler = remember {
        PermissionHandler(context) { _, status ->
            cameraStatus = status
            // Eğer izin verildiyse kamerayı aç
            showCameraAction = (status == PermissionStatus.Granted)
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        // Kamera ile fotoğraf çekildiğinde yapılacak işlemler
        // Örneğin, resmi bir Image composable ile gösterebilirsiniz
    }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        permissionHandler.handleResult(isGranted)
        if (isGranted) {
            showCameraAction = true
            cameraLauncher.launch(null)
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
            permissionHandler.request(AppPermissions.CAMERA)
            launcher.launch(AppPermissions.CAMERA)
        }) {
            Text("Kamera İzni İste")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Durum: ${cameraStatus ?: "Bilinmiyor"}")
        Spacer(modifier = Modifier.height(32.dp))
        // Eğer izin verildiyse kamerayı açacak bir placeholder göster
        if (showCameraAction) {
            Text("Kamera açıldı! (Kamera intenti başlatıldı)", color = MaterialTheme.colorScheme.primary)
        }
        OutlinedButton(onClick = onBack) {
            Text("Geri Dön")
        }
    }
}
```

Satır satır açıklama:
1-13: Paket ve importlar — Compose, Activity result API ve proje içi model/sabitler import ediliyor. Bazı importlar (Intent, MediaStore, Manifest) kullanılmıyor; temizlenebilir.

20: @Composable — fonksiyonun Compose bileşeni olduğunu belirtir.

21-22: Fonksiyon imzası — `onBack` lambda parametresi geri butonu için.

23: `context` — Compose'dan activity alınır; launcher/permission işlemleri için gerekli.

24: `cameraStatus` — iznin durumunu tutan state.

25: `showCameraAction` — izin verildiğinde kamera açma davranışını tetiklemek için.

28-36: `PermissionHandler` kullanımı — request edilen izni saklamak ve sonuç geldiğinde durumu güncellemek için.

38-41: `cameraLauncher` — TakePicturePreview ile kamera önizleme/çekim için launcher.

43-50: `launcher` — RequestPermission launcher; izin verildiğinde PermissionHandler.handleResult çağrılır ve eğer granted ise `cameraLauncher.launch(null)` ile kamera tetiklenir.

52-77: UI düzeni — başlık, açıklama, buton, durum gösterimi ve geri butonu.

Notlar:
- Bazı importlar kullanılmadığı için kaldırılabilir.
- PermissionHandler.currentPermission doğru set edilmeden handleResult çağrılırsa `shouldShowRequestPermissionRationale` yanlış sonuç verebilir; request sırasındaki çağrı sırası önemlidir.

---

## 2) GalleryPermissionScreen.kt

```kotlin
package com.karegraf.androidarchitecturestarterkit.presentation.permission

import android.Manifest
import android.os.Build
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

/**
 * Galeri izniyle ilgili kullanıcıya bilgi veren ve izin isteyen ekran.
 * Sadece izin verildiyse galeri açılır.
 */
@Composable
fun GalleryPermissionScreen(
    onBack: () -> Unit = {}
) {
    val context = LocalContext.current as androidx.activity.ComponentActivity
    var galleryStatus by remember { mutableStateOf<PermissionStatus?>(null) }
    var showGalleryAction by remember { mutableStateOf(false) }

    // Android sürümüne göre uygun izin
    val galleryPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    // Galeri açma launcher'ı
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        // Galeriden seçilen görselin uri'si ile yapılacak işlemler
        // Örneğin, resmi bir Image composable ile gösterebilirsiniz
    }

    // İzin isteme launcher'ı
    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        galleryStatus = if (isGranted) PermissionStatus.Granted else PermissionStatus.Denied
        showGalleryAction = isGranted
        if (isGranted) {
            galleryLauncher.launch("image/*")
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
            text = "Galeri İzni",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Uygulamanın galeriye erişebilmesi için izin vermelisiniz.",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = {
            permissionLauncher.launch(galleryPermission)
        }) {
            Text("Galeri İzni İste")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Durum: ${galleryStatus ?: "Bilinmiyor"}")
        Spacer(modifier = Modifier.height(32.dp))
        if (showGalleryAction) {
            Text("Galeriye erişim sağlandı! (Galeri açıldı)", color = MaterialTheme.colorScheme.primary)
        }
        OutlinedButton(onClick = onBack) {
            Text("Geri Dön")
        }
    }
}
```

Satır satır açıklama:
1-11: Paket ve importlar — Build kontrolü ve gerekli Compose/Activity-result sınıfları.

27-31: Android 13+ için `READ_MEDIA_IMAGES`, eski sürümler için `READ_EXTERNAL_STORAGE` kullanımı — doğru ve gerekli bir kontrol.

34-37: `galleryLauncher` — GetContent ile kullanıcıdan görsel seçtirmek için.

40-47: `permissionLauncher` — izin sonucu işlenir; isGranted true ise `galleryLauncher.launch("image/*")` çağrılır.

UI kısmı: başlık, açıklama, izin butonu, durum gösterimi, geri butonu.

Notlar:
- Android 13 uyumu doğru.
- Kalıcı reddetme (PermanentlyDenied) durumunu ele almak isteniyorsa `shouldShowRequestPermissionRationale` ile kullanıcının ayarlara yönlendirilmesi eklenebilir.

---

## 3) LocationPermissionScreen.kt

```kotlin
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

/**
 * Konum izniyle ilgili kullanıcıya bilgi veren ve izin isteyen ekran.
 * PermissionHandler ile gerçek izin isteme işlemi yapılır.
 * Eğer izin verildiyse ekrana konum erişimi sağlandı mesajı gösterilir.
 */
@Composable
fun LocationPermissionScreen(
    onBack: () -> Unit = {}
) {
    val context = LocalContext.current as androidx.activity.ComponentActivity
    var locationStatus by remember { mutableStateOf<PermissionStatus?>(null) }
    var showLocationAction by remember { mutableStateOf(false) }

    // PermissionHandler ile izin sonucu yönetimi
    val permissionHandler = remember {
        PermissionHandler(context) { _, status ->
            locationStatus = status
            showLocationAction = (status == PermissionStatus.Granted)
        }
    }

    // Sadece launcher ile izin iste
    val locationLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        permissionHandler.handleResult(isGranted)
        if (isGranted) {
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
```

Satır satır açıklama:
- `PermissionHandler` ile sonuç yönetimi; izin sonucu `shouldShowRequestPermissionRationale` kullanılarak Denied/PermanentlyDenied ayrımı yapılır.
- `locationLauncher.launch(AppPermissions.LOCATION)` çağrısının `AppPermissions.LOCATION` sabitiyle manifestteki izin stringi uyumlu olduğundan emin olun.
- Android 12/13 ve arka plan konum izinleri farklılıkları nedeniyle ek yönetim gerekebilir.

---

## 4) PermissionHandler.kt

```kotlin
package com.karegraf.androidarchitecturestarterkit.presentation.permission

import android.Manifest
import android.icu.util.ICUUncheckedIOException
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.currentComposer
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.fragment.app.Fragment
import com.karegraf.androidarchitecturestarterkit.domain.model.PermissionStatus

class PermissionHandler(
    private val activity: ComponentActivity,
    private val onResult: (permission: String, PermissionStatus?) -> Unit,
) {
    private var currentPermission: String = ""

    fun handleResult(isGranted: Boolean) {
        val status = when {
            isGranted -> PermissionStatus.Granted
            shouldShowRequestPermissionRationale(
                activity, currentPermission
            ) -> PermissionStatus.Denied
            else -> PermissionStatus.PermanentlyDenied
        }
        onResult(currentPermission, status)
    }

    fun request(permission: String) {
        currentPermission = permission
    }
}
```

Satır satır açıklama:
- `currentPermission` ile hangi iznin istendiğini saklar.
- `handleResult` sonucu `PermissionStatus` enumuna çevirir:
  - Granted: izin verildi.
  - Denied: kullanıcı reddetti fakat tekrar sorulabilir (shouldShowRequestPermissionRationale true).
  - PermanentlyDenied: kullanıcı "bir daha sorma" seçeneğini işaretlemiş veya ayarlardan kapatmış.

Not: Bu sınıf yalnızca state saklama ve sonucu yorumlama işlevi görüyor; gerçek `ActivityResultLauncher` çağrısını çağırmıyor.

---

## 5) PermissionViewModel.kt

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

Açıklamalar:
- ViewModel Hilt ile inject edilmiş manager'ları kullanarak izin durumlarını StateFlow'larda sağlar.
- `checkAndReturn` ve `updateStatus` fonksiyonları API 33 (Tiramisu) annotasyonu içeriyor — buna neden gerek duyulduğunu `PermissionManager` implementasyonları ile kontrol edin.

---

## 6) PermissionItem.kt

```kotlin
package com.karegraf.androidarchitecturestarterkit.presentation.permission

import com.karegraf.androidarchitecturestarterkit.domain.model.PermissionStatus
import com.karegraf.androidarchitecturestarterkit.presentation.Screen

data class PermissionItem(
    val title: String = "",
    val description: String = "",
    val status: PermissionStatus? = null,
    val route: Screen = Screen.landing,
)
```

Açıklama:
- Basit bir DTO; izin menüsündeki öğeler için başlık, açıklama, durum ve rota tutar.

---

## 7) PermissionItemRow.kt

```kotlin
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
```

Açıklama:
- `permissionItems` listesi menü oluşturmak için kullanılır; her öğe `PermissionItem` tipindendir.

---

Son notlar ve iyileştirme önerileri:
- `PermissionHandler` ve `Permission*Screen` dosyalarında import edilen fakat kullanılmayan sınıfları temizleyin.
- `PermissionHandler.request(permission)` çağrısından sonra mutlaka ilgili launcher ile `launch` yapılmalı; şu an çağrı sırası doğru ama `currentPermission` set edilmeden `handleResult` çağrılırsa sorun olabilir.
- Konum izni istenirken manifestte ve runtime'da hangi izin stringinin (ACCESS_FINE_LOCATION / ACCESS_COARSE_LOCATION / ACCESS_BACKGROUND_LOCATION) kullanılacağına dikkat edin.
- Galeri izni için Android 13+ değişikliği (READ_MEDIA_IMAGES) zaten uygulanmış; bu iyi.
