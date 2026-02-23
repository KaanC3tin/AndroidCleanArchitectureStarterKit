# PermissionHandler.kt — Satır Satır Açıklama

Dosya: `app/src/main/java/com/karegraf/androidarchitecturestarterkit/presentation/permission/PermissionHandler.kt`

Aşağıda dosyanın orijinal kodu ve her satır için kısa açıklamalar bulunmaktadır.

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

Satır satır açıklamalar (kod içindeki satır numaraları 1'den başlar):

1: package bildirimi.

2-10: importlar — bazı importlar kullanılmıyor (ör. Manifest, ActivityResultLauncher, ActivityResultContracts, currentComposer, Fragment) — temizlenebilir. Kalan gerekli import `ComponentActivity` ve `shouldShowRequestPermissionRationale` ile `PermissionStatus`.

12-15: class tanımı — PermissionHandler Activity referansı ve sonuç callback'i alır. onResult(permission, status) ile dışarı bildirim yapılır.

16: currentPermission değişkeni — hangi iznin istendiğini saklar.

18-28: handleResult fonksiyonu — iznin sonucu işlendiğinde çağrılır. Mantık:
 - Eğer isGranted true ise PermissionStatus.Granted.
 - Değilse ve shouldShowRequestPermissionRationale true ise PermissionStatus.Denied (yani kullanıcı reddetti ama tekrar sorulabilir).
 - Aksi halde PermissionStatus.PermanentlyDenied (kullanıcı "bir daha sorma" veya ayarlardan kapattı).
 - Son olarak callback ile sonuç döndürülür.

30-32: request fonksiyonu — çağrıldığında currentPermission güncellenir (sadece saklar, launcher veya doğrudan izin istemez).

Notlar / Düzeltme önerileri:
- Bu sınıf sadece iznin hangi string olduğunu saklıyor ve sonuç yorumluyor; gerçek `ActivityResultLauncher` çağrısını kendisi yapmıyor. Bu tasarımın uygunu projenin diğer kısımlarına bağlıdır, ancak daha sıkı bir yapıya (örneğin request metodu içinde doğrudan launcher çağırma) geçilebilir.
- Kullanılmayan importlar kaldırılmalı.
- `shouldShowRequestPermissionRationale` çağrısında `activity` ve `currentPermission` boş veya yanlış olabilir; request edilmeden önce `currentPermission`'ın set edildiğinden emin olun.

