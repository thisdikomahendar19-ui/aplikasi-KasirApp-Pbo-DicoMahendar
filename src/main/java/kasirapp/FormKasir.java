package kasirapp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.util.*;
import java.util.List;

public class FormKasir extends JFrame {

    // Data produk
    private List<Produk> daftarProduk = new ArrayList<>();
    private List<ItemBelanja> keranjang = new ArrayList<>();
    private NumberFormat rupiahFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    // Components
    private JComboBox<Produk> cbProduk;
    private JTextField txtQty, txtBayar, txtKembalian, txtSearch;
    private JLabel lblHarga, lblTotal, lblNomorStruk;
    private JTable tabelKeranjang;
    private DefaultTableModel modelTabel;
    private JButton btnTambah, btnHapus, btnBayar, btnBaru, btnHapusSemua;

    private int nomorStruk = 1;

    public FormKasir() {
        initDaftarProduk();
        initComponents();
        setTitle("🏪 Aplikasi Kasir - Toko Serba Ada");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initDaftarProduk() {
        daftarProduk.add(new Produk("P001", "Beras 5kg", 65000, 50));
        daftarProduk.add(new Produk("P002", "Minyak Goreng 1L", 18000, 80));
        daftarProduk.add(new Produk("P003", "Gula Pasir 1kg", 14000, 100));
        daftarProduk.add(new Produk("P004", "Tepung Terigu 1kg", 12000, 60));
        daftarProduk.add(new Produk("P005", "Telur Ayam 1kg", 28000, 40));
        daftarProduk.add(new Produk("P006", "Indomie Goreng", 3500, 200));
        daftarProduk.add(new Produk("P007", "Sabun Mandi Lifebuoy", 5000, 150));
        daftarProduk.add(new Produk("P008", "Shampo Pantene 170ml", 22000, 70));
        daftarProduk.add(new Produk("P009", "Aqua Botol 600ml", 4000, 300));
        daftarProduk.add(new Produk("P010", "Susu Ultra 1L", 18500, 90));
        daftarProduk.add(new Produk("P011", "Roti Tawar Sari Roti", 16000, 45));
        daftarProduk.add(new Produk("P012", "Kopi Kapal Api 165g", 13500, 110));
    }

    private void initComponents() {
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(245, 247, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // === HEADER ===
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(41, 128, 185));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));

        JLabel lblJudul = new JLabel("🏪 SISTEM KASIR TOKO SERBA ADA");
        lblJudul.setFont(new Font("Arial", Font.BOLD, 20));
        lblJudul.setForeground(Color.WHITE);

        lblNomorStruk = new JLabel("No. Struk: #001");
        lblNomorStruk.setFont(new Font("Arial", Font.PLAIN, 13));
        lblNomorStruk.setForeground(new Color(200, 230, 255));

        JLabel lblTanggal = new JLabel(new java.text.SimpleDateFormat("dd MMMM yyyy, HH:mm").format(new Date()));
        lblTanggal.setFont(new Font("Arial", Font.PLAIN, 12));
        lblTanggal.setForeground(new Color(200, 230, 255));

        JPanel headerRight = new JPanel(new GridLayout(2, 1));
        headerRight.setOpaque(false);
        headerRight.add(lblNomorStruk);
        headerRight.add(lblTanggal);

        headerPanel.add(lblJudul, BorderLayout.WEST);
        headerPanel.add(headerRight, BorderLayout.EAST);

        // === LEFT PANEL - INPUT PRODUK ===
        JPanel leftPanel = new JPanel(new BorderLayout(5, 5));
        leftPanel.setBackground(new Color(245, 247, 250));
        leftPanel.setPreferredSize(new Dimension(320, 0));

        // Panel pilih produk
        JPanel panelInput = new JPanel(new GridBagLayout());
        panelInput.setBackground(Color.WHITE);
        panelInput.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Title
        JLabel lblInputTitle = new JLabel("📦 TAMBAH PRODUK");
        lblInputTitle.setFont(new Font("Arial", Font.BOLD, 14));
        lblInputTitle.setForeground(new Color(41, 128, 185));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panelInput.add(lblInputTitle, gbc);

        // Separator
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(220, 220, 220));
        gbc.gridy = 1;
        panelInput.add(sep, gbc);

        // Pilih Produk
        gbc.gridwidth = 1;
        gbc.gridy = 2; gbc.gridx = 0;
        panelInput.add(new JLabel("Pilih Produk:"), gbc);

        cbProduk = new JComboBox<>(daftarProduk.toArray(new Produk[0]));
        cbProduk.setFont(new Font("Arial", Font.PLAIN, 13));
        cbProduk.addActionListener(e -> updateHargaProduk());
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panelInput.add(cbProduk, gbc);

        // Harga
        gbc.gridwidth = 1;
        gbc.gridy = 4; gbc.gridx = 0;
        panelInput.add(new JLabel("Harga Satuan:"), gbc);

        lblHarga = new JLabel("Rp 0");
        lblHarga.setFont(new Font("Arial", Font.BOLD, 14));
        lblHarga.setForeground(new Color(39, 174, 96));
        gbc.gridx = 1;
        panelInput.add(lblHarga, gbc);

        // Qty
        gbc.gridy = 5; gbc.gridx = 0; gbc.gridwidth = 1;
        panelInput.add(new JLabel("Jumlah (Qty):"), gbc);

        txtQty = new JTextField("1");
        txtQty.setFont(new Font("Arial", Font.PLAIN, 14));
        txtQty.setHorizontalAlignment(JTextField.CENTER);
        gbc.gridx = 1;
        panelInput.add(txtQty, gbc);

        // Tombol tambah
        btnTambah = createButton("+ Tambah ke Keranjang", new Color(39, 174, 96));
        btnTambah.addActionListener(e -> tambahKeKeranjang());
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 5, 5, 5);
        panelInput.add(btnTambah, gbc);

        // Panel pembayaran
        JPanel panelBayar = new JPanel(new GridBagLayout());
        panelBayar.setBackground(Color.WHITE);
        panelBayar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        gbc2.insets = new Insets(5, 5, 5, 5);

        JLabel lblBayarTitle = new JLabel("💰 PEMBAYARAN");
        lblBayarTitle.setFont(new Font("Arial", Font.BOLD, 14));
        lblBayarTitle.setForeground(new Color(41, 128, 185));
        gbc2.gridx = 0; gbc2.gridy = 0; gbc2.gridwidth = 2;
        panelBayar.add(lblBayarTitle, gbc2);

        JSeparator sep2 = new JSeparator();
        gbc2.gridy = 1;
        panelBayar.add(sep2, gbc2);

        // Total
        gbc2.gridy = 2; gbc2.gridwidth = 1; gbc2.gridx = 0;
        JLabel lblTotalLabel = new JLabel("Total Belanja:");
        lblTotalLabel.setFont(new Font("Arial", Font.BOLD, 13));
        panelBayar.add(lblTotalLabel, gbc2);

        lblTotal = new JLabel("Rp 0");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 18));
        lblTotal.setForeground(new Color(231, 76, 60));
        gbc2.gridx = 1;
        panelBayar.add(lblTotal, gbc2);

        // Uang bayar
        gbc2.gridy = 3; gbc2.gridx = 0;
        panelBayar.add(new JLabel("Uang Bayar (Rp):"), gbc2);

        txtBayar = new JTextField();
        txtBayar.setFont(new Font("Arial", Font.PLAIN, 14));
        txtBayar.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) { hitungKembalian(); }
        });
        gbc2.gridx = 1;
        panelBayar.add(txtBayar, gbc2);

        // Kembalian
        gbc2.gridy = 4; gbc2.gridx = 0;
        JLabel lblKembalianLabel = new JLabel("Kembalian:");
        lblKembalianLabel.setFont(new Font("Arial", Font.BOLD, 13));
        panelBayar.add(lblKembalianLabel, gbc2);

        txtKembalian = new JTextField("Rp 0");
        txtKembalian.setFont(new Font("Arial", Font.BOLD, 14));
        txtKembalian.setForeground(new Color(39, 174, 96));
        txtKembalian.setEditable(false);
        txtKembalian.setBackground(new Color(232, 245, 233));
        gbc2.gridx = 1;
        panelBayar.add(txtKembalian, gbc2);

        // Tombol bayar
        btnBayar = createButton("💳 PROSES PEMBAYARAN", new Color(41, 128, 185));
        btnBayar.setFont(new Font("Arial", Font.BOLD, 14));
        btnBayar.addActionListener(e -> prosesPembayaran());
        gbc2.gridx = 0; gbc2.gridy = 5; gbc2.gridwidth = 2;
        gbc2.insets = new Insets(10, 5, 5, 5);
        panelBayar.add(btnBayar, gbc2);

        btnBaru = createButton("🔄 Transaksi Baru", new Color(149, 165, 166));
        btnBaru.addActionListener(e -> transaksiBaruConfirm());
        gbc2.gridy = 6;
        panelBayar.add(btnBaru, gbc2);

        leftPanel.add(panelInput, BorderLayout.NORTH);
        leftPanel.add(panelBayar, BorderLayout.CENTER);

        // === RIGHT PANEL - TABEL KERANJANG ===
        JPanel rightPanel = new JPanel(new BorderLayout(5, 5));
        rightPanel.setBackground(new Color(245, 247, 250));

        JPanel tabelHeader = new JPanel(new BorderLayout());
        tabelHeader.setBackground(Color.WHITE);
        tabelHeader.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel lblKeranjangTitle = new JLabel("🛒 KERANJANG BELANJA");
        lblKeranjangTitle.setFont(new Font("Arial", Font.BOLD, 14));
        lblKeranjangTitle.setForeground(new Color(41, 128, 185));

        btnHapus = createButton("🗑 Hapus Item", new Color(231, 76, 60));
        btnHapus.addActionListener(e -> hapusItem());

        btnHapusSemua = createButton("❌ Kosongkan", new Color(192, 57, 43));
        btnHapusSemua.addActionListener(e -> kosongkanKeranjang());

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        btnPanel.setOpaque(false);
        btnPanel.add(btnHapus);
        btnPanel.add(btnHapusSemua);

        tabelHeader.add(lblKeranjangTitle, BorderLayout.WEST);
        tabelHeader.add(btnPanel, BorderLayout.EAST);

        // Tabel
        String[] kolom = {"No", "Kode", "Nama Produk", "Harga Satuan", "Qty", "Subtotal"};
        modelTabel = new DefaultTableModel(kolom, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        tabelKeranjang = new JTable(modelTabel);
        tabelKeranjang.setFont(new Font("Arial", Font.PLAIN, 13));
        tabelKeranjang.setRowHeight(28);
        tabelKeranjang.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabelKeranjang.getTableHeader().setBackground(new Color(41, 128, 185));
        tabelKeranjang.getTableHeader().setForeground(Color.WHITE);
        tabelKeranjang.setSelectionBackground(new Color(174, 214, 241));
        tabelKeranjang.setGridColor(new Color(230, 230, 230));
        tabelKeranjang.setShowHorizontalLines(true);

        // Set lebar kolom
        int[] colWidths = {35, 55, 200, 110, 50, 110};
        for (int i = 0; i < colWidths.length; i++) {
            tabelKeranjang.getColumnModel().getColumn(i).setPreferredWidth(colWidths[i]);
        }

        JScrollPane scrollPane = new JScrollPane(tabelKeranjang);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        rightPanel.add(tabelHeader, BorderLayout.NORTH);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        add(mainPanel);

        // === MENU BAR ===
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(30, 100, 160));
        menuBar.setBorder(BorderFactory.createEmptyBorder());

        JMenu menuFile = buatMenu("📋 Menu");
        JMenuItem miProduk = new JMenuItem("📦 Data Produk");
        miProduk.addActionListener(e -> new FormDataProduk().setVisible(true));
        JMenuItem miLaporan = new JMenuItem("📊 Laporan Penjualan");
        miLaporan.addActionListener(e -> new FormLaporan().setVisible(true));
        JMenuItem miPengaturan = new JMenuItem("⚙ Pengaturan");
        miPengaturan.addActionListener(e -> new FormPengaturan().setVisible(true));
        JMenuItem miKeluar = new JMenuItem("🚪 Logout & Keluar");
        miKeluar.addActionListener(e -> {
            int c = JOptionPane.showConfirmDialog(this, "Yakin ingin keluar?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (c == JOptionPane.YES_OPTION) System.exit(0);
        });
        menuFile.add(miProduk);
        menuFile.add(miLaporan);
        menuFile.addSeparator();
        menuFile.add(miPengaturan);
        menuFile.addSeparator();
        menuFile.add(miKeluar);

        JMenu menuBantuan = buatMenu("❓ Bantuan");
        JMenuItem miTentang = new JMenuItem("ℹ Tentang Aplikasi");
        miTentang.addActionListener(e -> JOptionPane.showMessageDialog(this,
            "🏪 Aplikasi Kasir v1.0\n\nDibuat dengan Java Swing\n© 2024 Toko Serba Ada",
            "Tentang", JOptionPane.INFORMATION_MESSAGE));
        menuBantuan.add(miTentang);

        menuBar.add(menuFile);
        menuBar.add(menuBantuan);
        setJMenuBar(menuBar);

        updateHargaProduk();
    }

    private JMenu buatMenu(String text) {
        JMenu menu = new JMenu(text);
        menu.setFont(new Font("Arial", Font.BOLD, 12));
        menu.setForeground(Color.WHITE);
        return menu;
    }

    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(0, 35));
        return btn;
    }

    private void updateHargaProduk() {
        Produk p = (Produk) cbProduk.getSelectedItem();
        if (p != null) lblHarga.setText(rupiahFormat.format(p.getHarga()));
    }

    private void tambahKeKeranjang() {
        Produk produk = (Produk) cbProduk.getSelectedItem();
        if (produk == null) return;

        int qty;
        try {
            qty = Integer.parseInt(txtQty.getText().trim());
            if (qty <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Jumlah qty harus berupa angka positif!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (qty > produk.getStok()) {
            JOptionPane.showMessageDialog(this, "Stok tidak cukup! Stok tersedia: " + produk.getStok(), "Stok Kurang", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Cek apakah sudah ada di keranjang
        for (ItemBelanja item : keranjang) {
            if (item.getProduk().getKode().equals(produk.getKode())) {
                int newQty = item.getQty() + qty;
                if (newQty > produk.getStok()) {
                    JOptionPane.showMessageDialog(this, "Total qty melebihi stok tersedia!", "Stok Kurang", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                item.setQty(newQty);
                refreshTabel();
                return;
            }
        }

        keranjang.add(new ItemBelanja(produk, qty));
        refreshTabel();
        txtQty.setText("1");
    }

    private void refreshTabel() {
        modelTabel.setRowCount(0);
        double total = 0;
        for (int i = 0; i < keranjang.size(); i++) {
            ItemBelanja item = keranjang.get(i);
            modelTabel.addRow(new Object[]{
                i + 1,
                item.getProduk().getKode(),
                item.getProduk().getNama(),
                rupiahFormat.format(item.getProduk().getHarga()),
                item.getQty(),
                rupiahFormat.format(item.getSubtotal())
            });
            total += item.getSubtotal();
        }
        lblTotal.setText(rupiahFormat.format(total));
        hitungKembalian();
    }

    private void hitungKembalian() {
        try {
            double bayar = Double.parseDouble(txtBayar.getText().replaceAll("[^0-9]", ""));
            double total = getTotalBelanja();
            double kembalian = bayar - total;
            if (kembalian >= 0) {
                txtKembalian.setText(rupiahFormat.format(kembalian));
                txtKembalian.setForeground(new Color(39, 174, 96));
            } else {
                txtKembalian.setText("Kurang: " + rupiahFormat.format(Math.abs(kembalian)));
                txtKembalian.setForeground(new Color(231, 76, 60));
            }
        } catch (NumberFormatException e) {
            txtKembalian.setText(rupiahFormat.format(0));
        }
    }

    private double getTotalBelanja() {
        return keranjang.stream().mapToDouble(ItemBelanja::getSubtotal).sum();
    }

    private void hapusItem() {
        int row = tabelKeranjang.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Pilih item yang ingin dihapus!", "Perhatian", JOptionPane.WARNING_MESSAGE);
            return;
        }
        keranjang.remove(row);
        refreshTabel();
    }

    private void kosongkanKeranjang() {
        if (keranjang.isEmpty()) return;
        int confirm = JOptionPane.showConfirmDialog(this, "Kosongkan semua item keranjang?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            keranjang.clear();
            refreshTabel();
            txtBayar.setText("");
        }
    }

    private void prosesPembayaran() {
        if (keranjang.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Keranjang masih kosong!", "Perhatian", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double bayar;
        try {
            bayar = Double.parseDouble(txtBayar.getText().replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Masukkan jumlah uang bayar!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double total = getTotalBelanja();
        if (bayar < total) {
            JOptionPane.showMessageDialog(this, "Uang bayar tidak mencukupi!\nKurang: " + rupiahFormat.format(total - bayar), "Pembayaran Gagal", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Tampilkan struk
        tampilkanStruk(bayar, total);
    }

    private void tampilkanStruk(double bayar, double total) {
        StringBuilder struk = new StringBuilder();
        struk.append("╔══════════════════════════════════════╗\n");
        struk.append("║       TOKO SERBA ADA                 ║\n");
        struk.append("║     Jl. Contoh No. 123, Jakarta      ║\n");
        struk.append("║       Telp: 021-12345678             ║\n");
        struk.append("╠══════════════════════════════════════╣\n");
        struk.append(String.format("  No. Struk : #%03d\n", nomorStruk));
        struk.append("  Tanggal   : ").append(new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date())).append("\n");
        struk.append("  Kasir     : Admin\n");
        struk.append("══════════════════════════════════════\n");
        struk.append(String.format("  %-20s %5s %8s\n", "Produk", "Qty", "Subtotal"));
        struk.append("--------------------------------------\n");

        for (ItemBelanja item : keranjang) {
            struk.append(String.format("  %-20s\n", item.getProduk().getNama()));
            struk.append(String.format("  @%-19s %5d %,10.0f\n",
                "Rp " + String.format("%,.0f", item.getProduk().getHarga()),
                item.getQty(), item.getSubtotal()));
        }

        struk.append("══════════════════════════════════════\n");
        struk.append(String.format("  TOTAL        : Rp %,15.0f\n", total));
        struk.append(String.format("  BAYAR        : Rp %,15.0f\n", bayar));
        struk.append(String.format("  KEMBALIAN    : Rp %,15.0f\n", bayar - total));
        struk.append("══════════════════════════════════════\n");
        struk.append("      Terima kasih telah berbelanja!\n");
        struk.append("         Selamat datang kembali!\n");
        struk.append("╚══════════════════════════════════════╝");

        JTextArea area = new JTextArea(struk.toString());
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));
        area.setEditable(false);
        area.setBackground(new Color(255, 255, 240));

        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(420, 400));

        JOptionPane.showMessageDialog(this, scroll, "✅ Struk Pembayaran #" + String.format("%03d", nomorStruk), JOptionPane.INFORMATION_MESSAGE);

        // Reset transaksi
        nomorStruk++;
        lblNomorStruk.setText("No. Struk: #" + String.format("%03d", nomorStruk));
        keranjang.clear();
        refreshTabel();
        txtBayar.setText("");
        txtKembalian.setText(rupiahFormat.format(0));
    }

    private void transaksiBaruConfirm() {
        if (!keranjang.isEmpty()) {
            int confirm = JOptionPane.showConfirmDialog(this, "Batalkan transaksi dan mulai yang baru?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;
        }
        keranjang.clear();
        refreshTabel();
        txtBayar.setText("");
        txtKembalian.setText(rupiahFormat.format(0));
    }
}

