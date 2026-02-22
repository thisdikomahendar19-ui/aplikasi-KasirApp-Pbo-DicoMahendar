package kasirapp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FormDataProduk extends JFrame {

    private JTextField txtKode, txtNama, txtHarga, txtStok, txtCari;
    private JTable tabelProduk;
    private DefaultTableModel modelTabel;
    private JButton btnTambah, btnEdit, btnHapus, btnSimpan, btnBatal, btnCari;
    private JLabel lblStatus;
    private NumberFormat rupiahFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    private List<Produk> daftarProduk = new ArrayList<>();
    private boolean modeEdit = false;
    private int indexEdit = -1;

    public FormDataProduk() {
        initDummyData();
        initComponents();
        setTitle("📦 Manajemen Data Produk");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(850, 580);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initDummyData() {
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
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(245, 247, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // === HEADER ===
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(39, 174, 96));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        JLabel lblJudul = new JLabel("📦 MANAJEMEN DATA PRODUK");
        lblJudul.setFont(new Font("Arial", Font.BOLD, 18));
        lblJudul.setForeground(Color.WHITE);
        headerPanel.add(lblJudul, BorderLayout.WEST);

        // === FORM INPUT ===
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 8, 5, 8);
        g.fill = GridBagConstraints.HORIZONTAL;

        addFormField(formPanel, g, "Kode Produk:", 0);
        txtKode = createTextField();
        g.gridx = 1; g.gridy = 0; g.weightx = 1;
        formPanel.add(txtKode, g);

        addFormField(formPanel, g, "Nama Produk:", 1);
        txtNama = createTextField();
        g.gridx = 1; g.gridy = 1;
        formPanel.add(txtNama, g);

        addFormField(formPanel, g, "Harga (Rp):", 2);
        txtHarga = createTextField();
        g.gridx = 1; g.gridy = 2;
        formPanel.add(txtHarga, g);

        addFormField(formPanel, g, "Stok:", 3);
        txtStok = createTextField();
        g.gridx = 1; g.gridy = 3;
        formPanel.add(txtStok, g);

        // Tombol aksi form
        JPanel btnFormPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        btnFormPanel.setBackground(Color.WHITE);

        btnSimpan = buatTombol("💾 Simpan", new Color(39, 174, 96));
        btnSimpan.addActionListener(e -> simpanProduk());

        btnBatal = buatTombol("✖ Batal", new Color(149, 165, 166));
        btnBatal.addActionListener(e -> resetForm());

        btnFormPanel.add(btnSimpan);
        btnFormPanel.add(btnBatal);

        g.gridx = 0; g.gridy = 4; g.gridwidth = 2; g.insets = new Insets(10, 8, 5, 8);
        formPanel.add(btnFormPanel, g);

        // Status label
        lblStatus = new JLabel("  Mode: Tambah Produk Baru");
        lblStatus.setFont(new Font("Arial", Font.ITALIC, 11));
        lblStatus.setForeground(new Color(100, 100, 100));
        g.gridy = 5; g.insets = new Insets(0, 8, 5, 8);
        formPanel.add(lblStatus, g);

        // === TABEL PANEL ===
        JPanel tabelPanel = new JPanel(new BorderLayout(5, 8));
        tabelPanel.setBackground(Color.WHITE);
        tabelPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        // Search & aksi tabel
        JPanel topTabel = new JPanel(new BorderLayout(10, 0));
        topTabel.setBackground(Color.WHITE);

        JPanel cariPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        cariPanel.setBackground(Color.WHITE);
        txtCari = createTextField();
        txtCari.setPreferredSize(new Dimension(200, 30));
        txtCari.putClientProperty("hint", "Cari produk...");
        txtCari.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) { cariProduk(); }
        });
        btnCari = buatTombol("🔍 Cari", new Color(52, 152, 219));
        btnCari.addActionListener(e -> cariProduk());
        cariPanel.add(new JLabel("Cari:"));
        cariPanel.add(txtCari);
        cariPanel.add(btnCari);

        JPanel aksiTabel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        aksiTabel.setBackground(Color.WHITE);
        btnTambah = buatTombol("➕ Tambah Baru", new Color(39, 174, 96));
        btnTambah.addActionListener(e -> resetForm());
        btnEdit = buatTombol("✏ Edit", new Color(243, 156, 18));
        btnEdit.addActionListener(e -> editProduk());
        btnHapus = buatTombol("🗑 Hapus", new Color(231, 76, 60));
        btnHapus.addActionListener(e -> hapusProduk());
        aksiTabel.add(btnTambah);
        aksiTabel.add(btnEdit);
        aksiTabel.add(btnHapus);

        topTabel.add(cariPanel, BorderLayout.WEST);
        topTabel.add(aksiTabel, BorderLayout.EAST);

        // Tabel
        String[] kolom = {"No", "Kode", "Nama Produk", "Harga", "Stok", "Status"};
        modelTabel = new DefaultTableModel(kolom, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        tabelProduk = new JTable(modelTabel);
        tabelProduk.setFont(new Font("Arial", Font.PLAIN, 13));
        tabelProduk.setRowHeight(28);
        tabelProduk.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabelProduk.getTableHeader().setBackground(new Color(39, 174, 96));
        tabelProduk.getTableHeader().setForeground(Color.WHITE);
        tabelProduk.setSelectionBackground(new Color(200, 245, 218));
        tabelProduk.setGridColor(new Color(230, 230, 230));

        int[] widths = {35, 60, 220, 120, 60, 80};
        for (int i = 0; i < widths.length; i++)
            tabelProduk.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);

        JScrollPane scroll = new JScrollPane(tabelProduk);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        tabelPanel.add(topTabel, BorderLayout.NORTH);
        tabelPanel.add(scroll, BorderLayout.CENTER);

        // Klik baris tabel untuk isi form
        tabelProduk.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) editProduk();
            }
        });

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(new Color(245, 247, 250));
        centerPanel.add(formPanel, BorderLayout.NORTH);
        centerPanel.add(tabelPanel, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel);
        refreshTabel(daftarProduk);
    }

    private void addFormField(JPanel panel, GridBagConstraints g, String label, int row) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.BOLD, 12));
        g.gridx = 0; g.gridy = row; g.gridwidth = 1; g.weightx = 0;
        g.insets = new Insets(5, 8, 5, 8);
        panel.add(lbl, g);
    }

    private JTextField createTextField() {
        JTextField tf = new JTextField();
        tf.setFont(new Font("Arial", Font.PLAIN, 13));
        tf.setPreferredSize(new Dimension(0, 32));
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        return tf;
    }

    private JButton buatTombol(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(btn.getPreferredSize().width + 10, 32));
        return btn;
    }

    private void refreshTabel(List<Produk> list) {
        modelTabel.setRowCount(0);
        for (int i = 0; i < list.size(); i++) {
            Produk p = list.get(i);
            String status = p.getStok() > 10 ? "✅ Aman" : p.getStok() > 0 ? "⚠ Sedikit" : "❌ Habis";
            modelTabel.addRow(new Object[]{i + 1, p.getKode(), p.getNama(), rupiahFormat.format(p.getHarga()), p.getStok(), status});
        }
    }

    private void simpanProduk() {
        String kode = txtKode.getText().trim();
        String nama = txtNama.getText().trim();
        String hargaStr = txtHarga.getText().trim();
        String stokStr = txtStok.getText().trim();

        if (kode.isEmpty() || nama.isEmpty() || hargaStr.isEmpty() || stokStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double harga;
        int stok;
        try {
            harga = Double.parseDouble(hargaStr);
            stok = Integer.parseInt(stokStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Harga dan stok harus berupa angka!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (modeEdit && indexEdit >= 0) {
            daftarProduk.set(indexEdit, new Produk(kode, nama, harga, stok));
            JOptionPane.showMessageDialog(this, "Produk berhasil diperbarui! ✅", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Cek duplikat kode
            for (Produk p : daftarProduk) {
                if (p.getKode().equalsIgnoreCase(kode)) {
                    JOptionPane.showMessageDialog(this, "Kode produk sudah ada!", "Duplikat", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            daftarProduk.add(new Produk(kode, nama, harga, stok));
            JOptionPane.showMessageDialog(this, "Produk baru berhasil ditambahkan! ✅", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        }

        resetForm();
        refreshTabel(daftarProduk);
    }

    private void editProduk() {
        int row = tabelProduk.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Pilih produk yang ingin diedit!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Produk p = daftarProduk.get(row);
        txtKode.setText(p.getKode());
        txtNama.setText(p.getNama());
        txtHarga.setText(String.valueOf(p.getHarga()));
        txtStok.setText(String.valueOf(p.getStok()));
        modeEdit = true;
        indexEdit = row;
        txtKode.setEditable(false);
        lblStatus.setText("  Mode: Edit Produk - " + p.getNama());
        lblStatus.setForeground(new Color(243, 156, 18));
    }

    private void hapusProduk() {
        int row = tabelProduk.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Pilih produk yang ingin dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
            "Hapus produk: " + daftarProduk.get(row).getNama() + "?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            daftarProduk.remove(row);
            refreshTabel(daftarProduk);
            resetForm();
        }
    }

    private void cariProduk() {
        String kata = txtCari.getText().trim().toLowerCase();
        if (kata.isEmpty()) { refreshTabel(daftarProduk); return; }
        List<Produk> hasil = new ArrayList<>();
        for (Produk p : daftarProduk) {
            if (p.getNama().toLowerCase().contains(kata) || p.getKode().toLowerCase().contains(kata))
                hasil.add(p);
        }
        refreshTabel(hasil);
    }

    private void resetForm() {
        txtKode.setText(""); txtNama.setText(""); txtHarga.setText(""); txtStok.setText("");
        txtKode.setEditable(true);
        modeEdit = false; indexEdit = -1;
        lblStatus.setText("  Mode: Tambah Produk Baru");
        lblStatus.setForeground(new Color(100, 100, 100));
        tabelProduk.clearSelection();
    }
}
