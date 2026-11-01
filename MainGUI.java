/**
 * ============================================================
 *  TUGAS PRAKTIK 2 - STSI4201 Pemrograman Berbasis Desktop
 *  APLIKASI KASIR RESTORAN SEDERHANA (Java Swing GUI)
 *  File : MainGUI.java
 * ============================================================
 * Antarmuka grafis (GUI) aplikasi kasir restoran menggunakan Swing.
 *
 * Dua tab utama (sesuai soal):
 *   1. TAB PELANGGAN  : menampilkan daftar menu, memesan makanan & minuman,
 *                       menghitung total, dan mencetak struk pesanan.
 *   2. TAB PEMILIK    : mengelola data menu restoran (tambah menu baru,
 *                       ubah harga, hapus menu) dengan konfirmasi Ya/Tidak.
 *
 * Struktur yang dipakai:
 *   - Array      : daftar menu restoran & daftar pesanan pelanggan
 *   - if / else if / else : validasi input dan cek syarat diskon & promo
 *   - nested if  : syarat promo (total > 50.000 DAN Es Teh minimal 2)
 *   - switch case: navigasi logika internal
 *   - for        : menampilkan daftar menu & menjumlahkan subtotal
 *   - for-each   : mencetak seluruh item pada struk pesanan
 *   - while      : perulangan input pesanan sampai kata "selesai"
 *   - do-while   : meminta input kembali bila nilai di luar pilihan
 * ============================================================
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class MainGUI extends JFrame {

    public static MainGUI instance;        // referensi untuk demo runner
    private JTabbedPane tabbedPane;        // tab pelanggan / pemilik

    // ================= ATRIBUT APLIKASI =================
    private Menu[] daftarMenu = new Menu[20];   // ARRAY menu restoran
    private int jumlahMenu = 0;                 // jumlah menu yang terpakai

    private final Pesanan pesanan = new Pesanan();   // ARRAY pesanan pelanggan
    private final Struk struk = new Struk();         // perhitungan & struk

    // ================= KOMPONEN TAB PELANGGAN =================
    private JTable tblMenu;            // tabel daftar menu restoran
    private DefaultTableModel modelMenu;

    private JTable tblPesanan;         // tabel pesanan pelanggan
    private DefaultTableModel modelPesanan;

    private JTextArea areaStruk;       // area cetak struk
    private JLabel lblTotalBayar;      // label total bayar

    // ================= KOMPONEN TAB PEMILIK =================
    private JTable tblMenuKelola;      // tabel daftar menu untuk pengelolaan
    private DefaultTableModel modelMenuKelola;

    // ================= KONSTRUKTOR =================
    public MainGUI() {
        instance = this;
        setTitle("Aplikasi Kasir Restoran - STSI4201 Pemrograman Berbasis Desktop");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 700);
        setLocationRelativeTo(null);

        // mengisi data menu awal (4 makanan + 4 minuman)
        isiMenuAwal();

        // inisialisasi komponen UI
        initUI();

        setVisible(true);
    }

    // ============================================================
    //  METHOD : INISIALISASI UI
    // ============================================================
    private void initUI() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Menu Pelanggan", createPanelPelanggan());
        tabbedPane.addTab("Kelola Menu (Pemilik)", createPanelPemilik());
        this.tabbedPane = tabbedPane;

        add(tabbedPane, BorderLayout.CENTER);
    }

    // ============================================================
    //  METHOD : MENGISI DATA MENU AWAL
    // ============================================================
    private void isiMenuAwal() {
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
    //  METHOD : MEMBUAT PANEL PELANGGAN
    // ============================================================
    private JPanel createPanelPelanggan() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ---- Bagian kiri: Daftar Menu ----
        JPanel panelKiri = new JPanel(new BorderLayout());
        panelKiri.setBorder(BorderFactory.createTitledBorder("Daftar Menu Restoran"));

        String[] colMenu = {"No", "Nama Menu", "Kategori", "Harga"};
        modelMenu = new DefaultTableModel(colMenu, 0);
        tblMenu = new JTable(modelMenu);
        tblMenu.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        muatTabelMenu(modelMenu);

        JScrollPane scrollMenu = new JScrollPane(tblMenu);
        scrollMenu.setPreferredSize(new Dimension(320, 400));
        panelKiri.add(scrollMenu, BorderLayout.CENTER);

        // ---- Bagian tengah: Pesanan ----
        JPanel panelTengah = new JPanel(new BorderLayout());
        panelTengah.setBorder(BorderFactory.createTitledBorder("Pesanan Pelanggan"));

        String[] colPesanan = {"Nama Menu", "Kategori", "Jumlah", "Harga Satuan", "Subtotal Item"};
        modelPesanan = new DefaultTableModel(colPesanan, 0);
        tblPesanan = new JTable(modelPesanan);
        tblPesanan.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPesanan = new JScrollPane(tblPesanan);
        panelTengah.add(scrollPesanan, BorderLayout.CENTER);

        JPanel panelTombolTengah = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton btnTambahPesanan = new JButton("Tambah Pesanan");
        JButton btnEditJumlah = new JButton("Edit Jumlah");
        JButton btnHapusItem = new JButton("Hapus Item Terpilih");
        JButton btnHitung = new JButton("Hitung Total");
        JButton btnCetak = new JButton("Cetak Struk");
        JButton btnReset = new JButton("Reset Pesanan");

        panelTombolTengah.add(btnTambahPesanan);
        panelTombolTengah.add(btnEditJumlah);
        panelTombolTengah.add(btnHapusItem);
        panelTombolTengah.add(btnHitung);
        panelTombolTengah.add(btnCetak);
        panelTombolTengah.add(btnReset);
        panelTengah.add(panelTombolTengah, BorderLayout.SOUTH);

        // ---- Bagian kanan: Struk / Total ----
        JPanel panelKanan = new JPanel(new BorderLayout());
        panelKanan.setBorder(BorderFactory.createTitledBorder("Struk Pesanan"));

        areaStruk = new JTextArea(20, 25);
        areaStruk.setEditable(false);
        areaStruk.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        areaStruk.setText("Belum ada struk.\n\n1. Pilih menu dari daftar di kiri.\n2. Isi jumlah, klik 'Tambah Pesanan'.\n3. Klik 'Hitung Total'.\n4. Klik 'Cetak Struk'.");

        JScrollPane scrollStruk = new JScrollPane(areaStruk);
        panelKanan.add(scrollStruk, BorderLayout.CENTER);

        JPanel panelTotal = new JPanel(new BorderLayout());
        lblTotalBayar = new JLabel("TOTAL BAYAR: Rp0", SwingConstants.CENTER);
        lblTotalBayar.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTotalBayar.setForeground(new Color(0, 100, 0));
        panelTotal.add(lblTotalBayar, BorderLayout.CENTER);
        panelKanan.add(panelTotal, BorderLayout.SOUTH);

        // Gabungkan kiri + tengah + kanan
        JSplitPane splitKiriTengah = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelKiri, panelTengah);
        splitKiriTengah.setResizeWeight(0.35);
        splitKiriTengah.setOneTouchExpandable(true);

        JSplitPane splitUtama = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitKiriTengah, panelKanan);
        splitUtama.setResizeWeight(0.70);
        splitUtama.setOneTouchExpandable(true);

        panel.add(splitUtama, BorderLayout.CENTER);

        // ================= EVENT LISTENER PELANGGAN =================
        btnTambahPesanan.addActionListener(e -> tambahPesananDariTabel());
        btnEditJumlah.addActionListener(e -> editJumlahItem());
        btnHapusItem.addActionListener(e -> hapusItemDariTabel());
        btnHitung.addActionListener(e -> hitungTotalBiayaGUI());
        btnCetak.addActionListener(e -> cetakStrukGUI());
        btnReset.addActionListener(e -> resetPesananGUI());

        // double-click pada tabel menu langsung menambah pesanan
        tblMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    tambahPesananDariTabel();
                }
            }
        });

        return panel;
    }

    // ============================================================
    //  METHOD : MEMBUAT PANEL PEMILIK (MANAJEMEN MENU)
    // ============================================================
    private JPanel createPanelPemilik() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblInfo = new JLabel("<html><b>Manajemen Menu Restoran (Pemilik)</b><br>"
                + "Pilih menu pada tabel, lalu klik tombol Ubah Harga atau Hapus Menu.<br>"
                + "Semua perubahan memerlukan konfirmasi 'Ya'/'Tidak'.</html>");
        panel.add(lblInfo, BorderLayout.NORTH);

        String[] colMenu = {"No", "Nama Menu", "Kategori", "Harga"};
        modelMenuKelola = new DefaultTableModel(colMenu, 0);
        tblMenuKelola = new JTable(modelMenuKelola);
        tblMenuKelola.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        muatTabelMenu(modelMenuKelola);

        JScrollPane scrollMenu = new JScrollPane(tblMenuKelola);
        panel.add(scrollMenu, BorderLayout.CENTER);

        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnTambah = new JButton("Tambah Menu Baru");
        JButton btnUbah = new JButton("Ubah Menu Terpilih");
        JButton btnHapus = new JButton("Hapus Menu Terpilih");
        JButton btnRefresh = new JButton("Refresh Daftar");

        panelTombol.add(btnTambah);
        panelTombol.add(btnUbah);
        panelTombol.add(btnHapus);
        panelTombol.add(btnRefresh);
        panel.add(panelTombol, BorderLayout.SOUTH);

        // ================= EVENT LISTENER PEMILIK =================
        btnTambah.addActionListener(e -> tambahMenuBaruGUI());
        btnUbah.addActionListener(e -> ubahMenuGUI());
        btnHapus.addActionListener(e -> hapusMenuGUI());
        btnRefresh.addActionListener(e -> muatTabelMenu(modelMenuKelola));

        return panel;
    }

    // ============================================================
    //  METHOD : MEMUAT TABEL MENU (digunakan kedua panel)
    // ============================================================
    private void muatTabelMenu(DefaultTableModel model) {
        model.setRowCount(0);
        for (int i = 0; i < jumlahMenu; i++) {
            Menu m = daftarMenu[i];
            Object[] row = {
                    i + 1,
                    m.getNama(),
                    m.getKategori(),
                    Menu.formatRupiah(m.getHarga())
            };
            model.addRow(row);
        }
    }

    // ============================================================
    //  METHOD : MENAMBAHKAN PESANAN DARI TABEL MENU
    // ============================================================
    private void tambahPesananDariTabel() {
        int row = tblMenu.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                    "Pilih menu pada tabel Daftar Menu terlebih dahulu.",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nama = (String) modelMenu.getValueAt(row, 1);
        int index = cariIndexMenu(nama);
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Menu tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String jumlahStr = JOptionPane.showInputDialog(this,
                "Jumlah " + daftarMenu[index].getNama() + " yang dipesan:", "1");
        if (jumlahStr == null) return; // user klik Batal

        try {
            int jumlah = Integer.parseInt(jumlahStr.trim());
            if (jumlah <= 0) {
                JOptionPane.showMessageDialog(this, "Jumlah minimal 1.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            pesanan.tambahItem(daftarMenu[index], jumlah);
            muatTabelPesanan();
            JOptionPane.showMessageDialog(this,
                    "Pesanan ditambahkan: " + daftarMenu[index].getNama() + " x" + jumlah,
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Jumlah harus berupa angka.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ============================================================
    //  METHOD : MENGHAPUS ITEM PESANAN TERPILIH
    // ============================================================
    private void hapusItemDariTabel() {
        int row = tblPesanan.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                    "Pilih item pada tabel Pesanan terlebih dahulu.",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int konfirmasi = JOptionPane.showConfirmDialog(this,
                "Yakin ingin menghapus item ini dari pesanan?",
                "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        if (konfirmasi == JOptionPane.YES_OPTION) {
            pesanan.hapusItem(row);
            muatTabelPesanan();
        }
    }

    // ============================================================
    //  METHOD : EDIT JUMLAH ITEM PESANAN (do-while validasi input)
    // ============================================================
    private void editJumlahItem() {
        int row = tblPesanan.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                    "Pilih item pada tabel Pesanan terlebih dahulu.",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ItemPesanan item = pesanan.getItem(row);
        String namaMenu = item.getMenu().getNama();
        int jumlahLama = item.getJumlah();

        String jumlahStr;
        int jumlahBaru = -1;

        // DO-WHILE : input diminta ulang sampai valid atau dibatalkan
        do {
            jumlahStr = JOptionPane.showInputDialog(this,
                    "Edit jumlah '" + namaMenu + "'\nJumlah lama: " + jumlahLama + "\nMasukkan jumlah baru:",
                    String.valueOf(jumlahLama));
            if (jumlahStr == null) {
                return; // user klik Batal
            }
            try {
                jumlahBaru = Integer.parseInt(jumlahStr.trim());
                if (jumlahBaru <= 0) {
                    JOptionPane.showMessageDialog(this, "Jumlah minimal 1.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                } else {
                    break;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Jumlah harus berupa angka.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } while (true);

        if (pesanan.ubahJumlahItem(row, jumlahBaru)) {
            muatTabelPesanan();
            JOptionPane.showMessageDialog(this,
                    "Jumlah '" + namaMenu + "' diubah menjadi " + jumlahBaru + ".",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Gagal mengubah jumlah.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ============================================================
    //  METHOD : MEMUAT TABEL PESANAN
    // ============================================================
    private void muatTabelPesanan() {
        modelPesanan.setRowCount(0);
        for (int i = 0; i < pesanan.getJumlahItem(); i++) {
            ItemPesanan item = pesanan.getItem(i);
            Menu m = item.getMenu();
            Object[] row = {
                    m.getNama(),
                    m.getKategori(),
                    item.getJumlah(),
                    Menu.formatRupiah(m.getHarga()),
                    Menu.formatRupiah(item.getSubtotal())
            };
            modelPesanan.addRow(row);
        }
    }

    // ============================================================
    //  METHOD : MENGHITUNG TOTAL BIAYA (GUI)
    // ============================================================
    private void hitungTotalBiayaGUI() {
        if (pesanan.isKosong()) {
            JOptionPane.showMessageDialog(this,
                    "Pesanan kosong. Silakan tambahkan item terlebih dahulu.",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        struk.hitung(pesanan);
        lblTotalBayar.setText("TOTAL BAYAR: " + Menu.formatRupiah(struk.getTotalBayar()));

        JOptionPane.showMessageDialog(this,
                struk.getInfoDiskon() + "\n" + struk.getInfoPromo(),
                "Informasi Diskon & Promo", JOptionPane.INFORMATION_MESSAGE);
    }

    // ============================================================
    //  METHOD : MENCETAK STRUK (GUI)
    // ============================================================
    private void cetakStrukGUI() {
        if (pesanan.isKosong()) {
            JOptionPane.showMessageDialog(this,
                    "Pesanan kosong. Tidak ada yang dapat dicetak.",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String teksStruk = struk.generateStrukText(pesanan);
        areaStruk.setText(teksStruk);

        JOptionPane.showMessageDialog(this,
                "Struk pesanan telah dicetak pada area kanan.",
                "Cetak Struk", JOptionPane.INFORMATION_MESSAGE);
    }

    // ============================================================
    //  METHOD : RESET PESANAN
    // ============================================================
    private void resetPesananGUI() {
        int konfirmasi = JOptionPane.showConfirmDialog(this,
                "Yakin ingin mengosongkan seluruh pesanan?",
                "Konfirmasi Reset", JOptionPane.YES_NO_OPTION);
        if (konfirmasi == JOptionPane.YES_OPTION) {
            pesanan.kosongkan();
            muatTabelPesanan();
            areaStruk.setText("Belum ada struk.\n\n1. Pilih menu dari daftar di kiri.\n2. Isi jumlah, klik 'Tambah Pesanan'.\n3. Klik 'Hitung Total'.\n4. Klik 'Cetak Struk'.");
            lblTotalBayar.setText("TOTAL BAYAR: Rp0");
        }
    }

    // ============================================================
    //  METHOD : MENAMBAHKAN MENU BARU (GUI)
    // ============================================================
    private void tambahMenuBaruGUI() {
        // input nama (do-while analog: ulang sampai valid)
        String nama = "";
        boolean validNama = false;
        while (!validNama) {
            nama = JOptionPane.showInputDialog(this, "Nama menu baru:", "Tambah Menu", JOptionPane.QUESTION_MESSAGE);
            if (nama == null) return; // Batal
            nama = nama.trim();
            if (nama.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama menu tidak boleh kosong.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            } else if (cariIndexMenu(nama) != -1) {
                JOptionPane.showMessageDialog(this, "Menu '" + nama + "' sudah ada. Gunakan nama lain.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            } else {
                validNama = true;
            }
        }

        // input kategori
        String kategori = "";
        boolean validKat = false;
        while (!validKat) {
            kategori = JOptionPane.showInputDialog(this,
                    "Kategori (makanan/minuman):", "Tambah Menu", JOptionPane.QUESTION_MESSAGE);
            if (kategori == null) return;
            kategori = kategori.trim().toLowerCase();
            if (!kategori.equals("makanan") && !kategori.equals("minuman")) {
                JOptionPane.showMessageDialog(this, "Kategori harus 'makanan' atau 'minuman'.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            } else {
                validKat = true;
            }
        }

        // input harga
        int harga = 0;
        boolean validHarga = false;
        while (!validHarga) {
            String hargaStr = JOptionPane.showInputDialog(this, "Harga (Rp):", "Tambah Menu", JOptionPane.QUESTION_MESSAGE);
            if (hargaStr == null) return;
            try {
                harga = Integer.parseInt(hargaStr.trim());
                if (harga <= 0) {
                    JOptionPane.showMessageDialog(this, "Harga harus lebih dari 0.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                } else {
                    validHarga = true;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Harga harus berupa angka.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // perbesar array bila penuh
        if (jumlahMenu >= daftarMenu.length) {
            perbesarArrayMenu();
        }
        daftarMenu[jumlahMenu] = new Menu(nama, kategori.toUpperCase(), harga);
        jumlahMenu = jumlahMenu + 1;

        muatTabelMenu(modelMenu);
        muatTabelMenu(modelMenuKelola);

        JOptionPane.showMessageDialog(this,
                "Menu '" + nama + "' berhasil ditambahkan.",
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
    }

    // ============================================================
    //  METHOD : UBAH DATA MENU (nama, harga, kategori) (GUI)
    //  Struktur kontrol : switch-case untuk validasi kategori
    // ============================================================
    private void ubahMenuGUI() {
        int row = tblMenuKelola.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                    "Pilih menu pada tabel terlebih dahulu.",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int index = row;
        Menu menuDipilih = daftarMenu[index];

        // --- input nama baru ---
        String namaBaru = JOptionPane.showInputDialog(this,
                "Ubah nama menu\nNama lama: " + menuDipilih.getNama() + "\nMasukkan nama baru:",
                menuDipilih.getNama());
        if (namaBaru == null) return;
        namaBaru = namaBaru.trim();
        if (namaBaru.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama menu tidak boleh kosong.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // --- input kategori baru (switch-case untuk validasi dan tampilan) ---
        String kategoriInput = JOptionPane.showInputDialog(this,
                "Kategori (1=makanan, 2=minuman):\nKategori lama: " + menuDipilih.getKategori(),
                menuDipilih.getKategori());
        if (kategoriInput == null) return;
        kategoriInput = kategoriInput.trim();

        String kategoriBaru;
        // SWITCH-CASE : memetakan input kategori ke nilai standar
        switch (kategoriInput) {
            case "1":
            case "makanan":
            case "MAKANAN":
                kategoriBaru = "MAKANAN";
                break;
            case "2":
            case "minuman":
            case "MINUMAN":
                kategoriBaru = "MINUMAN";
                break;
            default:
                kategoriBaru = menuDipilih.getKategori(); // tidak diubah jika input tidak valid
                break;
        }

        // --- input harga baru ---
        String hargaStr = JOptionPane.showInputDialog(this,
                "Ubah harga menu\nHarga lama: " + Menu.formatRupiah(menuDipilih.getHarga()) + "\nMasukkan harga baru (Rp):",
                String.valueOf(menuDipilih.getHarga()));
        if (hargaStr == null) return;

        int hargaBaru;
        try {
            hargaBaru = Integer.parseInt(hargaStr.trim());
            if (hargaBaru <= 0) {
                JOptionPane.showMessageDialog(this, "Harga harus lebih dari 0.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Harga harus berupa angka.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // konfirmasi sebelum dieksekusi
        int konfirmasi = JOptionPane.showConfirmDialog(this,
                "Yakin ingin mengubah menu '" + menuDipilih.getNama() + "'\n"
                        + "dari: " + menuDipilih.getKategori() + ", " + Menu.formatRupiah(menuDipilih.getHarga()) + "\n"
                        + "menjadi: " + kategoriBaru + ", " + Menu.formatRupiah(hargaBaru) + "?",
                "Konfirmasi Ubah Menu", JOptionPane.YES_NO_OPTION);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            menuDipilih.setNama(namaBaru);
            menuDipilih.setKategori(kategoriBaru);
            menuDipilih.setHarga(hargaBaru);
            muatTabelMenu(modelMenu);
            muatTabelMenu(modelMenuKelola);
            JOptionPane.showMessageDialog(this,
                    "Menu '" + namaBaru + "' berhasil diubah.",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Perubahan dibatalkan.", "Dibatalkan", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // ============================================================
    //  METHOD : HAPUS MENU (GUI)
    // ============================================================
    private void hapusMenuGUI() {
        int row = tblMenuKelola.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                    "Pilih menu pada tabel terlebih dahulu.",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int index = row;
        Menu menuDipilih = daftarMenu[index];

        int konfirmasi = JOptionPane.showConfirmDialog(this,
                "Yakin ingin menghapus menu '" + menuDipilih.getNama() + "'?",
                "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            // pergeseran array ke kiri (analog perulangan while di versi console)
            int posisi = index;
            while (posisi < jumlahMenu - 1) {
                daftarMenu[posisi] = daftarMenu[posisi + 1];
                posisi = posisi + 1;
            }
            daftarMenu[jumlahMenu - 1] = null;
            jumlahMenu = jumlahMenu - 1;

            muatTabelMenu(modelMenu);
            muatTabelMenu(modelMenuKelola);
            JOptionPane.showMessageDialog(this,
                    "Menu '" + menuDipilih.getNama() + "' berhasil dihapus.",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Penghapusan dibatalkan.", "Dibatalkan", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // ============================================================
    //  METHOD : MENCARI INDEX MENU BERDASARKAN NAMA
    // ============================================================
    private int cariIndexMenu(String nama) {
        for (int i = 0; i < jumlahMenu; i++) {
            if (daftarMenu[i].getNama().equalsIgnoreCase(nama.trim())) {
                return i;
            }
        }
        return -1;
    }

    // ============================================================
    //  METHOD : MEMBESARKAN ARRAY MENU
    // ============================================================
    private void perbesarArrayMenu() {
        Menu[] arrayBaru = new Menu[daftarMenu.length * 2];
        for (int i = 0; i < jumlahMenu; i++) {
            arrayBaru[i] = daftarMenu[i];
        }
        daftarMenu = arrayBaru;
    }

    // ============================================================
    //  METHOD : TANGKAPAN LAYAR PENUH MONITOR (untuk demo)
    // ============================================================
    public static void captureScreen(String fileName) {
        try {
            Robot robot = new Robot();
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage screen = robot.createScreenCapture(screenRect);
            File out = new File(fileName);
            ImageIO.write(screen, "png", out);
            System.out.println("[CAPTURE] " + fileName);
        } catch (AWTException | IOException ex) {
            System.out.println("[CAPTURE GAGAL] " + fileName + " : " + ex.getMessage());
        }
    }

    // ============================================================
    //  METHOD DEMO (panggilan publik untuk runner)
    // ============================================================
    public void demoReset() {
        pesanan.kosongkan();
        muatTabelPesanan();
        areaStruk.setText("Belum ada struk.\n\n1. Pilih menu dari daftar di kiri.\n2. Isi jumlah, klik 'Tambah Pesanan'.\n3. Klik 'Hitung Total'.\n4. Klik 'Cetak Struk'.");
        lblTotalBayar.setText("TOTAL BAYAR: Rp0");
    }

    public void demoAddItems() {
        // Ayam Bakar x3
        int idxAyam = cariIndexMenu("Ayam Bakar");
        pesanan.tambahItem(daftarMenu[idxAyam], 3);
        // Es Teh x2
        int idxTeh = cariIndexMenu("Es Teh");
        pesanan.tambahItem(daftarMenu[idxTeh], 2);
        // Jus Alpukat x1
        int idxJus = cariIndexMenu("Jus Alpukat");
        pesanan.tambahItem(daftarMenu[idxJus], 1);
        muatTabelPesanan();
    }

    public void demoHitungTotal() {
        struk.hitung(pesanan);
        lblTotalBayar.setText("TOTAL BAYAR: " + Menu.formatRupiah(struk.getTotalBayar()));
    }

    public void demoCetakStruk() {
        String teks = struk.generateStrukText(pesanan);
        areaStruk.setText(teks);
    }

    public void demoSwitchToPemilik() {
        tabbedPane.setSelectedIndex(1);
        muatTabelMenu(modelMenuKelola);
    }

    public void demoTambahMenu() {
        if (jumlahMenu >= daftarMenu.length) {
            perbesarArrayMenu();
        }
        daftarMenu[jumlahMenu] = new Menu("Es Kelapa Muda", "MINUMAN", 12000);
        jumlahMenu = jumlahMenu + 1;
        muatTabelMenu(modelMenu);
        muatTabelMenu(modelMenuKelola);
    }

    public void demoUbahHarga() {
        int idxAyam = cariIndexMenu("Ayam Bakar");
        daftarMenu[idxAyam].setHarga(30000);
        muatTabelMenu(modelMenu);
        muatTabelMenu(modelMenuKelola);
    }

    public void demoHapusMenu() {
        int idxTeh = cariIndexMenu("Es Jeruk");
        if (idxTeh != -1) {
            int posisi = idxTeh;
            while (posisi < jumlahMenu - 1) {
                daftarMenu[posisi] = daftarMenu[posisi + 1];
                posisi = posisi + 1;
            }
            daftarMenu[jumlahMenu - 1] = null;
            jumlahMenu = jumlahMenu - 1;
            muatTabelMenu(modelMenu);
            muatTabelMenu(modelMenuKelola);
        }
    }

    public void demoSwitchToPelanggan() {
        tabbedPane.setSelectedIndex(0);
    }

    // ============================================================
    //  METHOD UTAMA
    // ============================================================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI());
    }
}
