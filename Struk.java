/**
 * ============================================================
 *  TUGAS PRAKTIK 2 - STSI4201 Pemrograman Berbasis Desktop
 *  File : Struk.java
 * ============================================================
 * Class Struk menghitung dan mencetak STRUK PESANAN.
 *
 * Aturan perhitungan (sesuai soal Tugas 2):
 *   - subtotal    : jumlah seluruh subtotal item
 *   - diskon 10%  : jika subtotal > Rp100.000  (IF - ELSE)
 *   - promo BOGO  : jika subtotal > Rp50.000 DAN ada minuman ≥ 1
 *                   (NESTED IF, berlaku untuk kategori MINUMAN umum)
 *   - pajak 10%   : 10% dari subtotal
 *   - biaya       : Rp20.000
 *   - total bayar : subtotal - diskon - promo + pajak + biaya
 *
 * Struktur kontrol:
 *   - IF - ELSE   : diskon 10%
 *   - NESTED IF   : promo beli 1 gratis 1 (luar: total > 50.000, dalam: minuman ≥ 1)
 *   - IF - ELSE   : tampilkan info diskon/promo pada struk
 *   - for-each    : mencetak item pada struk
 * ============================================================
 */
public class Struk {

    // ================= KONSTANTA ATURAN BISNIS =================
    private static final int BATAS_DISKON = 100000;     // diskon bila subtotal > 100.000
    private static final int BATAS_PROMO = 50000;       // promo bila subtotal > 50.000
    private static final int BIAYA_PELAYANAN = 20000;   // biaya pelayanan Rp20.000

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

        // ---- 3. Promo beli 1 gratis 1 untuk kategori MINUMAN (NESTED IF) ----
        bonusPromo = 0;
        promoAktif = false;

        if (subtotal > BATAS_PROMO) {                  // <-- IF LUAR : total > Rp50.000
            if (pesanan.getTotalItemPromo() >= 1) {    // <-- IF DALAM : ada minuman ≥ 1
                bonusPromo = pesanan.getHargaItemPromo(); // gratis 1 minuman (termurah)
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
                    + "   (minuman termurah)");
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
            // IF : ada minuman setidaknya 1
            if (promoAktif) {
                return "[INFO] Total memenuhi syarat promo (>Rp50.000)."
                        + "\n[INFO] Promo aktif! Beli 1 gratis 1 untuk kategori MINUMAN, bonus "
                        + Menu.formatRupiah(bonusPromo) + ".";
            } else {
                return "[INFO] Total memenuhi syarat promo (>Rp50.000)."
                        + "\n[INFO] Promo tidak aktif karena belum ada item minuman yang dipesan.";
            }
        } else {
            return "[INFO] Promo tidak aktif karena total belum melebihi Rp50.000.";
        }
    }

    // ============================================================
    //  METHOD : MENGEMBALIKAN TEKS STRUK (untuk GUI / cetak ke layar)
    // ============================================================
    public String generateStrukText(Pesanan pesanan) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===========================================\n");
        sb.append("             STRUK PESANAN\n");
        sb.append("===========================================\n");

        int nomor = 1;
        for (ItemPesanan item : pesanan.getSemuaItem()) {
            sb.append(item.getBarisStruk(nomor)).append("\n");
            nomor = nomor + 1;
        }

        sb.append("------------------------------------------\n");
        sb.append("Total Biaya Pesanan     : ").append(Menu.formatRupiah(subtotal)).append("\n");

        if (diskonAktif) {
            sb.append("Diskon 10%              : -").append(Menu.formatRupiah(diskon))
                    .append("   (total > ").append(Menu.formatRupiah(BATAS_DISKON)).append(")\n");
        } else {
            sb.append("Diskon 10%              : tidak ada\n");
        }

        if (promoAktif) {
            sb.append("Promo Beli 1 Gratis 1   : -").append(Menu.formatRupiah(bonusPromo))
                    .append("   (kategori MINUMAN)\n");
        } else {
            sb.append("Promo Beli 1 Gratis 1   : tidak ada\n");
        }

        sb.append("Pajak 10%               : ").append(Menu.formatRupiah(pajak))
                .append("   (10% dari ").append(Menu.formatRupiah(subtotal)).append(")\n");
        sb.append("Biaya Pelayanan         : ").append(Menu.formatRupiah(biayaPelayanan)).append("\n");
        sb.append("------------------------------------------\n");
        sb.append("TOTAL BAYAR             : ").append(Menu.formatRupiah(totalBayar)).append("\n");
        sb.append("===========================================\n");

        return sb.toString();
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
