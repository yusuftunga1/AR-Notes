# AR Notes

> Durum: Proje geliştirme aşamasında, ilk AR not yerleştirme akışı çalışır durumda. Aşağıdaki TODO’lar tamamlandıkça güncellenecek.

## Özellikler
- Jetpack Compose UI, Material 3
- ARCore + SceneView (arsceneview) ile plane tarama ve not yerleştirme
- Kamera izin kontrolü ve plane tespit durumuna göre buton durumu
- Giriş ekranı (AR Notes’e Hoş Geldiniz), ayarlar paneli

## Roadmap / TODO
- [ ] AR not içeriğini düzenleme ve silme
- [ ] Not listesi/galeri ekranı (kaydedilen notları gösterme)
- [ ] Plane algılama geri bildirimi için UI (rehber/metin)
- [ ] İyileştirilmiş ikon paketi ve splash ekran
- [ ] Daha fazla test: farklı cihaz/ARCore sürümleri

## Kullanılan Teknolojiler
- Kotlin, Android SDK 34 (minSdk 24)
- Jetpack Compose, Material 3, Navigation
- CameraX (camera-core, camera2, lifecycle, view)
- ARCore 1.45.0, SceneView (arsceneview 2.0.3)
- Coil (resim yükleme), Accompanist Placeholder

## Bağımlılıklar (özet)
| Kütüphane | Sürüm |
| --- | --- |
| Compose BOM | 2024.02.00 |
| Material3 | BOM ile |
| Navigation-Compose | 2.7.7 |
| CameraX core/camera2/lifecycle/view | 1.3.4 |
| ARCore | 1.45.0 |
| SceneView arsceneview | 2.0.3 |
| Coil-Compose | 2.5.0 |

## Gereksinimler
- Android Studio Flamingo/sonrası
- Android SDK 34, minSdk 24
- ARCore destekli cihaz ve Google Play Hizmetleri (AR)

## Kurulum
```bash
# kök dizinde
./gradlew.bat :app:assembleDebug
```
APK `app/build/outputs/apk/debug/` altında oluşur.

## AR kullanımı
1) Uygulamayı aç, kamera izni ver.
2) Plane noktaları belirdikten sonra yüzeye dokun; "Not Ekle" butonu aktif olur ve not eklenir.
3) Kamerayı hareket ettirdiğinde not yerinde kalır.

## Navigasyon
- Ekran 1: Giriş/ayarlar
- Ekran 2: AR sahnesi (plane tarama + not ekleme)

## Test / Sorun giderme
- Plane görünmüyorsa: yeterli ışık, düz yüzey; kamerayı yavaşça hareket ettir.
- ARCore yok uyarısı: Cihazda Google Play Hizmetleri (AR) kurulu ve güncel olmalı.
- Kamera izni reddedildiyse: Ayarlar > Uygulama > İzinler’den kamerayı aç.

## İzinler
- Kamera izni: ARCore için gerekli.

