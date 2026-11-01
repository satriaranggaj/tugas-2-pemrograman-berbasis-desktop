# CHECKLIST PEMENUHAN INSTRUKSI TUGAS PRAKTIK 2

## A. Menu Restoran
| Ketentuan | Status | Bukti |
|-----------|--------|-------|
| Class Menu dengan nama, harga, kategori | Terpenuhi | Menu.java: baris 22-24 |
| Minimal 4 makanan dan 4 minuman | Terpenuhi | MainGUI.java isiMenuAwal(): 8 menu |
| Data menu menggunakan array | Terpenuhi | MainGUI.java: Menu[] daftarMenu = new Menu[20] |
| Ditampilkan kepada pelanggan | Terpenuhi | Tabel Daftar Menu di tab Pelanggan |

## B. Pemesanan Pelanggan
| Ketentuan | Status | Bukti |
|-----------|--------|-------|
| Pilih menu tersedia | Terpenuhi | Tabel menu klik/double-click |
| Masukkan jumlah pesanan | Terpenuhi | Dialog input jumlah |
| Pesan beberapa menu sekaligus | Terpenuhi | tambahItem dipanggil berkali-kali |
| Ubah jumlah item | Terpenuhi | editJumlahItem() + ubahJumlahItem() |
| Hapus item pesanan | Terpenuhi | hapusItemDariTabel() + hapusItem() |
| Selesaikan pesanan (tombol Selesai/Hitung) | Terpenuhi | Tombol Hitung Total |
| Pesanan kosong tidak diproses | Terpenuhi | isKosong() cek di hitungTotalBiayaGUI() |
| Input tidak valid ditangani | Terpenuhi | try-catch NumberFormatException, cek <=0 |
| Simpan nama, harga, jumlah, subtotal | Terpenuhi | ItemPesanan + ItemPesanan[] daftarItem |

## C. Perhitungan Transaksi
| Ketentuan | Status | Bukti |
|-----------|--------|-------|
| Subtotal per item | Terpenuhi | ItemPesanan.getSubtotal() |
| Total subtotal seluruh pesanan | Terpenuhi | Pesanan.getSubtotal() |
| Pajak 10% | Terpenuhi | Struk.java: pajak = subtotal * 10 / 100 |
| Biaya pelayanan Rp20.000 | Terpenuhi | Struk.java: BIAYA_PELAYANAN = 20000 |
| Diskon 10% jika > Rp100.000 | Terpenuhi | Struk.java: if (subtotal > BATAS_DISKON) |
| Promo BOGO minuman jika > Rp50.000 | Terpenuhi | Struk.java: nested if (subtotal > BATAS_PROMO dan ada minuman) |
| Tidak ada perhitungan ganda | Terpenuhi | hitung() dipanggil sekali per transaksi |
| Tampilkan subtotal, promo, diskon, pajak, pelayanan, total | Terpenuhi | generateStrukText() + cetak() |

## D. Struk Pembayaran
| Ketentuan | Status | Bukti |
|-----------|--------|-------|
| Nama menu | Terpenuhi | getBarisStruk() |
| Harga satuan | Terpenuhi | getBarisStruk() |
| Jumlah | Terpenuhi | getBarisStruk() |
| Subtotal per menu | Terpenuhi | getBarisStruk() |
| Subtotal keseluruhan | Terpenuhi | generateStrukText() |
| Promo dan diskon | Terpenuhi | generateStrukText() |
| Pajak | Terpenuhi | generateStrukText() |
| Biaya pelayanan | Terpenuhi | generateStrukText() |
| Total pembayaran | Terpenuhi | generateStrukText() |

## E. Fitur Pemilik Restoran
| Ketentuan | Status | Bukti |
|-----------|--------|-------|
| Lihat daftar menu | Terpenuhi | Tabel di tab Pemilik |
| Tambah menu baru | Terpenuhi | tambahMenuBaruGUI() |
| Ubah nama, harga, kategori | Terpenuhi | ubahMenuGUI() + setNama/setKategori/setHarga |
| Hapus menu | Terpenuhi | hapusMenuGUI() |
| Konfirmasi sebelum hapus | Terpenuhi | JOptionPane.YES_NO_OPTION |
| Pesan berhasil/gagal | Terpenuhi | JOptionPane informasi |
| Perubahan konsisten | Terpenuhi | muatTabelMenu dipanggil untuk kedua tabel |
| Tidak merusak pesanan berjalan | Terpenuhi | Array menu dan array pesanan terpisah |

## F. Penggunaan Array
| Ketentuan | Status | Bukti |
|-----------|--------|-------|
| Data menu array | Terpenuhi | Menu[] daftarMenu |
| Data pesanan array | Terpenuhi | ItemPesanan[] daftarItem |
| Array menampung jumlah yang dibutuhkan | Terpenuhi | perbesarArray() / perbesarArrayMenu() |
| Tidak error indeks | Terpenuhi | Cek jumlahMenu < daftarMenu.length |
| Penghapusan tidak merusak array | Terpenuhi | Pergeseran ke kiri dengan while |

## G. Struktur Class dan OOP
| Ketentuan | Status | Bukti |
|-----------|--------|-------|
| Class Menu | Terpenuhi | Menu.java |
| Class ItemPesanan | Terpenuhi | ItemPesanan.java |
| Class Pesanan | Terpenuhi | Pesanan.java |
| Class Struk | Terpenuhi | Struk.java |
| Class MainGUI | Terpenuhi | MainGUI.java |
| Tidak ada duplikasi perhitungan | Terpenuhi | Struk sebagai pusat perhitungan |
| Tanggung jawab tiap class tepat | Terpenuhi | Setiap class memiliki responsibility sendiri |

## H. Percabangan
| Struktur | Status | Lokasi |
|----------|--------|--------|
| if | Terpenuhi | Banyak tempat (validasi, cek kosong) |
| if-else | Terpenuhi | Struk.hitung() diskon, getInfoDiskon |
| if-else if | Terpenuhi | MainGUI.tambahMenuBaruGUI() validasi nama |
| Nested if | Terpenuhi | Struk.hitung() promo |
| switch-case | Terpenuhi | MainGUI.ubahMenuGUI() validasi kategori |

## I. Perulangan
| Struktur | Status | Lokasi |
|----------|--------|--------|
| for | Terpenuhi | Pesanan.getSubtotal(), muatTabelMenu |
| for-each | Terpenuhi | Struk.cetak(), Pesanan.getTotalItemPromo |
| while | Terpenuhi | Pesanan.hapusItem() pergeseran, editJumlahItem validasi |
| do-while | Terpenuhi | MainGUI.editJumlahItem() input jumlah |

## J. Validasi dan Penanganan Kesalahan
| Kondisi | Status | Bukti |
|---------|--------|-------|
| Menu belum dipilih | Terpenuhi | Cek row == -1 |
| Jumlah kosong | Terpenuhi | Dialog batal |
| Jumlah nol/negatif | Terpenuhi | Cek <= 0 |
| Harga tidak valid | Terpenuhi | try-catch NumberFormatException |
| Nama menu kosong | Terpenuhi | Cek isEmpty() |
| Kategori invalid | Terpenuhi | switch-case default |
| Pesanan kosong saat bayar | Terpenuhi | isKosong() |
| Menu tidak ditemukan (hapus/ubah) | Terpenuhi | Cek row == -1 |
| User batal dialog | Terpenuhi | Cek null pada showInputDialog |

## K. Laporan DOCX
| Elemen | Status |
|--------|--------|
| Cover | Terpenuhi |
| Identitas mahasiswa | Terpenuhi |
| Instruksi tugas | Terpenuhi |
| Pendahuluan | Terpenuhi |
| Tujuan aplikasi | Terpenuhi |
| Penjelasan struktur class | Terpenuhi |
| Penjelasan array | Terpenuhi |
| Penjelasan percabangan | Terpenuhi |
| Penjelasan perulangan | Terpenuhi |
| Penjelasan fitur pelanggan | Terpenuhi |
| Penjelasan fitur pemilik | Terpenuhi |
| Penjelasan perhitungan | Terpenuhi |
| Screenshot aplikasi (GUI) | Terpenuhi (9 gambar) |
| Kesimpulan | Terpenuhi |
| Referensi | Terpenuhi |

## L. Source Code Final
| File | Status |
|------|--------|
| Menu.java | Siap dijalankan |
| ItemPesanan.java | Siap dijalankan |
| Pesanan.java | Siap dijalankan |
| Struk.java | Siap dijalankan |
| MainGUI.java | Siap dijalankan |

**Kompilasi: BERHASIL (tidak ada error)**

## Ringkasan Status
| Aspek | Status |
|-------|--------|
| Aplikasi berjalan dengan baik | Terpenuhi |
| Implementasi sesuai instruksi tugas | Terpenuhi |
| Semua fitur berfungsi | Terpenuhi |
| Laporan konsisten dengan source code | Terpenuhi |
| Screenshot dari aplikasi yang berjalan | Terpenuhi |
| Tidak ada klaim fitur yang tidak diimplementasikan | Terpenuhi |

**STATUS KESELURUHAN: TERPENUHI**
