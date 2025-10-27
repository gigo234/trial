# Auto Link Opener - Aplikasi Android

Aplikasi Android untuk membuka link secara otomatis pada waktu yang dijadwalkan.

## Fitur

- ✅ Tambah link dengan jadwal waktu tertentu (jam dan menit)
- ✅ Aktifkan/nonaktifkan jadwal individual
- ✅ Hapus jadwal yang tidak diperlukan
- ✅ Link akan dibuka otomatis di browser pada waktu yang ditentukan
- ✅ Jadwal berulang setiap hari
- ✅ UI modern dengan Material Design 3 dan Jetpack Compose

## Cara Build APK

### Prasyarat
- Android Studio (versi terbaru)
- JDK 8 atau lebih tinggi
- Android SDK dengan API Level 34

### Langkah-langkah Build

1. **Buka project di Android Studio**
   ```bash
   # Buka folder AutoLinkOpener di Android Studio
   ```

2. **Sync Gradle**
   - Android Studio akan otomatis sync dependencies
   - Tunggu hingga proses selesai

3. **Build APK Release**
   
   **Opsi A: Melalui Android Studio**
   - Klik menu `Build` → `Build Bundle(s) / APK(s)` → `Build APK(s)`
   - APK akan tersimpan di `app/build/outputs/apk/release/`

   **Opsi B: Melalui Command Line**
   ```bash
   cd AutoLinkOpener
   ./gradlew assembleRelease
   ```
   
   APK akan tersimpan di:
   ```
   app/build/outputs/apk/release/app-release-unsigned.apk
   ```

4. **Sign APK (Opsional untuk distribusi)**
   - Untuk instalasi pribadi, APK unsigned sudah cukup
   - Untuk distribusi, perlu sign dengan keystore

### Instalasi APK

1. Transfer APK ke perangkat Android
2. Aktifkan "Install from Unknown Sources" di Settings
3. Tap file APK untuk install
4. Berikan permission yang diperlukan (Alarm, Notification)

## Cara Menggunakan Aplikasi

1. **Tambah Link Terjadwal**
   - Tap tombol `+` di pojok kanan bawah
   - Masukkan URL (contoh: https://google.com)
   - Masukkan jam (0-23) dan menit (0-59)
   - Tap "Tambah"

2. **Kelola Jadwal**
   - Toggle switch untuk aktifkan/nonaktifkan jadwal
   - Tap icon delete untuk hapus jadwal

3. **Link Otomatis Terbuka**
   - Pada waktu yang dijadwalkan, link akan otomatis terbuka di browser
   - Jadwal berulang setiap hari

## Teknologi yang Digunakan

- **Kotlin** - Bahasa pemrograman
- **Jetpack Compose** - UI Framework modern
- **Material Design 3** - Design system
- **AlarmManager** - Scheduling system
- **SharedPreferences + Gson** - Data persistence

## Struktur Project

```
AutoLinkOpener/
├── app/
│   ├── src/main/
│   │   ├── java/com/autolinkopener/
│   │   │   ├── MainActivity.kt          # Activity utama dengan UI
│   │   │   ├── AlarmReceiver.kt         # Receiver untuk alarm
│   │   │   └── ui/theme/                # Theme dan styling
│   │   ├── res/                         # Resources (layouts, values, icons)
│   │   └── AndroidManifest.xml          # Manifest file
│   └── build.gradle.kts                 # App-level build config
├── build.gradle.kts                     # Project-level build config
└── settings.gradle.kts                  # Settings
```

## Permissions

Aplikasi memerlukan permissions berikut:
- `INTERNET` - Untuk membuka link
- `SCHEDULE_EXACT_ALARM` - Untuk scheduling tepat waktu
- `POST_NOTIFICATIONS` - Untuk notifikasi (Android 13+)

## Troubleshooting

**Link tidak terbuka otomatis:**
- Pastikan jadwal dalam status "enabled" (toggle ON)
- Periksa permission alarm sudah diberikan
- Pastikan aplikasi tidak di-force stop atau dibatasi battery optimization

**Build error:**
- Pastikan Android SDK sudah terinstal
- Sync Gradle dan clean project: `Build` → `Clean Project`
- Invalidate caches: `File` → `Invalidate Caches / Restart`

## Lisensi

Project ini dibuat untuk keperluan pribadi.
