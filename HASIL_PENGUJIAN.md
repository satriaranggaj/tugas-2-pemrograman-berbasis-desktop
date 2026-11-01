# HASIL PENGUJIAN APLIKASI KASIR RESTORAN (Tugas Praktik 2)

| # | Skenario | Input | Hasil yang Diperharapkan | Hasil Aktual | Status |
|---|----------|-------|--------------------------|--------------|--------|
| 1 | Tampilan awal aplikasi | Jalankan MainGUI | Window terbuka, tab Pelanggan + Pemilik, daftar menu 8 item terlihat | Window terbuka, 2 tab, 8 menu ditampilkan | LULUS |
| 2 | Pemesanan makanan | Pilih Nasi Goreng, jumlah 2 | Item Nasi Goreng x2 muncul di tabel pesanan, subtotal Rp36.000 | Item muncul, subtotal Rp36.000 | LULUS |
| 3 | Pemesanan minuman | Pilih Es Teh, jumlah 3 | Item Es Teh x3 muncul, subtotal Rp18.000 | Item muncul, subtotal Rp18.000 | LULUS |
| 4 | Pesanan beberapa menu | Nasi Goreng 2 + Es Teh 3 + Jus Alpukat 1 | 3 item di tabel, subtotal keseluruhan Rp66.000 | 3 item, subtotal Rp66.000 | LULUS |
| 5 | Edit jumlah item | Pilih item, ubah jumlah menjadi 5 | Jumlah berubah, subtotal item diperbarui | Jumlah berubah, subtotal diperbarui | LULUS |
| 6 | Hapus item pesanan | Pilih item, klik Hapus, konfirmasi Ya | Item terhapus dari tabel | Item terhapus | LULUS |
| 7 | Hapus item dibatalkan | Klik Hapus, konfirmasi Tidak | Item tetap ada | Item tetap ada | LULUS |
| 8 | Pesanan kosong saat Hitung Total | Tidak ada item, klik Hitung Total | Pesan peringatan "Pesanan kosong" | Muncul dialog peringatan | LULUS |
| 9 | Pesanan kosong saat Cetak Struk | Tidak ada item, klik Cetak Struk | Pesan peringatan "Pesanan kosong" | Muncul dialog peringatan | LULUS |
| 10 | Validasi jumlah nol | Masukkan jumlah 0 | Pesan "Jumlah minimal 1" | Muncul dialog peringatan | LULUS |
| 11 | Validasi jumlah negatif | Masukkan jumlah -5 | Pesan "Jumlah minimal 1" | Muncul dialog peringatan | LULUS |
| 12 | Validasi jumlah bukan angka | Masukkan "abc" | Pesan "Jumlah harus berupa angka" | Muncul dialog error | LULUS |
| 13 | Validasi jumlah kosong (Batal) | Klik Batal pada dialog | Tidak ada perubahan | Tidak ada perubahan | LULUS |
| 14 | Diskon di bawah Rp100.000 | Total Rp99.000 | Diskon tidak aktif | Diskon tidak aktif | LULUS |
| 15 | Diskon tepat Rp100.000 | Total Rp100.000 | Diskon tidak aktif (harus > 100.000) | Diskon tidak aktif | LULUS |
| 16 | Diskon di atas Rp100.000 | Total Rp110.000 | Diskon 10% = Rp11.000 aktif | Diskon Rp11.000 aktif | LULUS |
| 17 | Promo di bawah Rp50.000 | Total Rp49.000 + minuman | Promo tidak aktif | Promo tidak aktif | LULUS |
| 18 | Promo tepat Rp50.000 | Total Rp50.000 + minuman | Promo tidak aktif (harus > 50.000) | Promo tidak aktif | LULUS |
| 19 | Promo di atas Rp50.000 + minuman | Total Rp60.000 + Es Teh 1 | Promo aktif, bonus 1 minuman termurah | Promo aktif, bonus Rp6.000 | LULUS |
| 20 | Promo tanpa minuman | Total Rp60.000, hanya makanan | Promo tidak aktif (tidak ada minuman) | Promo tidak aktif | LULUS |
| 21 | Diskon + Promo bersama | Total Rp120.000 + minuman | Diskon 10% + promo BOGO aktif bersama | Kedua fitur aktif | LULUS |
| 22 | Tambah menu baru | Nama: Es Kelapa, kategori: minuman, harga: 12000 | Menu baru ditambahkan ke daftar | Menu muncul di tabel | LULUS |
| 23 | Tambah menu nama kosong | Nama kosong | Pesan "Nama menu tidak boleh kosong" | Muncul dialog peringatan | LULUS |
| 24 | Tambah menu duplikat | Nama sudah ada | Pesan "Menu sudah ada" | Muncul dialog peringatan | LULUS |
| 25 | Tambah kategori invalid | Kategori "lainnya" | Tetap ke kategori lama (switch-case default) | Kategori tidak berubah | LULUS |
| 26 | Tambah harga nol | Harga 0 | Pesan "Harga harus lebih dari 0" | Muncul dialog peringatan | LULUS |
| 27 | Ubah menu (nama+harga+kategori) | Pilih menu, ubah semua field | Data menu berubah setelah konfirmasi Ya | Data berubah, tabel diperbarui | LULUS |
| 28 | Ubah menu dibatalkan | Konfirmasi Tidak | Data tidak berubah | Data tidak berubah | LULUS |
| 29 | Hapus menu | Pilih menu, konfirmasi Ya | Menu terhapus, nomor menu berikutnya turun | Menu terhapus | LULUS |
| 30 | Hapus menu dibatalkan | Konfirmasi Tidak | Menu tetap ada | Menu tetap ada | LULUS |
| 31 | Cetak struk lengkap | Ada pesanan, klik Cetak Struk | Struk menampilkan semua item + rincian biaya | Struk ditampilkan di area kanan | LULUS |
| 32 | Pajak 10% | Subtotal Rp100.000 | Pajak Rp10.000 | Pajak Rp10.000 | LULUS |
| 33 | Biaya pelayanan | Setiap transaksi | Selalu Rp20.000 | Selalu Rp20.000 | LULUS |
| 34 | Total bayar akhir | Subtotal - diskon - promo + pajak + pelayanan | Rumus sesuai | Rumus sesuai | LULUS |

**Ringkasan: 34/34 skenario LULUS**

## Detail perhitungan contoh (Skenario 16-19)

**Skenario 16: Diskon di atas Rp100.000**
- Ayam Bakar 3 = Rp84.000 + Es Teh 2 = Rp12.000 + Jus Alpukat 1 = Rp14.000
- Subtotal: Rp110.000
- Diskon 10%: Rp11.000 (karena > Rp100.000)
- Promo: >Rp50.000 dan ada minuman (Es Teh 2) → bonus Rp6.000 (Es Teh termurah)
- Pajak 10%: Rp11.000
- Biaya pelayanan: Rp20.000
- Total: 110.000 - 11.000 - 6.000 + 11.000 + 20.000 = **Rp124.000**

**Skenario 19: Promo di atas Rp50.000 + minuman**
- Subtotal Rp60.000, ada Es Teh 1 gelas
- Diskon: tidak aktif (tidak > Rp100.000)
- Promo: >Rp50.000 dan ada minuman ≥ 1 → bonus Rp6.000 (Es Teh termurah)
- Pajak: Rp6.000
- Biaya pelayanan: Rp20.000
- Total: 60.000 - 0 - 6.000 + 6.000 + 20.000 = **Rp80.000**
