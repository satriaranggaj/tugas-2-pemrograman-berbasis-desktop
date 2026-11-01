# Aplikasi Kasir Restoran Sederhana — Tugas Praktik 2

Tugas Praktik 2, mata kuliah **STSI4201 Pemrograman Berbasis Desktop** — Universitas Terbuka.
Versi **GUI (Java Swing)** dari aplikasi kasir restoran.

---

## Fitur

1. **Input menu restoran** — data menu makanan & minuman disimpan dalam **array**
   (`Menu[] daftarMenu`), minimal 4 makanan + 4 minuman.
2. **Pemesanan pelanggan** — pelanggan memilih menu dari tabel, mengisi jumlah,
   dan klik `Tambah Pesanan`. Pesanan **tidak terbatas** item. Bisa mengedit
   jumlah atau menghapus item yang sudah dipilih.
3. **Menghitung total biaya** — subtotal + pajak 10% + biaya pelayanan Rp20.000,
   ditambah diskon 10% (total > Rp100.000) dan promo beli 1 gratis 1 untuk
   kategori minuman (total > Rp50.000 dan ada minuman ≥ 1).
4. **Mencetak struk** — item, jumlah, harga per item, total per item, total biaya,
   pajak, biaya pelayanan, diskon, promo, dan total bayar.
5. **Manajemen menu (pemilik)** — tambah menu baru, ubah data menu (nama, harga,
   kategori), hapus menu. Semua perubahan memerlukan konfirmasi `Ya`/`Tidak`.

---

## Aturan Bisnis

| Aturan | Nilai |
|---|---|
| Total biaya keseluruhan | jumlah subtotal seluruh item |
| Diskon | 10% jika total > Rp100.000 |
| Promo | total > Rp50.000 **DAN** ada minuman ≥ 1 → beli 1 gratis 1 minuman termurah |
| Pajak | 10% dari total biaya keseluruhan |
| Biaya pelayanan | Rp20.000 |
| Rumus total bayar | `total − diskon − promo + pajak + biaya pelayanan` |

---

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

---

## Struktur Project

```
AppRestoranTugas2/
├── Menu.java          class menu: nama, harga, kategori + getter/setter
├── ItemPesanan.java   satu baris pesanan: menu + jumlah + subtotal
├── Pesanan.java       ARRAY pesanan pelanggan (tidak terbatas) + subtotal + item promo
├── Struk.java         perhitungan diskon/promo/pajak/biaya + generateStrukText()
├── MainGUI.java       GUI Swing: tab Menu Pelanggan + tab Kelola Menu (Pemilik)
├── Main.java          versi console (pendukung)
├── README.md          berkas ini
└── docs/
```

---

## Cara Menjalankan

### Tanpa NetBeans (CMD / PowerShell)

```bash
cd "C:\path\ke\AppRestoranTugas2"
javac *.java
java MainGUI
```

### NetBeans

1. **File → New Project → Java with Ant → Java Application**, isi nama `AppRestoranTugas2`,
   pilih folder proyek ini, **Finish**.
2. Hapus file `AppRestoranTugas2.java` yang dibuat otomatis.
3. Klik kanan **Source Packages → New → Other → Java Class → Class from Existing Sources… → Add Files…**,
   pilih 6 file `.java`, **Finish**.
4. Pastikan tidak ada baris `package …;` (semua kelas di *default package*).
5. Klik kanan `MainGUI.java` → **Run File**.

### Kebutuhan

- **JDK 8 atau lebih baru** (sudah diuji sampai JDK 25).
- Tanpa database, tanpa library eksternal.

---

## Perilaku Program

| Situasi | Hasil |
|---|---|
| Menu belum dipilih | Pesan peringatan "Pilih menu pada tabel terlebih dahulu" |
| Jumlah nol / negatif | Pesan "Jumlah minimal 1" |
| Jumlah bukan angka | Pesan "Jumlah harus berupa angka" |
| Batal dialog | Tidak ada perubahan |
| Pesanan kosong saat Hitung Total / Cetak Struk | Pesan peringatan "Pesanan kosong" |
| Konfirmasi ubah/hapus menu | `Ya` → perubahan disimpan, `Tidak` → dibatalkan |
| Nama menu kosong / duplikat | Pesan peringatan, input diminta ulang |
| Kategori tidak valid | Tetap ke kategori lama |
| Harga <= 0 atau bukan angka | Pesan peringatan / error |

---

## Catatan Akademik

- Pada Tugas 1 perulangan **dilarang**; pada Tugas 2 perulangan **wajib** dipakai
  (`for`, `for-each`, `while`, `do-while`) dan semuanya dipakai pada program ini.
- Struktur keputusan `if`, `if-else`, `if-else if`, nested `if`, dan `switch-case`
  juga wajib dipakai dan telah diimplementasikan di `MainGUI.java` dan `Struk.java`.
- Semua data menu dan data pesanan disimpan dalam **array** (bukan `ArrayList`),
  dengan mekanisme perbesar array otomatis saat kapasitas penuh.
- Promo beli 1 gratis 1 berlaku untuk **kategori minuman secara umum**, bukan hanya
  satu menu minuman tertentu.

---

## Lisensi

Dibuat untuk keperluan tugas akademik Universitas Terbuka (STSI4201).
