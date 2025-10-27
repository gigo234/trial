# 🔔 Aplikasi Pengingat Absensi

Aplikasi Python yang secara otomatis menampilkan notifikasi dan membuka link absensi setiap jam pada menit ke-55 (08:55, 09:55, 10:55, dst).

## 🚀 Fitur

- ⏰ Notifikasi otomatis setiap jam 0:55
- 🌐 Membuka link absensi di browser default
- 💻 Support Linux, macOS, dan Windows
- 🔄 Berjalan terus menerus di background
- ✨ Tanpa dependencies eksternal (versi simple)

## 📋 Persyaratan

- Python 3.6 atau lebih baru
- Untuk Linux: `notify-send` (biasanya sudah terinstall)

## ▶️ Cara Menjalankan

### Versi Simple (Tanpa Dependencies - RECOMMENDED)

```bash
python3 absensi_reminder_simple.py
```

### Versi dengan Schedule Library

1. Install dependencies terlebih dahulu:
```bash
pip install schedule
```

2. Jalankan aplikasi:
```bash
python3 absensi_reminder.py
```

## 🖥️ Output Aplikasi

Ketika aplikasi berjalan, Anda akan melihat:
```
============================================================
🔔 APLIKASI PENGINGAT ABSENSI
============================================================
Link Absensi: https://absen.idolmartidolaku.com/index.php
Jadwal: Setiap jam pada menit ke-55
Sistem: Linux
============================================================

⏳ Aplikasi berjalan... (Tekan Ctrl+C untuk berhenti)

⏰ Waktu sekarang: 14:23:45
📅 Target berikutnya: 14:55:00
⏳ Menunggu 1875 detik...
```

Saat jam 0:55, aplikasi akan:
1. Menampilkan notifikasi sistem
2. Membuka browser dengan link absensi
3. Menunggu jam berikutnya

## 🛑 Menghentikan Aplikasi

Tekan `Ctrl+C` di terminal untuk menghentikan aplikasi.

## 🔄 Menjalankan di Background

### Linux/macOS

Jalankan di background dengan nohup:
```bash
nohup python3 absensi_reminder_simple.py > absensi.log 2>&1 &
```

Cek apakah aplikasi berjalan:
```bash
ps aux | grep absensi_reminder
```

Menghentikan aplikasi:
```bash
pkill -f absensi_reminder_simple.py
```

Melihat log:
```bash
tail -f absensi.log
```

### Windows

**Cara 1: Menggunakan VBS Script**

Double-click file `start_absensi.vbs` untuk menjalankan aplikasi tanpa window terminal.

**Cara 2: Menggunakan Task Scheduler**

1. Buka Task Scheduler
2. Create Basic Task
3. Trigger: At startup atau At log on
4. Action: Start a program
5. Program: `python` atau `pythonw`
6. Arguments: `C:\path\to\absensi_reminder_simple.py`

## 🔧 Konfigurasi

Edit file Python untuk mengubah:

```python
# Ubah URL absensi
ABSENSI_URL = "https://absen.idolmartidolaku.com/index.php"

# Ubah menit target (default: 55)
target = now.replace(minute=55, second=0, microsecond=0)
```

## 📝 Catatan Penting

- ✅ Aplikasi harus tetap berjalan agar notifikasi muncul tepat waktu
- ✅ Pastikan koneksi internet aktif saat notifikasi muncul
- ✅ Browser default akan digunakan untuk membuka link
- ✅ Notifikasi akan muncul setiap jam pada menit ke-55 (00:55, 01:55, 02:55, dst)

## 🐛 Troubleshooting

### Notifikasi tidak muncul di Linux

Install libnotify:
```bash
sudo apt-get install libnotify-bin
```

Test notifikasi:
```bash
notify-send "Test" "Notifikasi berhasil!"
```

### Permission denied

Berikan permission execute:
```bash
chmod +x absensi_reminder_simple.py
```

### Browser tidak terbuka

Pastikan browser default sudah diset di sistem Anda.

### Python tidak ditemukan

Cek instalasi Python:
```bash
python3 --version
```

Jika belum terinstall, install Python 3:
- Ubuntu/Debian: `sudo apt-get install python3`
- macOS: `brew install python3`
- Windows: Download dari python.org

## 📂 File dalam Project

- `absensi_reminder_simple.py` - Versi tanpa dependencies (RECOMMENDED)
- `absensi_reminder.py` - Versi dengan schedule library
- `requirements.txt` - Dependencies untuk versi schedule
- `start_absensi.vbs` - Script untuk menjalankan di Windows tanpa terminal
- `README.md` - Dokumentasi ini

## 💡 Tips

1. **Auto-start saat boot (Linux):**
   Tambahkan ke crontab:
   ```bash
   crontab -e
   ```
   Tambahkan baris:
   ```
   @reboot /usr/bin/python3 /path/to/absensi_reminder_simple.py > /tmp/absensi.log 2>&1 &
   ```

2. **Auto-start saat login (macOS):**
   Buat file `~/Library/LaunchAgents/com.absensi.reminder.plist`

3. **Monitoring:**
   Gunakan `screen` atau `tmux` untuk menjalankan aplikasi di session terpisah

## 📄 Lisensi

Free to use

## 🤝 Kontribusi

Silakan buat issue atau pull request untuk perbaikan dan fitur baru.

---

**Dibuat dengan ❤️ untuk memudahkan absensi Anda**
