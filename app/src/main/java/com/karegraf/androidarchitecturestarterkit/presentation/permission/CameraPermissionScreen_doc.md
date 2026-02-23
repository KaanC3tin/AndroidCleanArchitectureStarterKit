# CameraPermissionScreen — Açıklama ve Satır Satır İnceleme

Aşağıda `CameraPermissionScreen.kt` dosyasının güncel tam kodu ve satır/satır açıklaması bulunmaktadır.

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
        viewModel.checkCamera()
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
            // önce hangi iznin istendiğini kaydet
            permissionHandler.request(AppPermissions.CAMERA)
            launcher.launch(AppPermissions.CAMERA)
        }) {
            Text("Kamera İzni İste")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Durum: ${cameraStatus ?: "Bilinmiyor"}")
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

Satır/Satır Açıklama (bloklar halinde gruplanmış ve satır aralıkları verildi):

- 1-11: Paket ve importlar. Compose, Activity Result API, ViewModel injection ve proje içi modellere/birimlere ait importlar. Bazı importlar (Intent, MediaStore, Manifest) dosyada kalmış olabilir — eğer gerçek kullanım yoksa temizlenebilir.

- 13-19: Kısa dokümantasyon; fonksiyonun görevini özetler.

- 20-25: `@Composable` fonksiyon bildirimi. `viewModel: PermissionViewModel = hiltViewModel()` ekledik — böylece ekran ViewModel'deki izin durumlarını dinleyebilir.

- 26: `context` — Compose içinden Activity referansı alınır. Gerekli çünkü PermissionHandler Activity'ye ihtiyaç duyar.

- 29-30: `cameraStatus` — ViewModel'den akışı (StateFlow) alıyoruz ve Compose ile otomatik recompose sağlıyoruz. `showCameraAction` sadece UI davranışını tetiklemek için local state.

- 33-43: `permissionHandler` oluşturuluyor. Bu helper, hangi iznin istendiğini kaydeder ve izin sonucu geldiğinde callback ile ViewModel'i günceller (`viewModel.checkCamera()`). Önemli: PermissionHandler sadece sonucu yorumlamak ve `currentPermission` bilgisini taşımak için kullanılır; gerçek `ActivityResultLauncher` çağrısı `launcher` üzerindendir.

- 45-47: `cameraLauncher` — kamera ile fotoğraf çekmek için kullanılır (TakePicturePreview). Çekilen bitmap burada işlenebilir.

- 49-58: `launcher` — tekil permission isteme launcher'ı. Sonuç geldiğinde önce `permissionHandler.handleResult(isGranted)` çağırılır (böylece `shouldShowRequestPermissionRationale` kullanılarak Denied/PermanentlyDenied ayrımı yapılır), sonra `viewModel.checkCamera()` ile ViewModel güncellenir. Eğer izin verildiyse, `cameraLauncher` başlatılır.

- 60-95: UI düzeni — başlık, açıklama, izin butonu, durum metni, kamera eylemi tetiklenmesi ve geri butonu.

Neden yaptık (özet):
- Önceki haliyle ekranlar kendi local mutableState'lerini kullanıyor ve ViewModel'deki StateFlow'lar bağlanmamıştı; bu, merkezi durum yönetimini ve test edilebilirliği zayıflatırdı. Yeni haliyle ekran ViewModel'e abone olur (`collectAsState()`), izin sonucu ViewModel aracılığıyla güncellenir (`checkCamera()`), böylece izin durumunu başka bileşenler de güvenle kullanabilir.
- `PermissionHandler.request(...)` çağrısı permission launch'tan önce yapılır; böylece `handleResult` doğru `currentPermission` bilgisi ile çalışır.
- Kamera intenti yalnızca izin verildiğinde başlatılır.

İyileştirme önerileri:
- Kullanılmayan importları kaldırın.
- `viewModel.checkCamera()` yerine `viewModel.updateStatus(AppPermissions.CAMERA, status)` gibi daha doğrudan bir güncelleme tercih edilebilir (veya `PermissionHandler` callback'te status'u ViewModel'e iletebilirsiniz).
- Kalıcı reddetme (permanently denied) senaryosu kullanıcıyı ayarlara yönlendirecek şekilde ele alınabilir.