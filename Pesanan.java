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
 * Struktur kontrol yang dipakai di class ini:
 *   - if            : validasi menu dan jumlah tidak boleh null / <= 0
 *   - if            : array harus diperbesar bila penuh
 *   - for           : getSubtotal, getSemuaItem, kosongkan
 *   - for-each      : getTotalItemPromo (kategori minuman), getHargaItemPromo
 *   - while         : hapusItem (pergeseran array ke kiri)
 *   - if-else if    : (digunakan dalam MainGUI untuk validasi input)
 * ============================================================
 */
public class Pesanan {

    // ================= KONSTANTA =================
    // Kategori minuman yang mendapat promo "beli 1 gratis 1"
    // Promo berlaku untuk KATEGORI minuman secara umum, bukan hanya satu item.
    public static final String KATEGORI_PROMO = "MINUMAN";
    public static final int MINIMUM_ITEM_PROMO = 1;   // minimal 1 item minuman

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

    // ================= METHOD MENGUBAH JUMLAH ITEM YANG SUDAH ADA =================
    // Digunakan saat pelanggan ingin mengubah jumlah pesanan item tertentu.
    public boolean ubahJumlahItem(int index, int jumlahBaru) {

        // IF : indeks di luar rentang
        if (index < 0 || index >= jumlahItem) {
            return false;
        }

        // IF : jumlah baru harus valid
        if (jumlahBaru <= 0) {
            return false;
        }

        daftarItem[index].setJumlah(jumlahBaru);
        return true;
    }

    // ================= METHOD MENGHAPUS ITEM PESANAN =================
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

    // ================= METHOD MENGHITUNG TOTAL ITEM PROMO (KATEGORI MINUMAN) =================
    // Perulangan for-each : menghitung jumlah item yang termasuk kategori minuman
    public int getTotalItemPromo() {
        int total = 0;
        for (ItemPesanan item : getSemuaItem()) {     // perulangan for-each
            // IF : item tersebut termasuk kategori minuman (promo)
            if (item.getMenu().getKategori().equalsIgnoreCase(KATEGORI_PROMO)) {
                total = total + item.getJumlah();
            }
        }
        return total;
    }

    // ================= METHOD MENGAMBIL HARGA ITEM PROMO (MINUMAN TERMURAH) =================
    // Perulangan for-each : mencari harga minuman termurah untuk dihitung sebagai bonus
    public int getHargaItemPromo() {
        int hargaTermurah = 0;
        boolean pertama = true;
        for (ItemPesanan item : getSemuaItem()) {     // perulangan for-each
            if (item.getMenu().getKategori().equalsIgnoreCase(KATEGORI_PROMO)) {
                int harga = item.getMenu().getHarga();
                if (pertama || harga < hargaTermurah) {
                    hargaTermurah = harga;
                    pertama = false;
                }
            }
        }
        return hargaTermurah;
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
