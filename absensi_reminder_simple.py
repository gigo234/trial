#!/usr/bin/env python3
"""
Aplikasi Pengingat Absensi (Versi Sederhana - Tanpa Dependencies)
Menampilkan notifikasi dan membuka link absensi setiap jam 0:55
"""

import time
import webbrowser
import subprocess
import platform
from datetime import datetime, timedelta

ABSENSI_URL = "https://absen.idolmartidolaku.com/index.php"

def show_notification(title, message):
    """Menampilkan notifikasi sistem"""
    system = platform.system()
    
    try:
        if system == "Linux":
            subprocess.run([
                'notify-send',
                title,
                message,
                '-u', 'critical',
                '-t', '10000'
            ], check=False)
        elif system == "Darwin":
            script = f'display notification "{message}" with title "{title}"'
            subprocess.run(['osascript', '-e', script], check=False)
        elif system == "Windows":
            script = f'''
            [void] [System.Reflection.Assembly]::LoadWithPartialName("System.Windows.Forms")
            $notification = New-Object System.Windows.Forms.NotifyIcon
            $notification.Icon = [System.Drawing.SystemIcons]::Information
            $notification.BalloonTipTitle = "{title}"
            $notification.BalloonTipText = "{message}"
            $notification.Visible = $True
            $notification.ShowBalloonTip(10000)
            Start-Sleep -Seconds 10
            $notification.Dispose()
            '''
            subprocess.run(['powershell', '-Command', script], check=False)
    except Exception as e:
        print(f"Error menampilkan notifikasi: {e}")

def open_absensi():
    """Fungsi yang dijalankan setiap jam 0:55"""
    current_time = datetime.now().strftime("%H:%M:%S")
    print(f"\n[{current_time}] 🔔 Waktunya absensi!")
    
    show_notification(
        "⏰ Pengingat Absensi",
        "Waktunya absensi! Browser akan dibuka otomatis."
    )
    
    try:
        webbrowser.open(ABSENSI_URL)
        print(f"✓ Browser dibuka: {ABSENSI_URL}")
    except Exception as e:
        print(f"✗ Error membuka browser: {e}")

def get_next_target_time():
    """Mendapatkan waktu target berikutnya (jam X:55)"""
    now = datetime.now()
    target = now.replace(minute=55, second=0, microsecond=0)
    
    if now.minute >= 55:
        target += timedelta(hours=1)
    
    return target

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
    
    try:
        while True:
            now = datetime.now()
            target = get_next_target_time()
            
            time_diff = (target - now).total_seconds()
            
            print(f"⏰ Waktu sekarang: {now.strftime('%H:%M:%S')}")
            print(f"📅 Target berikutnya: {target.strftime('%H:%M:%S')}")
            print(f"⏳ Menunggu {int(time_diff)} detik...\n")
            
            if time_diff > 60:
                time.sleep(60)
            elif time_diff > 0:
                time.sleep(time_diff)
            else:
                open_absensi()
                time.sleep(60)
                
    except KeyboardInterrupt:
        print("\n\n👋 Aplikasi dihentikan oleh user.")
        print("Terima kasih telah menggunakan Aplikasi Pengingat Absensi!\n")

if __name__ == "__main__":
    main()
