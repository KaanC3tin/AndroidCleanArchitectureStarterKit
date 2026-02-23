# PermissionItemRow.kt — Satır Satır Açıklama

Dosya: `app/src/main/java/com/karegraf/androidarchitecturestarterkit/presentation/permission/PermissionItemRow.kt`

Aşağıda dosyanın orijinal kodu ve her satır için kısa açıklamalar bulunmaktadır.

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

Satır satır açıklamalar (kod içindeki satır numaraları 1'den başlar):

1: package bildirimi.

3: Screen importu — uygulama içi rota tanımları için.

5: permissionItems sabit liste tanımı — uygulamanın izinler menüsünde gösterilecek öğelerin listesi.

7-10: Birinci öğe — Kamera izni için başlık, açıklama ve route.

11-16: İkinci öğe — Konum izni için başlık, açıklama ve route.

17-20: Üçüncü öğe — Galeri izni için başlık, açıklama ve route.

Bu liste UI tarafında menü oluşturmak için direkt kullanılabilir.

