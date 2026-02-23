# LocationPermissionScreen.kt — Satır Satır Açıklama

Dosya: `app/src/main/java/com/karegraf/androidarchitecturestarterkit/presentation/permission/LocationPermissionScreen.kt`

Aşağıda dosyanın orijinal kodu ve her satır için kısa açıklamalar bulunmaktadır.

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

Satır satır açıklamalar (kod içindeki satır numaraları 1'den başlar):

1: package bildirimi — dosyanın paket yolunu belirtir.
2-11: importlar — launcher, compose bileşenleri ve PermissionStatus ile AppPermissions için gerekli importlar.

14-17: Dokümantasyon yorumları — ekranın amacı özetlenir.

18: @Composable annotasyonu.

19-21: LocationPermissionScreen imzası.

22: context — activity referansı, PermissionHandler için gerekli.

23: locationStatus — konum izninin durumunu tutar.

24: showLocationAction — izin verildiğinde konum işlemleri başladığını göstermek için bool.

27-33: permissionHandler — PermissionHandler nesnesi oluşturulur; callback içinde locationStatus güncellenir ve showLocationAction belirlenir.

36-45: locationLauncher — RequestPermission için launcher; sonucu permissionHandler.handleResult ile işler ve izin verildiyse showLocationAction true yapar (aynı zamanda konum işlemleri burada başlatılırsa eklenebilir).

47-79: UI Column — başlık, açıklama, izin butonu, durum gösterimi ve geri butonu.
 - Buton tıklanınca locationLauncher.launch(AppPermissions.LOCATION) çağrılır.

80: fonksiyon kapanışı.

Notlar / Hatalar ve düzeltme önerileri:
- Bu ekranda `AppPermissions.LOCATION` sabitinin doğru tanımlandığından emin olun; manifest ve runtime izin stringi (ACCESS_FINE_LOCATION veya ACCESS_COARSE_LOCATION) ile eşleşmelidir.
- Kullanıcı konumu istenirken Android 12+ ve 13+ için ek davranışlar (background location gibi) ayrı izinler gerektirebilir; bu ihtiyaçlara dikkat edin.

