# LocationPermissionScreen.kt Dokümantasyonu

## Amaç
Kullanıcıdan konum izni istemek ve izin durumunu yönetmek için Compose tabanlı bir ekran sunar. Kullanıcı izin verdiğinde, konum işlemlerinin başlatılabileceği bir mesaj gösterir.

## Neden Bu Kodlar Kullanıldı?
- **LocalContext.current as ComponentActivity**: Android'in izin isteme API'si için aktivite referansı gerekir.
- **remember ve mutableStateOf**: Compose'da izin durumunu ve UI güncellemelerini yönetmek için state kullanılır.
- **PermissionHandler**: İzin sonucu yönetimini merkezi ve tekrar kullanılabilir yapmak için kullanılır.
- **ActivityResultLauncher**: Android'in modern izin isteme ve sonuç alma mekanizmasıdır.
- **Button ve Text**: Kullanıcıdan izin istemek ve sonucu göstermek için Compose bileşenleri kullanılır.
- **showLocationAction**: İzin verildiğinde ekranda farklı bir mesaj göstermek için kullanılır.

---

# CameraPermissionScreen.kt Dokümantasyonu

## Amaç
Kullanıcıdan kamera izni istemek ve izin verildiğinde kamerayı açmak için Compose tabanlı bir ekran sunar.

## Neden Bu Kodlar Kullanıldı?
- **cameraStatus by remember**: Kamera izin durumu state olarak tutulur, UI güncellenir.
- **PermissionHandler**: İzin sonucu yönetimini kolaylaştırır.
- **cameraLauncher**: Kamera ile fotoğraf çekmek için ActivityResultLauncher kullanılır.
- **launcher**: Kamera izni istemek için kullanılır. İzin verilirse kamera açılır.
- **showCameraAction**: İzin verildiyse kullanıcıya kamera açıldığı bilgisi verilir.

---

# GalleryPermissionScreen.kt Dokümantasyonu

## Amaç
Kullanıcıdan galeriye erişim izni istemek ve izin verildiğinde galeri açmak için Compose tabanlı bir ekran sunar.

## Neden Bu Kodlar Kullanıldı?
- **Android sürümüne göre izin seçimi**: Farklı Android sürümlerinde farklı izinler gereklidir (READ_MEDIA_IMAGES veya READ_EXTERNAL_STORAGE).
- **galleryLauncher**: Galeriden görsel seçmek için ActivityResultLauncher kullanılır.
- **permissionLauncher**: Galeri izni istemek için kullanılır. İzin verilirse galeri açılır.
- **showGalleryAction**: İzin verildiyse kullanıcıya galeri açıldığı bilgisi verilir.

---

# PermissionItem.kt Dokümantasyonu

## Amaç
İzin menüsünde gösterilecek izinlerin başlık, açıklama ve durumunu tutan veri sınıfıdır.

## Neden Bu Kodlar Kullanıldı?
- **data class PermissionItem**: Başlık, açıklama, izin durumu ve tıklama fonksiyonu gibi bilgileri tek bir veri yapısında toplamak için kullanılır.

---

# PermissionViewModel.kt Dokümantasyonu

## Amaç
Kamera, galeri ve konum izinlerinin durumunu yöneten ViewModel sınıfıdır. İzinlerin kontrolü ve güncellenmesi işlemlerini içerir.

## Neden Bu Kodlar Kullanıldı?
- **MutableStateFlow<PermissionStatus?>**: Her izin için ayrı state tutulur ve UI ile reaktif olarak paylaşılır.
- **checkAndReturn(permission: String)**: İzin durumunu kontrol eder ve state'i günceller.
- **updateStatus(permission: String, status: PermissionStatus?)**: İlgili iznin durumunu günceller.
- **checkCamera(), checkGallery(), checkLocation()**: İlgili izinlerin durumunu kontrol eder.

---

# PermissionMenuScreen.kt Dokümantasyonu

## Amaç
Kullanıcıya izinler menüsü sunar. Her izin için ayrı bir buton ve açıklama gösterir. Navigation ile ilgili izin ekranına yönlendirir.

## Neden Bu Kodlar Kullanıldı?
- **permissionItems**: Menüde gösterilecek izinler ve açıklamaları listeler.
- **Card ve Button**: Her izin için kart ve git butonu oluşturur.
- **onClick**: İlgili izin ekranına yönlendirir.

---

# PermissionHandler.kt Dokümantasyonu

## Amaç
İzin isteme ve izin sonucunu yönetmek için yardımcı bir sınıftır. İzin sonucu callback ile üst katmana iletilir.

## Neden Bu Kodlar Kullanıldı?
- **handleResult(isGranted: Boolean)**: İzin sonucunu değerlendirir ve uygun PermissionStatus ile üst katmana iletir.
- **request(permission: String)**: Hangi iznin istendiğini kaydeder.

