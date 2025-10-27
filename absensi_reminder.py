#!/usr/bin/env python3
"""
Aplikasi Pengingat Absensi
Menampilkan notifikasi dan membuka link absensi setiap jam 0:55
"""

import schedule
import time
import webbrowser
import subprocess
import platform
from datetime import datetime

ABSENSI_URL = "https://absen.idolmartidolaku.com/index.php"

def show_notification(title, message):
    """Menampilkan notifikasi sistem"""
    system = platform.system()
    
    try:
        if system == "Linux":
            # Menggunakan notify-send untuk Linux
            subprocess.run([
                'notify-send',
                title,
                message,
                '-u', 'critical',
                '-t', '10000'
            ])
        elif system == "Darwin":  # macOS
            # Menggunakan osascript untuk macOS
            script = f'display notification "{message}" with title "{title}"'
            subprocess.run(['osascript', '-e', script])
        elif system == "Windows":
            # Menggunakan PowerShell untuk Windows
            script = f'''
            Add-Type -AssemblyName System.Windows.Forms
            $notification = New-Object System.Windows.Forms.NotifyIcon
            $notification.Icon = [System.Drawing.SystemIcons]::Information
            $notification.BalloonTipTitle = "{title}"
            $notification.BalloonTipText = "{message}"
            $notification.Visible = $True
            $notification.ShowBalloonTip(10000)
            '''
            subprocess.run(['powershell', '-Command', script])
    except Exception as e:
        print(f"Error menampilkan notifikasi: {e}")

def open_absensi():
    """Fungsi yang dijalankan setiap jam 0:55"""
    current_time = datetime.now().strftime("%H:%M:%S")
    print(f"\n[{current_time}] Waktunya absensi!")
    
    # Tampilkan notifikasi
    show_notification(
        "⏰ Pengingat Absensi",
        "Waktunya absensi! Browser akan dibuka otomatis."
    )
    
    # Buka link di browser default
    try:
        webbrowser.open(ABSENSI_URL)
        print(f"✓ Browser dibuka: {ABSENSI_URL}")
    except Exception as e:
        print(f"✗ Error membuka browser: {e}")

def main():
    """Fungsi utama aplikasi"""
    print("=" * 60)
    print("🔔 APLIKASI PENGINGAT ABSENSI")
    print("=" * 60)
    print(f"Link Absensi: {ABSENSI_URL}")
    print(f"Jadwal: Setiap jam pada menit ke-55")
    print(f"Sistem: {platform.system()}")
    print("=" * 60)
    print("\n⏳ Aplikasi berjalan... (Tekan Ctrl+C untuk berhenti)\n")
    
    # Jadwalkan untuk setiap jam pada menit ke-55
    # Format: HH:55
    for hour in range(24):
        schedule_time = f"{hour:02d}:55"
        schedule.every().day.at(schedule_time).do(open_absensi)
        print(f"✓ Terjadwal: {schedule_time}")
    
    print("\n" + "=" * 60)
    print("Menunggu waktu berikutnya...")
    print("=" * 60 + "\n")
    
    # Loop utama
    try:
        while True:
            schedule.run_pending()
            time.sleep(30)  # Cek setiap 30 detik
    except KeyboardInterrupt:
        print("\n\n👋 Aplikasi dihentikan oleh user.")
        print("Terima kasih telah menggunakan Aplikasi Pengingat Absensi!\n")

if __name__ == "__main__":
    main()
