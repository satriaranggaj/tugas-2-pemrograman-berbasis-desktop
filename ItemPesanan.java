/**
 * ============================================================
 *  TUGAS PRAKTIK 2 - STSI4201 Pemrograman Berbasis Desktop
 *  File : ItemPesanan.java
 * ============================================================
 * Class ItemPesanan merepresentasikan SATU BARIS pesanan pelanggan,
 * yaitu gabungan antara menu yang dipilih dan jumlah yang dipesan.
 *
 * Saat item pesanan dibuat, seluruh informasi menu (nama, kategori,
 * harga) di-snapshot sehingga tidak berubah meski pemilik restoran
 * mengubah data menu nantinya.
 *
 * Atribut :
 *   - menu             : object Menu yang dipesan (referensi asli)
 *   - namaSaatDipesan  : snapshot nama menu saat dipesan
 *   - kategoriSaatDipesan : snapshot kategori menu saat dipesan
 *   - hargaSaatDipesan : snapshot harga satuan saat dipesan
 *   - jumlah           : berapa banyak menu tersebut dipesan
 * ============================================================
 */
public class ItemPesanan {

    // ================= ATRIBUT =================
    private Menu menu;                  // object menu yang dipesan (referensi)
    private final String namaSaatDipesan;       // snapshot nama saat dipesan
    private final String kategoriSaatDipesan;   // snapshot kategori saat dipesan
    private final int hargaSaatDipesan;         // snapshot harga saat dipesan
    private int jumlah;                   // jumlah pesanan untuk menu tersebut

    // ================= CONSTRUCTOR =================
    public ItemPesanan(Menu menu, int jumlah) {
        this.menu = menu;
        this.namaSaatDipesan = menu.getNama();
        this.kategoriSaatDipesan = menu.getKategori();
        this.hargaSaatDipesan = menu.getHarga();
        this.jumlah = jumlah;
    }

    // ================= METHOD GETTER =================
    public Menu getMenu() {
        return menu;
    }

    public String getNama() {
        return namaSaatDipesan;
    }

    public String getKategori() {
        return kategoriSaatDipesan;
    }

    public int getHargaSatuan() {
        return hargaSaatDipesan;
    }

    public int getJumlah() {
        return jumlah;
    }

    // ================= METHOD SETTER JUMLAH =================
    // Digunakan saat mengubah jumlah pesanan item yang sudah ada.
    public void setJumlah(int jumlahBaru) {
        if (jumlahBaru <= 0) {
            throw new IllegalArgumentException(
                "Jumlah pesanan minimal 1."
            );
        }

        this.jumlah = jumlahBaru;
    }

    // ================= METHOD MENGHITUNG SUBTOTAL SATU ITEM =================
    // subtotal item = harga snapshot saat dipesan x jumlah pesanan
    public int getSubtotal() {
        return Math.multiplyExact(hargaSaatDipesan, jumlah);
    }

    // ================= METHOD MENGUBAH ITEM MENJADI TEKS STRUK =================
    public String getBarisStruk(int nomor) {
        return nomor + ". " + namaSaatDipesan
            + " (" + kategoriSaatDipesan + ")"
            + "\n     Jumlah          : " + jumlah
            + "\n     Harga Satuan    : "
            + Menu.formatRupiah(hargaSaatDipesan)
            + "\n     Subtotal Item   : "
            + Menu.formatRupiah(getSubtotal());
    }
}
