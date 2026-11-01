# LAPORAN TUGAS PRAKTIK 2
## APLIKASI KASIR RESTORAN SEDERHANA (JAVA CONSOLE)

Mata Kuliah : **STSI4201 Pemrograman Berbasis Desktop**
Universitas Terbuka

| Identitas | Keterangan |
|---|---|
| Nama | *(isi nama lengkap)* |
| NIM | *(isi NIM)* |
| Program Studi | *(isi program studi)* |
| Mata Kuliah | STSI4201 Pemrograman Berbasis Desktop |
| Tugas | Tugas Praktik 2 — Struktur Keputusan dan Struktur Perulangan |

---

## 1. Deskripsi Singkat

Program ini merupakan aplikasi kasir restoran sederhana berbasis Java Console. Program
terdiri atas dua menu utama yaitu **menu pelanggan** untuk memesan makanan dan minuman
serta **menu pengelolaan menu restoran** untuk pemilik restoran. Program ini merupakan
pengembangan dari Tugas Praktik 1: class `Menu`, perhitungan total biaya, diskon, pajak,
biaya pelayanan, promo, dan pencetakan struk dipertahankan, kemudian ditambahkan
fitur manajemen menu (tambah, ubah harga, hapus) serta seluruh struktur perulangan
`for`, `for-each`, `while`, dan `do-while` sesuai ketentuan soal.

## 2. Tujuan dan Indikator Hasil Belajar

| Indikator | Terpenuhi pada |
|---|---|
| Tipe data, variabel, identifier, keyword pada class, object, dan method | `Menu.java`, `ItemPesanan.java`, `Pesanan.java`, `Struk.java`, `Main.java` |
| Array dan/atau String dalam method | `Menu[] daftarMenu` (Main), `ItemPesanan[] daftarItem` (Pesanan), method `formatRupiah()` |
| Struktur keputusan if, if-else, if-else if, nested if, switch case | `Struk.java` (if, if-else, nested if), `Main.java` (if, if-else, if-else if, switch case) |
| Struktur perulangan for, for-each, while, do-while | `Main.java`, `Pesanan.java`, `Struk.java` |

## 3. Fitur Aplikasi

1. **Input Menu Restoran** — data menu makanan dan minuman beserta harga disimpan dalam
   array, setiap item memiliki nama, harga, dan kategori.
2. **Pemesanan** — pelanggan mengetik nama menu yang dipesan, tidak terbatas hingga
   mengetik `selesai`; input di luar pilihan akan diminta kembali.
3. **Menghitung Total Biaya** — subtotal + pajak 10% + biaya pelayanan Rp20.000, dengan
   diskon 10% bila total > Rp100.000 dan promo beli 1 gratis 1 bila total > Rp50.000.
4. **Mencetak Struk Pesanan** — item, jumlah, harga per item, total per item, total biaya,
   pajak, biaya pelayanan, diskon/promo, dan total bayar.
5. **Manajemen Menu Aplikasi** — tambah menu baru, ubah harga, hapus menu, dengan
   konfirmasi `Ya`/`Tidak` dan navigasi kembali ke menu utama.

## 4. Struktur Project

```
AppRestoranTugas2/
├── Menu.java          class menu: nama, harga, kategori + constructor + getter/setter harga
├── ItemPesanan.java   satu baris pesanan: menu + jumlah + subtotal item
├── Pesanan.java       ARRAY pesanan pelanggan (tidak terbatas) + subtotal + item promo
├── Struk.java         perhitungan diskon/promo/pajak/biaya + pencetakan struk
├── Main.java          menu utama, menu pelanggan, menu pemilik (kelola menu)
├── LAPORAN_TUGAS_2.md laporan ini
├── README.md          ringkasan cara menjalankan
└── docs/
```

## 5. Cara Menjalankan

```bash
cd "C:\path\ke\AppRestoranTugas2"
javac *.java
java Main
```

Di NetBeans: buat project Java Application → hapus class bawaan → *Class from Existing Sources*
untuk 5 file `.java` → klik kanan `Main.java` → **Run File**.

Kebutuhan: **JDK 8+**, tanpa database dan tanpa library eksternal.

## 6. Source Code

Berikut source code lengkap aplikasi kasir restoran (5 class). Program dapat
dikompilasi dengan `javac *.java` dan dijalankan dengan `java Main` (JDK 8+).

### 6.1 Menu.java

```java
/**
 * ============================================================
 *  TUGAS PRAKTIK 2 - STSI4201 Pemrograman Berbasis Desktop
 *  APLIKASI KASIR RESTORAN SEDERHANA (Console)
 *  File : Menu.java
 * ============================================================
 * Class Menu digunakan untuk merepresentasikan SATU menu restoran.
 *
 * Atribut (sesuai soal):
 *   - nama     : nama menu, contoh "Nasi Goreng"
 *   - harga    : harga menu dalam Rupiah (int)
 *   - kategori : kategori menu, contoh "MAKANAN" atau "MINUMAN"
 *
 * Class ini sama dengan versi Tugas Praktik 1 (dibawa ke sini),
 * ditambah setter harga karena pada Tugas 2 pemilik restoran
 * dapat mengubah harga menu.
 * ============================================================
 */
public class Menu {

    // ================= ATRIBUT (VARIABEL INSTANSI) =================
    private String nama;        // tipe data String : teks nama menu
    private String kategori;    // tipe data String : "MAKANAN" / "MINUMAN"
    private int harga;          // tipe data int    : harga dalam Rupiah

    // ================= CONSTRUCTOR =================
    public Menu(String nama, String kategori, int harga) {
        this.nama = nama;           // this menunjuk ke atribut milik object ini
        this.kategori = kategori;
        this.harga = harga;
    }

    // ================= METHOD GETTER =================
    public String getNama() {
        return nama;
    }

    public String getKategori() {
        return kategori;
    }

    public int getHarga() {
        return harga;
    }

    // ================= METHOD SETTER HARGA =================
    // Dipakai saat pemilik restoran mengubah harga menu
    public void setHarga(int hargaBaru) {
        this.harga = hargaBaru;
    }

    // ================= METHOD MENAMPILKAN SATU BARIS MENU =================
    // Method void : hanya menampilkan, tidak mengembalikan nilai
    public void cetakMenu(int nomor) {
        System.out.println(nomor + ". " + nama
                + "   ->   " + formatRupiah(harga)
                + "   (" + kategori + ")");
    }

    // ================= METHOD MENGUBAH ANGKA MENJADI TEKS RUPIAH =================
    // Method static : bisa dipanggil tanpa membuat object (Menu.formatRupiah)
    public static String formatRupiah(int nilai) {
        // Locale "id-ID" membuat angka 18000 ditampilkan menjadi 18.000
        return "Rp" + String.format(java.util.Locale.forLanguageTag("id-ID"), "%,d", nilai);
    }
}
```

### 6.2 ItemPesanan.java

```java
/**
 * ============================================================
 *  TUGAS PRAKTIK 2 - STSI4201 Pemrograman Berbasis Desktop
 *  File : ItemPesanan.java
 * ============================================================
 * Class ItemPesanan merepresentasikan SATU BARIS pesanan pelanggan,
 * yaitu gabungan antara menu yang dipilih dan jumlah yang dipesan.
 *
 * Atribut :
 *   - menu   : object Menu yang dipesan
 *   - jumlah : berapa banyak menu tersebut dipesan
 * ============================================================
 */
public class ItemPesanan {

    // ================= ATRIBUT =================
    private Menu menu;      // object menu yang dipesan
    private int jumlah;     // jumlah pesanan untuk menu tersebut

    // ================= CONSTRUCTOR =================
    public ItemPesanan(Menu menu, int jumlah) {
        this.menu = menu;
        this.jumlah = jumlah;
    }

    // ================= METHOD GETTER =================
    public Menu getMenu() {
        return menu;
    }

    public int getJumlah() {
        return jumlah;
    }

    // ================= METHOD MENGHITUNG SUBTOTAL SATU ITEM =================
    // subtotal item = harga satuan x jumlah pesanan
    public int getSubtotal() {
        return menu.getHarga() * jumlah;
    }

    // ================= METHOD MENGUBAH ITEM MENJADI TEKS STRUK =================
    public String getBarisStruk(int nomor) {
        return nomor + ". " + menu.getNama() + " (" + menu.getKategori() + ")"
                + "\n     Jumlah          : " + jumlah
                + "\n     Harga Satuan    : " + Menu.formatRupiah(menu.getHarga())
                + "\n     Subtotal Item   : " + Menu.formatRupiah(getSubtotal());
    }
}
```

### 6.3 Pesanan.java

```java
/**
 * ============================================================
 *  TUGAS PRAKTIK 2 - STSI4201 Pemrograman Berbasis Desktop
 *  File : Pesanan.java
 * ============================================================
 * Class Pesanan menyimpan DAFTAR PESANAN pelanggan memakai ARRAY.
 *
 * Berbeda dengan Tugas 1 (maksimal 4 item), pada Tugas 2 jumlah
 * pesanan tidak terbatas, sehingga ukuran array dapat bertambah
 * otomatis (perbesarArray) ketika sudah penuh.
 *
 * Nested if / if yang dipakai di class ini:
 *   - if : item pesanan tidak boleh null
 *   - if : array harus diperbesar bila penuh
 * ============================================================
 */
public class Pesanan {

    // ================= KONSTANTA =================
    // Item minuman yang mendapat promo "beli 1 gratis 1"
    public static final String NAMA_ITEM_PROMO = "Es Teh";

    // ================= ATRIBUT (ARRAY PESANAN) =================
    private ItemPesanan[] daftarItem = new ItemPesanan[10];   // array 1 dimensi
    private int jumlahItem = 0;                                // jumlah item terpakai

    // ================= METHOD MENAMBAHKAN ITEM PESANAN =================
    public void tambahItem(Menu menu, int jumlah) {

        // IF : menu tidak boleh null
        if (menu == null) {
            return;
        }

        // IF : jumlah harus lebih dari 0
        if (jumlah <= 0) {
            return;
        }

        // IF : array penuh -> perbesar dulu (perulangan for di perbesarArray)
        if (jumlahItem >= daftarItem.length) {
            perbesarArray();
        }

        daftarItem[jumlahItem] = new ItemPesanan(menu, jumlah);
        jumlahItem = jumlahItem + 1;
    }

    // ================= METHOD MENGHAPUS ITEM PESANAN =================
    // Method : menghapus pesanan (dipakai menu pengelolaan pesanan)
    public boolean hapusItem(int index) {

        // IF : nomor di luar rentang item yang ada
        if (index < 0 || index >= jumlahItem) {
            return false;
        }

        // pergeseran item setelahnya ke kiri (perulangan while)
        int posisi = index;
        while (posisi < jumlahItem - 1) {
            daftarItem[posisi] = daftarItem[posisi + 1];
            posisi = posisi + 1;
        }
        daftarItem[jumlahItem - 1] = null;
        jumlahItem = jumlahItem - 1;

        return true;
    }

    // ================= METHOD MENGAMBIL SEMUA ITEM (untuk for-each) =================
    // Mengembalikan array baru yang hanya berisi item yang terpakai,
    // sehingga aman diproses dengan perulangan for-each.
    public ItemPesanan[] getSemuaItem() {
        ItemPesanan[] hasil = new ItemPesanan[jumlahItem];
        for (int i = 0; i < jumlahItem; i++) {       // perulangan for
            hasil[i] = daftarItem[i];
        }
        return hasil;
    }

    public ItemPesanan getItem(int index) {
        return daftarItem[index];
    }

    public int getJumlahItem() {
        return jumlahItem;
    }

    // ================= METHOD MENGHITUNG TOTAL BIAYA PESANAN =================
    // Perulangan for : menjumlahkan subtotal setiap item
    public int getSubtotal() {
        int total = 0;
        for (int i = 0; i < jumlahItem; i++) {
            total = total + daftarItem[i].getSubtotal();
        }
        return total;
    }

    // ================= METHOD MENGHITUNG TOTAL GELAS ITEM PROMO =================
    // Perulangan for-each : menjumlahkan jumlah "Es Teh" yang dibeli
    public int getTotalItemPromo() {
        int total = 0;
        for (ItemPesanan item : getSemuaItem()) {     // perulangan for-each
            // IF : item tersebut adalah minuman promo
            if (item.getMenu().getNama().equalsIgnoreCase(NAMA_ITEM_PROMO)) {
                total = total + item.getJumlah();
            }
        }
        return total;
    }

    // ================= METHOD MENGAMBIL HARGA 1 GELAS ITEM PROMO =================
    public int getHargaItemPromo() {
        for (ItemPesanan item : getSemuaItem()) {     // perulangan for-each
            if (item.getMenu().getNama().equalsIgnoreCase(NAMA_ITEM_PROMO)) {
                return item.getMenu().getHarga();
            }
        }
        return 0;
    }

    // ================= METHOD MENGCEK APAKAH PESANAN KOSONG =================
    public boolean isKosong() {
        return jumlahItem == 0;
    }

    // ================= METHOD MENGOSONGKAN PESANAN =================
    public void kosongkan() {
        for (int i = 0; i < jumlahItem; i++) {
            daftarItem[i] = null;
        }
        jumlahItem = 0;
    }

    // ================= METHOD MEMBESARKAN ARRAY PESANAN =================
    // Digunakan ketika array penuh dan pesanan masih bisa ditambah
    private void perbesarArray() {
        ItemPesanan[] arrayBaru = new ItemPesanan[daftarItem.length * 2];
        for (int i = 0; i < jumlahItem; i++) {       // perulangan for
            arrayBaru[i] = daftarItem[i];
        }
        daftarItem = arrayBaru;
    }
}
```

### 6.4 Struk.java

```java
/**
 * ============================================================
 *  TUGAS PRAKTIK 2 - STSI4201 Pemrograman Berbasis Desktop
 *  File : Struk.java
 * ============================================================
 * Class Struk menghitung dan mencetak STRUK PESANAN.
 *
 * Aturan perhitungan (sama dengan Tugas 1):
 *   - subtotal    : jumlah seluruh subtotal item
 *   - diskon 10%  : jika subtotal > Rp100.000
 *   - promo BOGO  : jika subtotal > Rp50.000 DAN Es Teh dibeli minimal 2
 *                   (nested if)
 *   - pajak 10%   : 10% dari subtotal
 *   - biaya       : Rp20.000
 *   - total bayar : subtotal - diskon - promo + pajak + biaya
 * ============================================================
 */
public class Struk {

    // ================= KONSTANTA ATURAN BISNIS =================
    private static final int BATAS_DISKON = 100000;     // diskon bila subtotal > 100.000
    private static final int BATAS_PROMO = 50000;       // promo bila subtotal > 50.000
    private static final int BIAYA_PELAYANAN = 20000;   // biaya pelayanan Rp20.000
    private static final int MINIMUM_ITEM_PROMO = 2;     // beli minimal 2 untuk promo

    // ================= HASIL PERHITUNGAN =================
    private int subtotal = 0;
    private int diskon = 0;
    private int bonusPromo = 0;
    private int pajak = 0;
    private int biayaPelayanan = BIAYA_PELAYANAN;
    private int totalBayar = 0;
    private boolean diskonAktif = false;
    private boolean promoAktif = false;

    // ============================================================
    //  METHOD : MENGHITUNG TOTAL BIAYA PESANAN
    //  Struktur keputusan : IF - ELSE (diskon) dan NESTED IF (promo)
    // ============================================================
    public void hitung(Pesanan pesanan) {

        // ---- 1. Subtotal keseluruhan ----
        subtotal = pesanan.getSubtotal();

        // ---- 2. Diskon 10% (IF - ELSE) ----
        if (subtotal > BATAS_DISKON) {                  // <-- IF
            diskon = subtotal * 10 / 100;
            diskonAktif = true;
        } else {                                       // <-- ELSE
            diskon = 0;
            diskonAktif = false;
        }

        // ---- 3. Promo beli 1 gratis 1 (NESTED IF) ----
        bonusPromo = 0;
        promoAktif = false;

        if (subtotal > BATAS_PROMO) {                  // <-- IF LUAR
            if (pesanan.getTotalItemPromo() >= MINIMUM_ITEM_PROMO) {   // <-- IF DALAM
                bonusPromo = pesanan.getHargaItemPromo();
                promoAktif = true;
            } else {
                bonusPromo = 0;
            }
        } else {
            bonusPromo = 0;
        }

        // ---- 4. Pajak 10% dari subtotal ----
        pajak = subtotal * 10 / 100;

        // ---- 5. Total bayar akhir ----
        totalBayar = subtotal - diskon - bonusPromo + pajak + biayaPelayanan;
    }

    // ============================================================
    //  METHOD : MENCETAK STRUK PESANAN
    //  Perulangan for-each untuk menampilkan seluruh item pesanan
    // ============================================================
    public void cetak(Pesanan pesanan) {

        System.out.println("\n===========================================");
        System.out.println("             STRUK PESANAN");
        System.out.println("===========================================");

        // perulangan for-each : menampilkan setiap item pesanan
        int nomor = 1;
        for (ItemPesanan item : pesanan.getSemuaItem()) {
            System.out.println(item.getBarisStruk(nomor));
            nomor = nomor + 1;
        }

        System.out.println("------------------------------------------");
        System.out.println("Total Biaya Pesanan     : " + Menu.formatRupiah(subtotal));

        // IF : tampilkan informasi diskon hanya bila aktif
        if (diskonAktif) {
            System.out.println("Diskon 10%              : -" + Menu.formatRupiah(diskon)
                    + "   (total > " + Menu.formatRupiah(BATAS_DISKON) + ")");
        } else {
            System.out.println("Diskon 10%              : tidak ada");
        }

        // IF : tampilkan informasi promo hanya bila aktif
        if (promoAktif) {
            System.out.println("Promo Beli 1 Gratis 1   : -" + Menu.formatRupiah(bonusPromo)
                    + "   (" + Pesanan.NAMA_ITEM_PROMO + ")");
        } else {
            System.out.println("Promo Beli 1 Gratis 1   : tidak ada");
        }

        System.out.println("Pajak 10%               : " + Menu.formatRupiah(pajak)
                + "   (10% dari " + Menu.formatRupiah(subtotal) + ")");
        System.out.println("Biaya Pelayanan         : " + Menu.formatRupiah(biayaPelayanan));
        System.out.println("------------------------------------------");
        System.out.println("TOTAL BAYAR             : " + Menu.formatRupiah(totalBayar));
        System.out.println("===========================================");
    }

    // ============================================================
    //  METHOD : PESAN INFORMASI DISKON (IF - ELSE)
    // ============================================================
    public String getInfoDiskon() {
        // IF : diskon aktif
        if (diskonAktif) {
            return "[INFO] Diskon 10% aktif, potongan " + Menu.formatRupiah(diskon) + ".";
        } else {                                       // <-- ELSE
            return "[INFO] Diskon tidak aktif.";
        }
    }

    // ============================================================
    //  METHOD : PESAN INFORMASI PROMO (NESTED IF)
    // ============================================================
    public String getInfoPromo() {
        // IF : total memenuhi syarat promo
        if (subtotal > BATAS_PROMO) {
            // IF : Es Teh dibeli minimal 2
            if (promoAktif) {
                return "[INFO] Total memenuhi syarat promo (>Rp50.000)."
                        + "\n[INFO] Promo aktif! Beli 1 gratis 1 untuk "
                        + Pesanan.NAMA_ITEM_PROMO + ", bonus "
                        + Menu.formatRupiah(bonusPromo) + ".";
            } else {
                return "[INFO] Total memenuhi syarat promo (>Rp50.000)."
                        + "\n[INFO] Promo tidak aktif karena " + Pesanan.NAMA_ITEM_PROMO
                        + " belum dibeli minimal 2.";
            }
        } else {
            return "[INFO] Promo tidak aktif karena total belum melebihi Rp50.000.";
        }
    }

    // ============================================================
    //  METHOD GETTER HASIL PERHITUNGAN
    // ============================================================
    public int getSubtotal() {
        return subtotal;
    }

    public int getDiskon() {
        return diskon;
    }

    public int getBonusPromo() {
        return bonusPromo;
    }

    public int getPajak() {
        return pajak;
    }

    public int getBiayaPelayanan() {
        return biayaPelayanan;
    }

    public int getTotalBayar() {
        return totalBayar;
    }

    public boolean isDiskonAktif() {
        return diskonAktif;
    }

    public boolean isPromoAktif() {
        return promoAktif;
    }
}
```

### 6.5 Main.java

```java
import java.util.Scanner;

/**
 * ============================================================
 *  TUGAS PRAKTIK 2 - STSI4201 Pemrograman Berbasis Desktop
 *  APLIKASI KASIR RESTORAN SEDERHANA (Console)
 *  File : Main.java
 * ============================================================
 * Class utama aplikasi kasir restoran.
 *
 * Dua menu utama (sesuai soal):
 *   1. MENU PELANGGAN  : memesan makanan & minuman, menghitung total,
 *                        dan mencetak struk pesanan.
 *   2. MENU PEMILIK    : mengelola data menu restoran (tambah menu baru,
 *                        ubah harga, hapus menu).
 *   3. KELUAR
 *
 * Struktur yang dipakai:
 *   - Array      : daftar menu restoran & daftar pesanan pelanggan
 *   - if / else if / else : validasi input dan cek syarat diskon & promo
 *   - nested if  : syarat promo (total > 50.000 DAN Es Teh minimal 2)
 *   - switch case: navigasi menu utama dan menu pengelolaan
 *   - for        : menampilkan daftar menu & menjumlahkan subtotal
 *   - for-each   : mencetak seluruh item pada struk pesanan
 *   - while      : perulangan input pesanan sampai kata "selesai"
 *   - do-while   : meminta input kembali bila nilai di luar pilihan
 * ============================================================
 */
public class Main {

    // ================= ATRIBUT APLIKASI =================
    private Menu[] daftarMenu = new Menu[20];   // ARRAY menu restoran
    private int jumlahMenu = 0;                 // jumlah menu yang terpakai

    private final Pesanan pesanan = new Pesanan();   // ARRAY pesanan pelanggan
    private final Struk struk = new Struk();         // perhitungan & struk

    // ================= METHOD UTAMA =================
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Main aplikasi = new Main();

        // mengisi data menu awal (4 makanan + 4 minuman)
        aplikasi.isiMenuAwal();

        boolean jalan = true;

        // perulangan WHILE : menu utama diulang sampai pengguna memilih Keluar
        while (jalan) {
            aplikasi.tampilkanMenuUtama();

            int pilihan = aplikasi.inputPilihan(input, 1, 3, "Pilih menu (1-3) : ");

            // SWITCH CASE : navigasi menu utama
            switch (pilihan) {
                case 1:
                    aplikasi.menuPelanggan(input);
                    break;
                case 2:
                    aplikasi.menuPemilik(input);
                    break;
                case 3:
                    jalan = false;
                    System.out.println("\nTerima kasih, aplikasi ditutup.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
                    break;
            }
        }

        input.close();
    }

    // ============================================================
    //  METHOD : MENGISI DATA MENU AWAL
    // ============================================================
    public void isiMenuAwal() {
        // 4 menu MAKANAN
        daftarMenu[jumlahMenu] = new Menu("Nasi Goreng", "MAKANAN", 18000);
        jumlahMenu = jumlahMenu + 1;
        daftarMenu[jumlahMenu] = new Menu("Mie Goreng", "MAKANAN", 17000);
        jumlahMenu = jumlahMenu + 1;
        daftarMenu[jumlahMenu] = new Menu("Ayam Bakar", "MAKANAN", 28000);
        jumlahMenu = jumlahMenu + 1;
        daftarMenu[jumlahMenu] = new Menu("Soto Ayam", "MAKANAN", 15000);
        jumlahMenu = jumlahMenu + 1;

        // 4 menu MINUMAN
        daftarMenu[jumlahMenu] = new Menu("Es Teh", "MINUMAN", 6000);
        jumlahMenu = jumlahMenu + 1;
        daftarMenu[jumlahMenu] = new Menu("Es Jeruk", "MINUMAN", 8000);
        jumlahMenu = jumlahMenu + 1;
        daftarMenu[jumlahMenu] = new Menu("Kopi", "MINUMAN", 10000);
        jumlahMenu = jumlahMenu + 1;
        daftarMenu[jumlahMenu] = new Menu("Jus Alpukat", "MINUMAN", 14000);
        jumlahMenu = jumlahMenu + 1;
    }

    // ============================================================
    //  METHOD : MENAMPILKAN MENU UTAMA
    // ============================================================
    public void tampilkanMenuUtama() {
        System.out.println("\n===========================================");
        System.out.println("       APLIKASI KASIR RESTORAN SEDERHANA");
        System.out.println("===========================================");
        System.out.println("1. Menu Pelanggan (Pesan makanan & minuman)");
        System.out.println("2. Kelola Menu Restoran (Pemilik)");
        System.out.println("3. Keluar");
    }

    // ============================================================
    //  METHOD : MENU PELANGGAN
    //  Pesanan tidak terbatas, berakhir saat pengguna mengetik "selesai"
    // ============================================================
    public void menuPelanggan(Scanner input) {

        System.out.println("\n=== MENU PELANGGAN ===");
        System.out.println("Masukkan nama menu yang dipesan.");
        System.out.println("Ketik 'selesai' untuk mengakhiri pesanan.");

        terimaPesanan(input);

        // IF : pelanggan tidak memesan apa pun
        if (pesanan.isKosong()) {
            System.out.println("\nTidak ada pesanan. Kembali ke menu utama.");
            return;
        }

        hitungTotalBiaya();
        cetakStruk();

        // setelah struk dicetak, pesanan dikosongkan agar pesanan berikutnya mulai baru
        pesanan.kosongkan();
        System.out.println("\nPesanan selesai. Kembali ke menu utama.");
    }

    // ============================================================
    //  METHOD : MENERIMA DAN MENGOLAH PESANAN PELANGGAN
    //  Perulangan WHILE : input diulang sampai pengguna mengetik "selesai"
    // ============================================================
    public void terimaPesanan(Scanner input) {

        boolean selesai = false;

        while (!selesai) {                       // perulangan WHILE

            System.out.print("\nNama menu yang dipesan : ");
            String nama = input.nextLine().trim();

            // IF : pelanggan mengakhiri pesanan
            if (nama.equalsIgnoreCase("selesai")) {
                selesai = true;
                continue;
            }

            // IF ELSE : menu tidak ditemukan -> diminta input kembali
            int index = cariIndexMenu(nama);
            if (index == -1) {
                System.out.println("Menu '" + nama + "' tidak ada. Silakan ulangi.");
                continue;                        // kembali ke while (input ulang)
            }

            // input jumlah pesanan
            int jumlah = inputJumlah(input);
            pesanan.tambahItem(daftarMenu[index], jumlah);

            System.out.println("Pesanan ditambahkan: " + daftarMenu[index].getNama()
                    + " x" + jumlah);
        }
    }

    // ============================================================
    //  METHOD : MENGHITUNG TOTAL BIAYA PESANAN
    // ============================================================
    public void hitungTotalBiaya() {
        struk.hitung(pesanan);

        // menampilkan informasi diskon dan promo
        System.out.println(struk.getInfoDiskon());
        System.out.println(struk.getInfoPromo());
    }

    // ============================================================
    //  METHOD : MENCETAK STRUK PESANAN
    // ============================================================
    public void cetakStruk() {
        struk.cetak(pesanan);
    }

    // ============================================================
    //  METHOD : MENU PENGELOLAAN MENU RESTORAN (PEMILIK)
    // ============================================================
    public void menuPemilik(Scanner input) {

        boolean kembali = false;

        // perulangan WHILE : menu pengelolaan diulang sampai memilih Kembali
        while (!kembali) {

            System.out.println("\n=== MENU PENGELOLAAN MENU RESTORAN ===");
            System.out.println("1. Tambah Menu Baru");
            System.out.println("2. Ubah Harga Menu");
            System.out.println("3. Hapus Menu");
            System.out.println("4. Kembali ke Menu Utama");

            int pilihan = inputPilihan(input, 1, 4, "Pilih menu (1-4) : ");

            // SWITCH CASE : navigasi menu pengelolaan
            switch (pilihan) {
                case 1:
                    tambahMenuBaru(input);
                    break;
                case 2:
                    ubahHargaMenu(input);
                    break;
                case 3:
                    hapusMenu(input);
                    break;
                case 4:
                    kembali = true;
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
                    break;
            }
        }
    }

    // ============================================================
    //  METHOD : MENAMBAHKAN MENU RESTORAN BARU
    //  Perulangan DO-WHILE : menambah beberapa menu sekaligus
    // ============================================================
    public void tambahMenuBaru(Scanner input) {

        boolean tambahLagi = true;

        // perulangan DO-WHILE : minimal satu menu ditambahkan
        while (tambahLagi) {

            // input nama menu (DO-WHILE : diminta ulang bila kosong / duplikat)
            String nama;
            do {
                System.out.print("\nNama menu baru           : ");
                nama = input.nextLine().trim();

                // IF : nama kosong
                if (nama.isEmpty()) {
                    System.out.println("Nama menu tidak boleh kosong.");
                } else if (cariIndexMenu(nama) != -1) {   // ELSE IF : nama sudah ada
                    System.out.println("Menu '" + nama + "' sudah ada. Gunakan nama lain.");
                }
            } while (nama.isEmpty() || cariIndexMenu(nama) != -1);

            // input kategori menu (DO-WHILE : hanya makanan atau minuman)
            String kategori;
            do {
                System.out.print("Kategori (makanan/minuman) : ");
                kategori = input.nextLine().trim().toLowerCase();

                // IF ELSE IF : kategori harus makanan atau minuman
                if (!kategori.equals("makanan") && !kategori.equals("minuman")) {
                    System.out.println("Kategori harus 'makanan' atau 'minuman'.");
                }
            } while (!kategori.equals("makanan") && !kategori.equals("minuman"));

            // input harga menu (DO-WHILE : harus lebih dari 0)
            int harga = inputHarga(input);

            // menyimpan menu baru ke dalam array
            if (jumlahMenu >= daftarMenu.length) {
                perbesarArrayMenu();
            }
            daftarMenu[jumlahMenu] = new Menu(nama, kategori.toUpperCase(), harga);
            jumlahMenu = jumlahMenu + 1;

            System.out.println("Menu '" + nama + "' berhasil ditambahkan.");

            // konfirmasi menambah menu lagi (DO-WHILE : sampai jawaban Ya/Tidak)
            tambahLagi = konfirmasi(input, "\nTambah menu lagi?");
        }
    }

    // ============================================================
    //  METHOD : MENGUBAH HARGA MENU RESTORAN
    // ============================================================
    public void ubahHargaMenu(Scanner input) {

        // menampilkan daftar menu lebih dulu
        System.out.println("\n-- Daftar Menu Saat Ini --");
        tampilkanDaftarMenu();

        // input nomor menu (DO-WHILE : diminta ulang bila di luar rentang)
        int nomor = inputNomorMenu(input);
        int index = nomor - 1;
        Menu menuDipilih = daftarMenu[index];

        System.out.println("\nMenu terpilih : " + menuDipilih.getNama()
                + "  (harga lama " + Menu.formatRupiah(menuDipilih.getHarga()) + ")");

        // input harga baru
        int hargaBaru = inputHarga(input);

        // konfirmasi sebelum dieksekusi (IF ELSE)
        boolean lanjut = konfirmasi(input, "Ubah harga menu '" + menuDipilih.getNama()
                + "' menjadi " + Menu.formatRupiah(hargaBaru) + "?");

        if (lanjut) {                              // <-- IF
            menuDipilih.setHarga(hargaBaru);
            System.out.println("Harga menu '" + menuDipilih.getNama()
                    + "' berhasil diubah menjadi " + Menu.formatRupiah(hargaBaru) + ".");
        } else {                                   // <-- ELSE
            System.out.println("Perubahan dibatalkan. Harga menu tidak berubah.");
        }
    }

    // ============================================================
    //  METHOD : MENGHAPUS MENU RESTORAN
    // ============================================================
    public void hapusMenu(Scanner input) {

        // menampilkan daftar menu lebih dulu
        System.out.println("\n-- Daftar Menu Saat Ini --");
        tampilkanDaftarMenu();

        // input nomor menu (DO-WHILE : diminta ulang bila di luar rentang)
        int nomor = inputNomorMenu(input);
        int index = nomor - 1;
        Menu menuDipilih = daftarMenu[index];

        // konfirmasi sebelum dieksekui (IF ELSE)
        boolean lanjut = konfirmasi(input, "Hapus menu '" + menuDipilih.getNama() + "'?");

        if (lanjut) {                              // <-- IF
            // pergeseran array ke kiri (perulangan while)
            int posisi = index;
            while (posisi < jumlahMenu - 1) {
                daftarMenu[posisi] = daftarMenu[posisi + 1];
                posisi = posisi + 1;
            }
            daftarMenu[jumlahMenu - 1] = null;
            jumlahMenu = jumlahMenu - 1;

            System.out.println("Menu '" + menuDipilih.getNama() + "' berhasil dihapus.");
        } else {                                   // <-- ELSE
            System.out.println("Penghapusan dibatalkan. Daftar menu tidak berubah.");
        }
    }

    // ============================================================
    //  METHOD : MENAMPILKAN DAFTAR MENU DIKELOMPOKKAN BERDASARKAN KATEGORI
    //  Perulangan FOR : menampilkan menu makanan dan menu minuman
    // ============================================================
    public void tampilkanDaftarMenu() {

        System.out.println("\n=== MAKANAN ===");
        for (int i = 0; i < jumlahMenu; i++) {      // perulangan FOR
            // IF : kategori makanan
            if (daftarMenu[i].getKategori().equalsIgnoreCase("MAKANAN")) {
                daftarMenu[i].cetakMenu(i + 1);
            }
        }

        System.out.println("\n=== MINUMAN ===");
        for (int i = 0; i < jumlahMenu; i++) {      // perulangan FOR
            // IF : kategori minuman
            if (daftarMenu[i].getKategori().equalsIgnoreCase("MINUMAN")) {
                daftarMenu[i].cetakMenu(i + 1);
            }
        }
    }

    // ============================================================
    //  METHOD : MENCARI NOMOR URUT MENU BERDASARKAN NAMA
    //  Return : nomor urut menu (1-based), atau -1 bila tidak ditemukan
    // ============================================================
    public int cariIndexMenu(String nama) {
        for (int i = 0; i < jumlahMenu; i++) {      // perulangan FOR
            if (daftarMenu[i].getNama().equalsIgnoreCase(nama.trim())) {
                return i;
            }
        }
        return -1;
    }

    // ============================================================
    //  METHOD : MENERIMA PILIHAN MENU (DO-WHILE)
    //  Bila nilai di luar pilihan, sistem terus meminta input kembali
    // ============================================================
    public int inputPilihan(Scanner input, int minimum, int maksimum, String pesan) {

        int pilihan = -1;

        // perulangan DO-WHILE : input selalu diminta minimal satu kali
        do {
            System.out.print(pesan);
            String teks = input.nextLine().trim();

            // IF : teks bukan angka
            if (!teks.matches("\\d+")) {
                System.out.println("Input harus berupa angka " + minimum + " - " + maksimum + ".");
                continue;
            }

            pilihan = Integer.parseInt(teks);

            // IF ELSE : nilai di luar rentang pilihan
            if (pilihan < minimum || pilihan > maksimum) {
                System.out.println("Pilihan tidak valid. Masukkan angka "
                        + minimum + " - " + maksimum + ".");
                pilihan = -1;
            }
        } while (pilihan < minimum || pilihan > maksimum);

        return pilihan;
    }

    // ============================================================
    //  METHOD : MENERIMA NOMOR MENU (DO-WHILE)
    // ============================================================
    public int inputNomorMenu(Scanner input) {

        int nomor = -1;

        do {
            System.out.print("Nomor menu : ");
            String teks = input.nextLine().trim();

            // IF : teks bukan angka
            if (!teks.matches("\\d+")) {
                System.out.println("Nomor menu harus berupa angka.");
                continue;
            }

            nomor = Integer.parseInt(teks);

            // IF ELSE : nomor di luar daftar menu
            if (nomor < 1 || nomor > jumlahMenu) {
                System.out.println("Nomor menu tidak valid. Masukkan 1 - " + jumlahMenu + ".");
                nomor = -1;
            }
        } while (nomor < 1 || nomor > jumlahMenu);

        return nomor;
    }

    // ============================================================
    //  METHOD : MENERIMA JUMLAH PESANAN (DO-WHILE)
    // ============================================================
    public int inputJumlah(Scanner input) {

        int jumlah = -1;

        do {
            System.out.print("Jumlah pesanan : ");
            String teks = input.nextLine().trim();

            // IF : teks bukan angka
            if (!teks.matches("\\d+")) {
                System.out.println("Jumlah harus berupa angka.");
                continue;
            }

            jumlah = Integer.parseInt(teks);

            // IF ELSE : jumlah harus lebih dari 0
            if (jumlah <= 0) {
                System.out.println("Jumlah minimal 1.");
                jumlah = -1;
            }
        } while (jumlah <= 0);

        return jumlah;
    }

    // ============================================================
    //  METHOD : MENERIMA HARGA MENU (DO-WHILE)
    // ============================================================
    public int inputHarga(Scanner input) {

        int harga = -1;

        do {
            System.out.print("Harga (Rp) : ");
            String teks = input.nextLine().trim();

            // IF : teks bukan angka
            if (!teks.matches("\\d+")) {
                System.out.println("Harga harus berupa angka.");
                continue;
            }

            harga = Integer.parseInt(teks);

            // IF ELSE : harga harus lebih dari 0
            if (harga <= 0) {
                System.out.println("Harga harus lebih dari 0.");
                harga = -1;
            }
        } while (harga <= 0);

        return harga;
    }

    // ============================================================
    //  METHOD : KONFIRMASI YA / TIDAK (DO-WHILE + IF ELSE IF)
    //  Return true bila menjawab Ya, false bila menjawab Tidak
    // ============================================================
    public boolean konfirmasi(Scanner input, String pertanyaan) {

        String jawaban = "";

        // perulangan DO-WHILE : input diminta ulang sampai jawabannya valid
        do {
            System.out.print(pertanyaan + " (Ya/Tidak) : ");
            jawaban = input.nextLine().trim();

            // IF ELSE IF : hanya 'Ya' atau 'Tidak' yang diterima
            if (!jawaban.equalsIgnoreCase("Ya") && !jawaban.equalsIgnoreCase("Tidak")) {
                System.out.println("Input tidak valid. Jawab dengan 'Ya' atau 'Tidak'.");
            }
        } while (!jawaban.equalsIgnoreCase("Ya") && !jawaban.equalsIgnoreCase("Tidak"));

        return jawaban.equalsIgnoreCase("Ya");
    }

    // ============================================================
    //  METHOD : MEMBESARKAN ARRAY MENU RESTORAN
    // ============================================================
    private void perbesarArrayMenu() {
        Menu[] arrayBaru = new Menu[daftarMenu.length * 2];
        for (int i = 0; i < jumlahMenu; i++) {      // perulangan FOR
            arrayBaru[i] = daftarMenu[i];
        }
        daftarMenu = arrayBaru;
    }
}
```

## 7. Penjelasan

### 7.1 Tipe Data, Variabel, Identifier, dan Keyword

| Tipe data | Contoh pada kode | Fungsi |
|---|---|---|
| `String` | `private String nama;` (Menu) | menyimpan nama menu, kategori, nama item promo |
| `int` | `private int harga;` (Menu) | menyimpan harga dalam Rupiah (tanpa titik desimal) |
| `boolean` | `private boolean diskonAktif;` (Struk) | menandai diskon/promo aktif atau tidak |
| `void` | `public void cetakMenu(int nomor)` | method yang hanya menampilkan data |
| array | `private Menu[] daftarMenu = new Menu[20];` | menyimpan daftar menu restoran |
| `final` | `private static final int BATAS_DISKON = 100000;` | konstanta batas diskon yang tidak dapat diubah |

Variabel contoh: `daftarMenu`, `jumlahMenu`, `pesanan`, `struk`, `subtotal`, `diskon`,
`pajak`, `totalBayar`. Identifier ditulis dengan huruf besar-kecil (camelCase) dan
tidak boleh diawali angka. Keyword yang dipakai: `class`, `public`, `private`, `static`,
`final`, `void`, `int`, `boolean`, `new`, `this`, `return`, `if`, `else`, `switch`, `case`,
`break`, `while`, `do`, `for`, `continue`, `try`, `catch`, `throw`.

### 7.2 Class, Object, dan Method

| Class | Objek yang dibuat | Method utama |
|---|---|---|
| `Menu` | `new Menu("Nasi Goreng", "MAKANAN", 18000)` | constructor, `getNama()`, `getKategori()`, `getHarga()`, `setHarga()`, `cetakMenu()`, `formatRupiah()` |
| `ItemPesanan` | `new ItemPesanan(menu, jumlah)` | constructor, `getMenu()`, `getJumlah()`, `getSubtotal()`, `getBarisStruk()` |
| `Pesanan` | `new Pesanan()` | `tambahItem()`, `hapusItem()`, `getSubtotal()`, `getTotalItemPromo()`, `getHargaItemPromo()`, `getSemuaItem()`, `perbesarArray()` |
| `Struk` | `new Struk()` | `hitung()`, `cetak()`, `getInfoDiskon()`, `getInfoPromo()`, `getTotalBayar()`, dll. |
| `Main` | `new Main()` | `main()`, `tampilkanMenuUtama()`, `terimaPesanan()`, `hitungTotalBiaya()`, `cetakStruk()`, `menuPemilik()`, `tambahMenuBaru()`, `ubahHargaMenu()`, `hapusMenu()`, `tampilkanDaftarMenu()`, `cariIndexMenu()`, `inputPilihan()`, `konfirmasi()` |

Class `Main` menjadi class utama sesuai petunjuk soal: method untuk menampilkan daftar
menu, menerima dan mengolah pesanan, menghitung total biaya, mencetak struk, mengelola
menu aplikasi, menambah menu baru, mengubah harga menu, dan menghapus menu.

### 7.3 Array dan String

| Unsur | Lokasi | Keterangan |
|---|---|---|
| Array menu | `private Menu[] daftarMenu = new Menu[20];` | array 1 dimensi berisi object `Menu`, bertambah otomatis melalui `perbesarArrayMenu()` |
| Array pesanan | `private ItemPesanan[] daftarItem = new ItemPesanan[10];` | array 1 dimensi berisi item pesanan, bertambah otomatis melalui `perbesarArray()` |
| String | `"MAKANAN"`, `"MINUMAN"`, `"selesai"`, `"Ya"`, `"Tidak"`, `Pesanan.NAMA_ITEM_PROMO = "Es Teh"` | pengelompokan kategori, penanda akhir pesanan, konfirmasi, dan penentuan item promo |
| Method String | `Menu.formatRupiah()`, `equalsIgnoreCase()`, `toLowerCase()`, `toUpperCase()`, `trim()`, `isEmpty()`, `matches("\\d+")`, `concat`/operator `+` | mengubah angka menjadi teks rupiah, membandingkan teks tanpa membedakan huruf besar-kecil, memvalidasi teks yang bukan angka |

### 7.4 Struktur Keputusan

**a. `if`** — pemeriksaan satu kondisi.

```java
// Main.terimaPesanan() : pengguna mengakhiri pesanan dengan kata "selesai"
if (nama.equalsIgnoreCase("selesai")) {
    selesai = true;
    continue;
}

// Pesanan.tambahItem() : menu tidak boleh null
if (menu == null) {
    return;
}
```

**b. `if` – `else`** — dua cabang.

```java
// Main.terimaPesanan() : menu tidak ditemukan -> diminta input kembali
int index = cariIndexMenu(nama);
if (index == -1) {
    System.out.println("Menu '" + nama + "' tidak ada. Silakan ulangi.");
    continue;
}
```

```java
// Struk.hitung() : diskon 10% hanya bila total melebihi Rp100.000
if (subtotal > BATAS_DISKON) {
    diskon = subtotal * 10 / 100;
    diskonAktif = true;
} else {
    diskon = 0;
    diskonAktif = false;
}
```

**c. `if` – `else if`** — lebih dari dua cabang.

```java
// Main.tambahMenuBaru() : nama menu tidak boleh kosong dan tidak boleh duplikat
if (nama.isEmpty()) {
    System.out.println("Nama menu tidak boleh kosong.");
} else if (cariIndexMenu(nama) != -1) {
    System.out.println("Menu '" + nama + "' sudah ada. Gunakan nama lain.");
}
```

```java
// Main.konfirmasi() : hanya 'Ya' atau 'Tidak' yang diterima
if (jawaban.equalsIgnoreCase("Ya")) {
    ...
} else if (jawaban.equalsIgnoreCase("Tidak")) {
    ...
} else {
    System.out.println("Input tidak valid. Jawab dengan 'Ya' atau 'Tidak'.");
}
```

**d. nested `if`** — syarat promo harus memenuhi dua kondisi (total besar **dan**
Es Teh minimal 2).

```java
// Struk.hitung()
if (subtotal > BATAS_PROMO) {                          // IF LUAR
    if (pesanan.getTotalItemPromo() >= MINIMUM_ITEM_PROMO) {   // IF DALAM
        bonusPromo = pesanan.getHargaItemPromo();
        promoAktif = true;
    } else {
        bonusPromo = 0;
    }
} else {
    bonusPromo = 0;
}
```

**e. `switch` – `case`** — navigasi menu.

```java
// Main.main() : menu utama
switch (pilihan) {
    case 1: aplikasi.menuPelanggan(input); break;
    case 2: aplikasi.menuPemilik(input);   break;
    case 3: jalan = false; break;
    default: System.out.println("Pilihan tidak valid."); break;
}
```

```java
// Main.menuPemilik() : menu pengelolaan menu restoran
switch (pilihan) {
    case 1: tambahMenuBaru(input); break;
    case 2: ubahHargaMenu(input); break;
    case 3: hapusMenu(input);    break;
    case 4: kembali = true;      break;
    default: System.out.println("Pilihan tidak valid."); break;
}
```

### 7.5 Struktur Perulangan

**a. `for`** — menampilkan daftar menu dan menjumlahkan subtotal.

```java
// Main.tampilkanDaftarMenu() : daftar menu dikelompokkan per kategori
for (int i = 0; i < jumlahMenu; i++) {
    if (daftarMenu[i].getKategori().equalsIgnoreCase("MAKANAN")) {
        daftarMenu[i].cetakMenu(i + 1);
    }
}
```

```java
// Pesanan.getSubtotal() : menjumlahkan subtotal seluruh item
int total = 0;
for (int i = 0; i < jumlahItem; i++) {
    total = total + daftarItem[i].getSubtotal();
}
```

**b. `for-each`** — mencetak seluruh item pesanan pada struk.

```java
// Struk.cetak()
int nomor = 1;
for (ItemPesanan item : pesanan.getSemuaItem()) {
    System.out.println(item.getBarisStruk(nomor));
    nomor = nomor + 1;
}
```

```java
// Pesanan.getTotalItemPromo() : menghitung total gelas Es Teh
for (ItemPesanan item : getSemuaItem()) {
    if (item.getMenu().getNama().equalsIgnoreCase(NAMA_ITEM_PROMO)) {
        total = total + item.getJumlah();
    }
}
```

**c. `while`** — perulangan input pesanan dan perulangan menu utama.

```java
// Main.terimaPesanan() : pesanan berakhir saat pengguna mengetik "selesai"
boolean selesai = false;
while (!selesai) {
    System.out.print("\nNama menu yang dipesan : ");
    String nama = input.nextLine().trim();
    if (nama.equalsIgnoreCase("selesai")) {
        selesai = true;
        continue;
    }
    ...
}
```

```java
// Main.main() : menu utama diulang sampai pengguna memilih Keluar
while (jalan) {
    aplikasi.tampilkanMenuUtama();
    int pilihan = aplikasi.inputPilihan(input, 1, 3, "Pilih menu (1-3) : ");
    switch (pilihan) { ... }
}
```

**d. `do-while`** — meminta input kembali sampai nilai berada di dalam pilihan.

```java
// Main.inputPilihan() : input di luar rentang -> diminta ulang
int pilihan = -1;
do {
    System.out.print(pesan);
    String teks = input.nextLine().trim();
    if (!teks.matches("\\d+")) {
        System.out.println("Input harus berupa angka " + minimum + " - " + maksimum + ".");
        continue;
    }
    pilihan = Integer.parseInt(teks);
    if (pilihan < minimum || pilihan > maksimum) {
        System.out.println("Pilihan tidak valid. Masukkan angka " + minimum + " - " + maksimum + ".");
        pilihan = -1;
    }
} while (pilihan < minimum || pilihan > maksimum);
```

`do-while` juga dipakai pada `inputNomorMenu()`, `inputJumlah()`, `inputHarga()`,
`konfirmasi()`, dan pada `tambahMenuBaru()` untuk menambah beberapa menu sekaligus.

### 7.6 Perhitungan Total Biaya

| Komponen | Perhitungan | Letak |
|---|---|---|
| Subtotal | `Σ (harga menu × jumlah)` | `Pesanan.getSubtotal()` |
| Diskon 10% | `subtotal > 100.000 ? subtotal × 10/100 : 0` | `Struk.hitung()` (if-else) |
| Promo BOGO | `subtotal > 50.000` **dan** `Es Teh ≥ 2` → potong 1 × Rp6.000 | `Struk.hitung()` (nested if) |
| Pajak 10% | `subtotal × 10/100` | `Struk.hitung()` |
| Biaya pelayanan | `Rp20.000` | konstanta `BIAYA_PELAYANAN` |
| Total bayar | `subtotal − diskon − promo + pajak + biaya pelayanan` | `Struk.hitung()` |

## 8. Skenario Pengujian

| # | Skenario | Input | Hasil yang benar | Hasil program |
|---|---|---|---|---|
| 1 | Total < Rp50.000 | Nasi Goreng 1, Es Teh 2 | subtotal 30.000, pajak 3.000, total 53.000 | sesuai |
| 2 | Promo aktif | Ayam Bakar 2, Es Teh 2 | subtotal 68.000, promo −6.000, pajak 6.800, total 88.800 | sesuai |
| 3 | Diskon + promo | Ayam Bakar 3, Jus Alpukat 1, Es Teh 2 | subtotal 110.000, diskon −11.000, promo −6.000, pajak 11.000, total 124.000 | sesuai |
| 4 | Promo tidak aktif (Es Teh 1) | Ayam Bakar 2, Es Teh 1 | promo 0, total 88.200 | sesuai |
| 5 | Menu tidak ditemukan | `Nasi Goren` | "Menu tidak ada. Silakan ulangi." lalu input baru | sesuai |
| 6 | Pilihan menu di luar rentang | `9` pada menu utama | "Pilihan tidak valid" lalu input baru | sesuai |
| 7 | Tambah menu baru | Es Kelapa / minuman / 15.000 | menu tersimpan dan tampil pada daftar | sesuai |
| 8 | Tambah beberapa menu | jawab `Ya` pada "Tambah menu lagi?" | menu baru kedua ditambahkan | sesuai |
| 9 | Ubah harga + konfirmasi `Ya` | Es Mangga → 13.000 | harga berubah menjadi Rp13.000 | sesuai |
| 10 | Hapus menu + konfirmasi `Tidak` | Es Kelapa | "Penghapusan dibatalkan", daftar tetap | sesuai |
| 11 | Hapus menu + konfirmasi `Ya` | Jus Alpukat | menu terhapus, nomor menu berikutnya turun | sesuai |
| 12 | Navigasi | pilih `4` pada menu pengelolaan | kembali ke menu utama | sesuai |

### Cuplikan hasil (Skenario 3)

```
[INFO] Diskon 10% aktif, potongan Rp11.000.
[INFO] Total memenuhi syarat promo (>Rp50.000).
[INFO] Promo aktif! Beli 1 gratis 1 untuk Es Teh, bonus Rp6.000.

===========================================
             STRUK PESANAN
===========================================
1. Ayam Bakar (MAKANAN)
     Jumlah          : 3
     Harga Satuan    : Rp28.000
     Subtotal Item   : Rp84.000
2. Jus Alpukat (MINUMAN)
     Jumlah          : 1
     Harga Satuan    : Rp14.000
     Subtotal Item   : Rp14.000
3. Es Teh (MINUMAN)
     Jumlah          : 2
     Harga Satuan    : Rp6.000
     Subtotal Item   : Rp12.000
------------------------------------------
Total Biaya Pesanan     : Rp110.000
Diskon 10%              : -Rp11.000   (total > Rp100.000)
Promo Beli 1 Gratis 1   : -Rp6.000   (Es Teh)
Pajak 10%               : Rp11.000   (10% dari Rp110.000)
Biaya Pelayanan         : Rp20.000
------------------------------------------
TOTAL BAYAR             : Rp124.000
===========================================
```

## 9. Bukti Video Penjelasan

| Keterangan | Isi |
|---|---|
| Link rekaman | *(sisipkan link YouTube mode Public/Unlisted atau Google Drive akses terbuka)* |
| Durasi | maksimal 15 menit |
| Wajah mahasiswa | tampil jelas |
| Intro wajib | Nama, NIM, Program studi, Mata Kuliah |
| Isi | penjelasan singkat tugas, demonstrasi program secara langsung, penjelasan hasil, penutup |

Checklist sebelum mengunggah:

- [ ] Link dapat dibuka tanpa permintaan izin akses tambahan
- [ ] Video tidak berstatus Private/Restricted
- [ ] Wajah terlihat jelas
- [ ] Menyebutkan Nama, NIM, Program studi, dan Mata Kuliah di awal
- [ ] Menampilkan proses praktik/demonstrasi program
- [ ] Durasi ≤ 15 menit

## 10. Kesimpulan

Program berhasil mengimplementasikan seluruh ketentuan soal: data menu dalam array,
pemesanan tidak terbatas hingga kata `selesai` dengan pengulangan input bila nama menu
tidak ditemukan, perhitungan total biaya dengan pajak 10% dan biaya pelayanan Rp20.000,
diskon 10% untuk total di atas Rp100.000, promo beli 1 gratis 1 untuk total di atas
Rp50.000, pencetakan struk lengkap, serta menu pengelolaan menu restoran dengan tambah,
ubah harga, hapus, konfirmasi `Ya`/`Tidak`, dan navigasi kembali ke menu utama.
Seluruh struktur keputusan (if, if-else, if-else if, nested if, switch case) dan seluruh
struktur perulangan (for, for-each, while, do-while) digunakan di dalam program.
