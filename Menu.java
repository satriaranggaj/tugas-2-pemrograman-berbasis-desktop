/**
 * ============================================================
 *  TUGAS PRAKTIK 2 - STSI4201 Pemrograman Berbasis Desktop
 *  APLIKASI KASIR RESTORAN SEDERHANA (Java Swing GUI)
 *  File : Menu.java
 * ============================================================
 * Class Menu digunakan untuk merepresentasikan SATU menu restoran.
 *
 * Atribut (sesuai soal):
 *   - nama     : nama menu, contoh "Nasi Goreng"
 *   - harga    : harga menu dalam Rupiah (int)
 *   - kategori : kategori menu, contoh "MAKANAN" atau "MINUMAN"
 *
 * Setter ditambahkan karena pada Tugas 2 pemilik restoran
 * dapat mengubah nama, harga, dan kategori menu.
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

    // ================= METHOD SETTER =================
    // Dipakai saat pemilik restoran mengubah data menu (nama, harga, kategori)
    public void setNama(String namaBaru) {
        this.nama = namaBaru;
    }

    public void setKategori(String kategoriBaru) {
        this.kategori = kategoriBaru;
    }

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
