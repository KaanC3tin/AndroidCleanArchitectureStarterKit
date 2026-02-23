# GalleryPermissionScreen.kt — Satır Satır Açıklama

Dosya: `app/src/main/java/com/karegraf/androidarchitecturestarterkit/presentation/permission/GalleryPermissionScreen.kt`

Aşağıda dosyanın orijinal kodu ve her satır için kısa açıklamalar bulunmaktadır.

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

Satır satır açıklamalar (kod içindeki satır numaraları 1'den başlar):

1: package bildirimi — dosyanın paket yolunu belirtir.
2-11: importlar — Build versiyon kontrolü, activity-result, Compose UI bileşenleri ve PermissionStatus modeli için gerekli sınıflar.

14-17: Dokümantasyon yorumları — ekranın amacı ve davranışı özetlenir.

18: @Composable annotasyonu.

19-21: GalleryPermissionScreen imzası — onBack lambda parametresi.

22: context değişkeni — activity referansı alınır (launcher, permission işlemleri için ihtiyaç).

23: galleryStatus — galeri izninin durumunu tutan state.

24: showGalleryAction — izin verildiğinde galeriyi açmak veya mesaj göstermek için bool.

27-31: Android sürüm kontrolü ile hangi izin stringinin kullanılacağı belirlenir:
 - Android 13+ (Tiramisu) için READ_MEDIA_IMAGES.
 - Önceki sürümler için READ_EXTERNAL_STORAGE.
Bu, API değişikliklerine uyum sağlamak içindir.

34-37: galleryLauncher — ActivityResultContracts.GetContent ile kullanıcıdan görsel seçtirmek için launcher tanımı; seçilen URI ile yapılacak işlem yorumlanmış.

40-47: permissionLauncher — RequestPermission kullanılarak izin sonucu alındıktan sonra durum güncellenir ve izin varsa galleryLauncher başlatılır.

49-79: UI Column — başlık, açıklama, izin isteme butonu, durum tekstleri ve geri butonu.
 - Butona basıldığında permissionLauncher.launch(galleryPermission) çağrılır.
 - showGalleryAction true ise kullanıcıya galeri açıldığı mesajı gösterilir.

80: fonksiyon kapanışı.

Notlar / İyileştirmeler:
- `Manifest` import'u kullanılmış ve gerekli; Build kontrolü doğru.
- Android 13+ izin modeline uyum sağlanmış, bu doğru yaklaşımdır.
- Eğer kullanıcı "PermanentlyDenied" (kalıcı reddetme) durumunu da ele almak isteniyorsa, permission sonucu işlenirken shouldShowRequestPermissionRationale çağrısı ile kullanıcıyı ayarlara yönlendirme mantığı eklenebilir.

