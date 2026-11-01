# Aplikasi Kasir Restoran Sederhana — Tugas Praktik 2

Tugas Praktik 2, mata kuliah **STSI4201 Pemrograman Berbasis Desktop** — Universitas Terbuka.
Versi **console (terminal)** dari aplikasi kasir restoran yang pada Tugas Praktik 1 masih
berupa console + GUI Swing.

---

## Fitur

1. **Input menu restoran** — data menu makanan & minuman disimpan dalam **array**
   (`Menu[] daftarMenu`), minimal 4 makanan + 4 minuman.
2. **Pemesanan** — pelanggan mengetik nama menu yang diinginkan, pesanan **tidak terbatas**,
   diakhiri dengan mengetik `selesai`. Nama menu di luar daftar → sistem meminta input kembali.
3. **Menghitung total biaya** — subtotal + pajak 10% + biaya pelayanan Rp20.000,
   ditambah diskon 10% (total > Rp100.000) dan promo beli 1 gratis 1 (total > Rp50.000).
4. **Mencetak struk** — item, jumlah, harga per item, total per item, total biaya,
   pajak, biaya pelayanan, diskon, promo, dan total bayar.
5. **Manajemen menu (pemilik)** — tambah menu baru, ubah harga, hapus menu.
   Semua perubahan memerlukan konfirmasi `Ya`/`Tidak`, dan input di luar pilihan
   selalu diminta ulang.

## Aturan Bisnis

| Aturan | Nilai |
|---|---|
| Total biaya keseluruhan | jumlah subtotal seluruh item |
| Diskon | 10% jika total > Rp100.000 |
| Promo | total > Rp50.000 **DAN** Es Teh minimal 2 → 1 gelas gratis (Rp6.000) |
| Pajak | 10% dari total biaya keseluruhan |
| Biaya pelayanan | Rp20.000 |
| Rumus total bayar | `total − diskon − promo + pajak + biaya pelayanan` |

## Daftar Menu Awal

| No | Nama Menu | Kategori | Harga |
|---:|---|---|---:|
| 1 | Nasi Goreng | MAKANAN | Rp18.000 |
| 2 | Mie Goreng | MAKANAN | Rp17.000 |
| 3 | Ayam Bakar | MAKANAN | Rp28.000 |
| 4 | Soto Ayam | MAKANAN | Rp15.000 |
| 5 | Es Teh | MINUMAN | Rp6.000 |
| 6 | Es Jeruk | MINUMAN | Rp8.000 |
| 7 | Kopi | MINUMAN | Rp10.000 |
| 8 | Jus Alpukat | MINUMAN | Rp14.000 |

## Struktur Project

```
AppRestoranTugas2/
├── Menu.java          class menu: nama, harga, kategori + constructor + getter/setter
├── ItemPesanan.java   satu baris pesanan: menu + jumlah + subtotal
├── Pesanan.java       ARRAY pesanan pelanggan (tidak terbatas) + subtotal + item promo
├── Struk.java         perhitungan diskon/promo/pajak/biaya + pencetakan struk
├── Main.java          menu utama, menu pelanggan, menu pemilik (kelola menu)
├── LAPORAN_TUGAS_2.md laporan: source code + penjelasan + skenario uji
├── README.md          berkas ini
└── docs/
```

## Cara Menjalankan

### Tanpa NetBeans (CMD / PowerShell)

```bash
cd "C:\path\ke\AppRestoranTugas2"
javac *.java
java Main
```

### NetBeans

1. **File → New Project → Java with Ant → Java Application**, isi nama `AppRestoranTugas2`,
   pilih folder proyek ini, **Finish**.
2. Hapus file `AppRestoranTugas2.java` yang dibuat otomatis.
3. Klik kanan **Source Packages → New → Other → Java Class → Class from Existing Sources… → Add Files…**,
   pilih 5 file `.java`, **Finish**.
4. Pastikan tidak ada baris `package …;` (semua kelas di *default package*).
5. Klik kanan `Main.java` → **Run File**.

### Kebutuhan

- **JDK 8 atau lebih baru** (sudah diuji sampai JDK 25).
- Tanpa database, tanpa library eksternal.

## Perilaku Program

| Situasi | Hasil |
|---|---|
| Nama menu di luar daftar | Pesan "tidak ada", input diminta ulang |
| Pilihan menu di luar rentang | Pesan "tidak valid", input diminta ulang |
| Konfirmasi ubah/hapus | `Ya` → perubahan dijalankan, `Tidak` → tidak ada perubahan |
| Konfirmasi menambah menu | `Ya` → input menu baru berikutnya, `Tidak` → kembali ke menu pengelolaan |
| Menu utama | `1` pelanggan, `2` pemilik, `3` keluar; navigasi selalu bisa kembali ke menu utama |

## Catatan Akademik

- Pada Tugas 1 perulangan **dilarang**; pada Tugas 2 perulangan **wajib** dipakai
  (`for`, `for-each`, `while`, `do-while`) dan semuanya dipakai pada program ini.
- Semua aturan perhitungan (diskon, pajak, biaya pelayanan, promo, struk) sama dengan
  Tugas 1 agar perbandingan kedua tugas mudah dilakukan.
- Data menu dan data pesanan hanya disimpan di **memory (array)** selama program berjalan.

## Lisensi

Dibuat untuk keperluan tugas akademik Universitas Terbuka (STSI4201).
