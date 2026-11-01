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

        // menampilkan daftar menu makanan dan minuman sebelum pesanan diambil
        // (sesuai soal: pelanggan memesan berdasarkan daftar menu yang ditampilkan)
        System.out.println("\n-- Daftar Menu Restoran --");
        tampilkanDaftarMenu();

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
