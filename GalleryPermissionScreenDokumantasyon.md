# Presentation Katmanı Kodları Dokümantasyonu

Bu doküman, `presentation/permission` klasörü altındaki dosyaların her birinin amacını, içerdiği kodların işlevini ve satır satır önemli noktalarını açıklamaktadır. Her dosya için ayrı başlıklar altında açıklamalar sunulmuştur.

---

## 1. LocationPermissionScreen.kt

**Amaç:**
Kullanıcıdan konum izni istemek ve izin durumunu yönetmek için Compose tabanlı bir ekran sunar. İzin verildiğinde, konum işlemlerinin başlatılabileceği bir mesaj gösterir.

**Önemli Kodlar ve Açıklamalar:**
- `val context = LocalContext.current as ComponentActivity`: Aktivite referansı alınır.
- `var locationStatus by remember { ... }`: İzin durumu state olarak tutulur.
- `PermissionHandler`: İzin sonucu yönetimi için kullanılır.
- `locationLauncher`: Android'in izin isteme API'si ile izin istenir.
- `Button(onClick = { ... })`: Kullanıcıdan konum izni istenir ve izin sonucu ekrana yansıtılır.
- `if (showLocationAction) { ... }`: İzin verildiyse kullanıcıya bilgi verilir.

---

## 2. CameraPermissionScreen.kt

**Amaç:**
Kullanıcıdan kamera izni istemek ve izin verildiğinde kamerayı açmak için Compose tabanlı bir ekran sunar.

**Önemli Kodlar ve Açıklamalar:**
- `var cameraStatus by remember { ... }`: Kamera izin durumu state olarak tutulur.
- `PermissionHandler`: İzin sonucu yönetimi için kullanılır.
- `cameraLauncher`: Kamera ile fotoğraf çekmek için kullanılır.
- `launcher`: Kamera izni istemek için kullanılır. İzin verilirse kamera açılır.
- `if (showCameraAction) { ... }`: İzin verildiyse kullanıcıya kamera açıldığı bilgisi verilir.

---

## 3. GalleryPermissionScreen.kt

**Amaç:**
Kullanıcıdan galeriye erişim izni istemek ve izin verildiğinde galeri açmak için Compose tabanlı bir ekran sunar.

**Önemli Kodlar ve Açıklamalar:**
- Android sürümüne göre doğru izin belirlenir (`READ_MEDIA_IMAGES` veya `READ_EXTERNAL_STORAGE`).
- `galleryLauncher`: Galeriden görsel seçmek için kullanılır.
- `permissionLauncher`: Galeri izni istemek için kullanılır. İzin verilirse galeri açılır.
- `if (showGalleryAction) { ... }`: İzin verildiyse kullanıcıya galeri açıldığı bilgisi verilir.

---

## 4. PermissionItem.kt

**Amaç:**
İzin menüsünde gösterilecek izinlerin başlık, açıklama ve durumunu tutan veri sınıfıdır.

**Önemli Kodlar ve Açıklamalar:**
- `data class PermissionItem(...)`: Başlık, açıklama, izin durumu ve tıklama fonksiyonu içerir.

---

## 5. PermissionViewModel.kt

**Amaç:**
Kamera, galeri ve konum izinlerinin durumunu yöneten ViewModel sınıfıdır. İzinlerin kontrolü ve güncellenmesi işlemlerini içerir.

**Önemli Kodlar ve Açıklamalar:**
- `MutableStateFlow<PermissionStatus?>`: Her izin için ayrı state tutulur.
- `checkAndReturn(permission: String)`: İzin durumunu kontrol eder ve state'i günceller.
- `updateStatus(permission: String, status: PermissionStatus?)`: İlgili iznin durumunu günceller.
- `checkCamera()`, `checkGallery()`, `checkLocation()`: İlgili izinlerin durumunu kontrol eder.

---

## 6. PermissionMenuScreen.kt

**Amaç:**
Kullanıcıya izinler menüsü sunar. Her izin için ayrı bir buton ve açıklama gösterir. Navigation ile ilgili izin ekranına yönlendirir.

**Önemli Kodlar ve Açıklamalar:**
- `permissionItems`: Menüde gösterilecek izinler ve açıklamaları.
- `Card` ve `Button`: Her izin için kart ve git butonu oluşturur.
- `onClick`: İlgili izin ekranına yönlendirir.

---

## 7. PermissionHandler.kt

**Amaç:**
İzin isteme ve izin sonucunu yönetmek için yardımcı bir sınıftır. İzin sonucu callback ile üst katmana iletilir.

**Önemli Kodlar ve Açıklamalar:**
- `handleResult(isGranted: Boolean)`: İzin sonucunu değerlendirir ve uygun PermissionStatus ile üst katmana iletir.
- `request(permission: String)`: Hangi iznin istendiğini kaydeder.

---

Her dosya, uygulamanın izin yönetimi ve kullanıcıya izin isteme süreçlerini modüler ve okunabilir şekilde yönetmek için tasarlanmıştır. Kodlar, Compose ve MVVM mimarisiyle uyumlu olarak yazılmıştır.
