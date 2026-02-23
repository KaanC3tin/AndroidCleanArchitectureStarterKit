# PermissionItem.kt — Satır Satır Açıklama

Dosya: `app/src/main/java/com/karegraf/androidarchitecturestarterkit/presentation/permission/PermissionItem.kt`

Aşağıda dosyanın orijinal kodu ve her satır için kısa açıklamalar bulunmaktadır.

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

Satır satır açıklamalar (kod içindeki satır numaraları 1'den başlar):

1: package bildirimi.

2-3: importlar — PermissionStatus enum/modeli ve uygulama içi Screen tanımı.

5-10: data class PermissionItem — izin listesi veya menu öğesi olarak kullanılacak basit DTO:
 - title: gösterilecek başlık metni.
 - description: kısa açıklama metni.
 - status: o iznin mevcut durumu (null veya PermissionStatus).
 - route: bu öğeye tıklandığında gitmesi gereken ekran (Screen tipinden).

Bu sınıf Presentation katmanında izinler listesini ve UI öğelerini oluşturmak için kullanılır.

