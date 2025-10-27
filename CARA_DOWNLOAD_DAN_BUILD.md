# 📱 Cara Download dan Build APK Auto Link Opener

## 📦 File yang Tersedia

File source code aplikasi sudah dikemas dalam format ZIP:
- **File**: `AutoLinkOpener.zip` (20 KB)
- **Lokasi**: `/vercel/sandbox/AutoLinkOpener.zip`

## 🔽 Cara Download

### Opsi 1: Download Langsung dari Terminal
Jika Anda memiliki akses ke terminal ini, jalankan:
```bash
# Copy file ke lokasi yang mudah diakses
cp /vercel/sandbox/AutoLinkOpener.zip ~/Downloads/
```

### Opsi 2: Download via Browser/File Manager
File tersimpan di: `/vercel/sandbox/AutoLinkOpener.zip`

## 🛠️ Cara Build APK

### Prasyarat
1. **Install Android Studio**
   - Download dari: https://developer.android.com/studio
   - Install dengan semua komponen default

2. **Install Java Development Kit (JDK)**
   - JDK 11 atau lebih tinggi
   - Biasanya sudah include dengan Android Studio

### Langkah-langkah Build

#### 1️⃣ Extract File ZIP
```bash
unzip AutoLinkOpener.zip
cd AutoLinkOpener
```

#### 2️⃣ Buka di Android Studio
- Buka Android Studio
- Pilih `File` → `Open`
- Pilih folder `AutoLinkOpener`
- Tunggu Gradle sync selesai (pertama kali akan download dependencies)

#### 3️⃣ Build APK

**Cara A: Melalui Menu Android Studio**
1. Klik menu `Build` → `Build Bundle(s) / APK(s)` → `Build APK(s)`
2. Tunggu proses build selesai
3. Klik notifikasi "locate" untuk membuka folder APK
4. APK tersimpan di: `app/build/outputs/apk/debug/app-debug.apk`

**Cara B: Melalui Terminal/Command Line**
```bash
# Di dalam folder AutoLinkOpener
./gradlew assembleDebug

# Untuk release version (unsigned)
./gradlew assembleRelease
```

APK akan tersimpan di:
- Debug: `app/build/outputs/apk/debug/app-debug.apk`
- Release: `app/build/outputs/apk/release/app-release-unsigned.apk`

## 📲 Cara Install APK di Android

### 1️⃣ Transfer APK ke HP Android
- Via USB cable
- Via email/messaging app
- Via cloud storage (Google Drive, Dropbox, dll)
- Via ADB: `adb install app-debug.apk`

### 2️⃣ Install di HP
1. Buka file APK di HP
2. Jika muncul peringatan "Install from Unknown Sources":
   - Tap "Settings"
   - Aktifkan "Allow from this source"
   - Kembali dan tap "Install"
3. Tap "Install"
4. Tap "Open" setelah instalasi selesai

## 🎯 Cara Menggunakan Aplikasi

### Menambah Link Terjadwal
1. Buka aplikasi "Auto Link Opener"
2. Tap tombol **+** (floating button di kanan bawah)
3. Masukkan:
   - **URL**: Link yang ingin dibuka (contoh: `https://google.com`)
   - **Jam**: 0-23 (format 24 jam)
   - **Menit**: 0-59
4. Tap "Tambah"

### Mengelola Jadwal
- **Aktifkan/Nonaktifkan**: Toggle switch di sebelah kanan
- **Hapus**: Tap icon tempat sampah (🗑️)

### Cara Kerja
- Link akan **otomatis terbuka di browser** pada waktu yang dijadwalkan
- Jadwal **berulang setiap hari**
- Pastikan aplikasi tidak di-force stop

## ⚙️ Troubleshooting

### Build Error
**Problem**: Gradle sync failed
**Solusi**:
```bash
# Clean project
./gradlew clean

# Atau di Android Studio:
# Build → Clean Project
# File → Invalidate Caches / Restart
```

**Problem**: SDK not found
**Solusi**:
- Buka Android Studio
- Tools → SDK Manager
- Install Android SDK Platform 34
- Install Android SDK Build-Tools

### Install Error
**Problem**: "App not installed"
**Solusi**:
- Uninstall versi lama jika ada
- Pastikan storage cukup
- Restart HP

### Link Tidak Terbuka Otomatis
**Problem**: Link tidak terbuka pada waktu yang dijadwalkan
**Solusi**:
1. Pastikan toggle jadwal dalam posisi ON (hijau)
2. Berikan permission alarm:
   - Settings → Apps → Auto Link Opener → Permissions
   - Aktifkan "Alarms & reminders"
3. Nonaktifkan battery optimization:
   - Settings → Battery → Battery optimization
   - Pilih "All apps"
   - Cari "Auto Link Opener"
   - Pilih "Don't optimize"

## 📋 Informasi Aplikasi

### Fitur
✅ Tambah multiple link dengan jadwal berbeda  
✅ Aktifkan/nonaktifkan jadwal individual  
✅ Hapus jadwal yang tidak diperlukan  
✅ Link otomatis terbuka di browser  
✅ Jadwal berulang setiap hari  
✅ UI modern dengan Material Design 3  

### Teknologi
- **Bahasa**: Kotlin
- **UI**: Jetpack Compose + Material Design 3
- **Scheduling**: AlarmManager
- **Storage**: SharedPreferences + Gson

### Permissions
- `INTERNET` - Untuk membuka link
- `SCHEDULE_EXACT_ALARM` - Untuk scheduling tepat waktu
- `POST_NOTIFICATIONS` - Untuk notifikasi (Android 13+)

## 📞 Support

Jika mengalami masalah:
1. Baca README.md di dalam project
2. Periksa troubleshooting di atas
3. Pastikan menggunakan Android 7.0 (API 24) atau lebih tinggi

---

**Selamat mencoba! 🚀**
