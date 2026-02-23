# CameraPermissionScreen — Dokümantasyon

Amaç
- Kullanıcıdan kamera izni istemek, izin sonucuna göre ViewModel durumunu güncellemek ve izin verildiyse kamera intentini başlatmak.

Kullanılan sınıflar / semboller
- `com.karegraf.androidarchitecturestarterkit.presentation.permission.CameraPermissionScreen` (Composable ekran)
- `com.karegraf.androidarchitecturestarterkit.presentation.permission.PermissionViewModel` (`cameraStatus`, `checkAndReturn`, `checkCamera`)
- `com.karegraf.androidarchitecturestarterkit.presentation.permission.PermissionHandler`
- `com.karegraf.androidarchitecturestarterkit.domain.model.PermissionStatus`
- `com.karegraf.androidarchitecturestarterkit.utils.AppPermissions`
- Jetpack bileşenleri: `rememberLauncherForActivityResult`, `ActivityResultContracts.TakePicturePreview`, `ActivityResultContracts.RequestPermission`, `LocalContext`.

Tam kaynak kod (orijinal):

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
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Kamera izniyle ilgili kullanıcıya bilgi veren ve izin isteyen ekran.
 * Artık `PermissionViewModel` içindeki `cameraStatus` StateFlow'u kullanır.
 */
@Composable
fun CameraPermissionScreen(
    onBack: () -> Unit = {},
    viewModel: PermissionViewModel = hiltViewModel()
) {
    val context = LocalContext.current as androidx.activity.ComponentActivity

    // ViewModel'den akışı al
    val cameraStatus by viewModel.cameraStatus.collectAsState()
    var showCameraAction by remember { mutableStateOf(false) }

    // PermissionHandler ile izin sonucu yönetimi
    val permissionHandler = remember {
        PermissionHandler(context) { permission, status ->
            // ViewModel'den durumu güncelle (kod basit tutularak check fonksiyonu çağrılıyor)
            when (permission) {
                AppPermissions.CAMERA -> viewModel.checkCamera()
            }
            showCameraAction = (status == PermissionStatus.Granted)
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        // Kamera ile fotoğraf çekildiğinde yapılacak işlemler
    }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        permissionHandler.handleResult(isGranted)
        // ViewModel'i güncelle
        // kullan: checkAndReturn ile hem durumu al hem güncelle
        val status = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            viewModel.checkAndReturn(AppPermissions.CAMERA)
        } else {
            // eski sürümlerde checkAndReturn annotation sınırlaması olabilir; fallback
            viewModel.cameraStatus.value ?: PermissionStatus.Denied
        }

        if (status == PermissionStatus.Granted) {
            showCameraAction = true
            cameraLauncher.launch(null)
        } else {
            showCameraAction = false
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
            // önce hangi iznin istendiğini kaydet
            permissionHandler.request(AppPermissions.CAMERA)
            launcher.launch(AppPermissions.CAMERA)
        }) {
            Text("Kamera İzni İste")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Durum: ${'$'}{cameraStatus ?: "Bilinmiyor"}")
        Spacer(modifier = Modifier.height(32.dp))
        if (showCameraAction) {
            Text("Kamera açıldı! (Kamera intenti başlatıldı)", color = MaterialTheme.colorScheme.primary)
        }
        OutlinedButton(onClick = onBack) {
            Text("Geri Dön")
        }
    }
}
```

Satır satır açıklama (seçili önemli kısımlar)

1. import ve paket bildirimi
   - Neden: Composable ekranın ihtiyaç duyduğu Android/Compose API'lerini içe aktarır.

2. @Composable fonksiyon imzası
   - onBack: geri navigasyon callback'i
   - viewModel: `PermissionViewModel` Hilt ile inject ediliyor. Bu ViewModel içindeki `cameraStatus` StateFlow'u UI'ı besler.

3. `val cameraStatus by viewModel.cameraStatus.collectAsState()`
   - ViewModel'deki StateFlow'u Compose tarafından gözlemlenebilir hale getirir.

4. `PermissionHandler` oluşturulması
   - Permission isteği öncesi hangi iznin sorulduğunu kaydetmek için kullanılır. handleResult çağrıldığında callback içine `permission` ve `status` gelir.

5. `cameraLauncher` (TakePicturePreview)
   - İzin verildiğinde kamerayı başlatmak için kullanılan ActivityResult API'si.

6. `launcher` (RequestPermission)
   - Kullanıcı izin verdiğinde `permissionHandler.handleResult(isGranted)` çağrılır.
   - Önemli: burada `viewModel.checkAndReturn(AppPermissions.CAMERA)` kullanılarak hem ViewModel güncellenir hem de anlık durum alınır. Ancak `checkAndReturn` sadece Android T (API 33) ve üzeri için işaretlendiği için runtime SDK kontrolü yapılır.

7. Buton onClick
   - `permissionHandler.request(AppPermissions.CAMERA)` ile hangi iznin isteneceği kaydedilir. Ardından `launcher.launch(AppPermissions.CAMERA)` ile izin dialogu gösterilir.

Neden `checkAndReturn` kullanıldı?
- Tek bir çağrıda (manager üzerinden) izin durumunu doğrulayıp ViewModel state'ini güncellemek ve sonucu döndürmek için. Bu hem UI tarafında duplication'ı önler hem de state'in her zaman manager'ın verdiği doğru değerle senkron olmasını sağlar.

Dikkat edilmesi gerekenler
- `checkAndReturn` fonksiyonu `@RequiresApi(Build.VERSION_CODES.TIRAMISU)` ile işaretlenmiş olabilir; bu sebeple view içinde runtime SDK kontrolü yapılmalı. Alternatif olarak ViewModel içinde `checkAndReturnCompat(permission)` şeklinde bir wrapper eklemek daha temizdir.
- `PermissionHandler` `shouldShowRequestPermissionRationale` kullanarak PermanentlyDenied tespiti yapar; UI'da bu durumda kullanıcıyı ayarlara yönlendirme (settings intent) gösterilmelidir.

Örnek kullanım akışı
1. Kullanıcı 'Kamera İzni İste' butonuna basar.
2. `permissionHandler.request(AppPermissions.CAMERA)` ile iznin tipi kaydedilir.
3. `launcher.launch(AppPermissions.CAMERA)` ile sistem izin penceresi açılır.
4. Kullanıcı karar verince `launcher` callback'i tetiklenir; burada `permissionHandler.handleResult(isGranted)` çağrısı ile izin sonucu belirlenir ve `viewModel.checkAndReturn(...)` ile ViewModel state'i güncellenir.

İlgili dosyalar
- `app/src/main/java/com/karegraf/.../presentation/permission/CameraPermissionScreen.kt` (kaynak kodu)
- `app/src/main/java/com/karegraf/.../presentation/permission/PermissionViewModel.kt` (ViewModel)
- `app/src/main/java/com/karegraf/.../presentation/permission/PermissionHandler.kt` (handler)

---

Bu dosya Camera ekranıyla ilgili kodun tamamını ve nedenlerini içerir. Eğer `checkAndReturn`'ı ViewModel içinde compat bir wrapper ile merkezileştirmek isterseniz, bunu da dokümana ekleyebilirim.