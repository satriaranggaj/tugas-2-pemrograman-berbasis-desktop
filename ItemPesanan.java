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
    private Menu menu;           // object menu yang dipesan
    private int jumlah;          // jumlah pesanan untuk menu tersebut
    private int hargaSaatDipesan;// snapshot harga saat item dipesan (agar tidak berubah jika harga menu diubah nanti)

    // ================= CONSTRUCTOR =================
    public ItemPesanan(Menu menu, int jumlah) {
        this.menu = menu;
        this.jumlah = jumlah;
        this.hargaSaatDipesan = menu.getHarga(); // simpan harga snapshot
    }

    // ================= METHOD GETTER =================
    public Menu getMenu() {
        return menu;
    }

    public int getJumlah() {
        return jumlah;
    }

    // ================= METHOD SETTER JUMLAH =================
    // Digunakan saat mengubah jumlah pesanan item yang sudah ada
    public void setJumlah(int jumlahBaru) {
        this.jumlah = jumlahBaru;
    }

    // ================= METHOD MENGHITUNG SUBTOTAL SATU ITEM =================
    // subtotal item = harga snapshot saat dipesan x jumlah pesanan
    public int getSubtotal() {
        return hargaSaatDipesan * jumlah;
    }

    // ================= METHOD MENGUBAH ITEM MENJADI TEKS STRUK =================
    public String getBarisStruk(int nomor) {
        return nomor + ". " + menu.getNama() + " (" + menu.getKategori() + ")"
                + "\n     Jumlah          : " + jumlah
                + "\n     Harga Satuan    : " + Menu.formatRupiah(hargaSaatDipesan)
                + "\n     Subtotal Item   : " + Menu.formatRupiah(getSubtotal());
    }
}
