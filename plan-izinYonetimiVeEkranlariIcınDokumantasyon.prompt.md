## Plan: İzin Yönetimi ve Ekranları İçin Dokümantasyon

Bu doküman, projede yapılan izin yönetimi ve ilgili ekran değişikliklerinin özetini ve kullanım şeklini açıklar. Her dosya için yapılan değişiklikler ve kullanım amacı detaylı şekilde belirtilmiştir.

---

### 1. AndroidManifest.xml
- Gerekli tüm izinler eklendi: CAMERA, READ_EXTERNAL_STORAGE (Android 12 ve öncesi), READ_MEDIA_IMAGES (Android 13+), ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION.
- `WRITE_EXTERNAL_STORAGE` kaldırıldı (Android 10+ için gereksiz).
- `tools:targetApi="31"` ile uygulamanın yeni API'lere uyumlu çalışması sağlandı.
- Uyarılar: READ_EXTERNAL_STORAGE Android 13+ için deprecated, READ_MEDIA_IMAGES için Play Store politikalarına dikkat edilmeli.

---

### 2. CameraPermissionScreen.kt
- Kamera izni Compose ile yönetiliyor.
- İzin verildiğinde otomatik olarak kamera uygulaması açılır (`MediaStore.ACTION_IMAGE_CAPTURE`).
- Kullanıcıya izin durumu ekranda gösterilir.
- Hatalı veya eski API kullanımı kaldırıldı, Accompanist 0.36.0 ile uyumlu hale getirildi.

---

### 3. GalleryPermissionScreen.kt
- Galeri izni Compose ile yönetiliyor.
- Android 13 ve sonrası için `READ_MEDIA_IMAGES`, öncesi için `READ_EXTERNAL_STORAGE` kullanılır.
- İzin verildiğinde otomatik olarak galeri açılır (`Intent.ACTION_PICK`).
- Kullanıcıya izin durumu ekranda gösterilir.

---

### 4. LocationPermissionScreen.kt
- Konum izni Compose ile yönetiliyor.
- Hem `ACCESS_FINE_LOCATION` hem de `ACCESS_COARSE_LOCATION` manifestte mevcut.
- İzin verildiğinde kullanıcıya bilgi göstermek için LaunchedEffect ile tetikleme hazır (Toast/Snackbar eklenebilir).
- Kullanıcıya izin durumu ekranda gösterilir.

---

### 5. build.gradle.kts (app)
- `com.google.accompanist:accompanist-permissions:0.36.0` eklendi.
- Compose ve navigation için gerekli bağımlılıklar mevcut.

---

### 6. MainActivity.kt
- Uygulama açılışında navigation graph başlatılır.
- Tüm izin ekranlarına Compose navigation ile erişim sağlanır.

---

### 7. AppNavGraph ve Navigation
- Her izin ekranı navigation graph’a eklendi.
- Kullanıcı menüden ilgili izin ekranına yönlendirilebilir.

---

### 8. PermissionMenuScreen (Varsa)
- Kullanıcıya üç izin ekranına gitmek için butonlar sunar.
- Navigation ile yönlendirme yapılır.

---

### 9. Proje Teması ve UI
- Material3 ve Compose kullanıldı.
- Tüm ekranlar modern Compose standartlarına uygun.

---

### 10. Genel Notlar
- Her ekran kendi izin kontrolünü ve UI’sini yönetir.
- Kodlar Accompanist 0.36.0 ve Compose ile uyumlu.
- Manifest ve kodda gereksiz veya eski API kullanımı yoktur.
- Android 13+ ve öncesi için izinler doğru şekilde ayrıştırılmıştır.

---

### İlerleme ve Geliştirme İçin
- Her yeni izin veya ekran için aynı Compose ve navigation yapısı kullanılabilir.
- Manifestte sadece gerekli izinler tutulmalı, yeni Android sürümlerine göre güncellenmeli.
- Kullanıcıya bilgi göstermek için Snackbar veya Toast eklenebilir.
- Play Store politikalarına uygunluk için izinlerin kullanım amacı net olmalı.

---

Bu dokümandan ilerleyerek projedeki izin yönetimi ve ekranlarını kolayca geliştirebilir ve sürdürebilirsin.

