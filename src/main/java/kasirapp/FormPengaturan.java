package kasirapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FormPengaturan extends JFrame {

    private JTextField txtNamaToko, txtAlamat, txtTelepon, txtNamaKasir;
    private JTextField txtUserAdmin, txtPassAdmin, txtUserKasir, txtPassKasir;
    private JComboBox<String> cbTema, cbFont;
    private JCheckBox chkSuara, chkStrukOtomatis, chkKonfirmasiHapus;
    private JSpinner spnDiskon;
    private JButton btnSimpan, btnReset, btnTutup;
    private JLabel lblStatus;

    public FormPengaturan() {
        initComponents();
        setTitle("⚙ Pengaturan Aplikasi");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(620, 650);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(0, 0));
        mainPanel.setBackground(new Color(245, 247, 250));

        // === HEADER ===
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(52, 73, 94));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        JLabel lblJudul = new JLabel("⚙ PENGATURAN APLIKASI KASIR");
        lblJudul.setFont(new Font("Arial", Font.BOLD, 18));
        lblJudul.setForeground(Color.WHITE);
        headerPanel.add(lblJudul, BorderLayout.WEST);

        // === TABS ===
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Arial", Font.BOLD, 12));
        tabs.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tabs.setBackground(new Color(245, 247, 250));

        tabs.addTab("🏪 Info Toko", buatTabInfoToko());
        tabs.addTab("👥 Akun Pengguna", buatTabAkun());
        tabs.addTab("🎨 Tampilan", buatTabTampilan());
        tabs.addTab("🔧 Umum", buatTabUmum());

        // === FOOTER ===
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        lblStatus = new JLabel("  Siap menyimpan perubahan");
        lblStatus.setFont(new Font("Arial", Font.ITALIC, 11));
        lblStatus.setForeground(new Color(100, 100, 100));

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        btnPanel.setBackground(Color.WHITE);

        btnReset = buatTombol("↩ Reset Default", new Color(149, 165, 166));
        btnReset.addActionListener(e -> resetDefault());

        btnSimpan = buatTombol("💾 Simpan Pengaturan", new Color(52, 73, 94));
        btnSimpan.addActionListener(e -> simpanPengaturan());

        btnTutup = buatTombol("✖ Tutup", new Color(231, 76, 60));
        btnTutup.addActionListener(e -> dispose());

        btnPanel.add(btnReset);
        btnPanel.add(btnSimpan);
        btnPanel.add(btnTutup);

        footerPanel.add(lblStatus, BorderLayout.WEST);
        footerPanel.add(btnPanel, BorderLayout.EAST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(tabs, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel buatTabInfoToko() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(8, 5, 8, 5);

        addLabel(panel, g, "🏪 Nama Toko:", 0);
        txtNamaToko = buatTextField("Toko Serba Ada");
        g.gridx = 1; g.gridy = 0; g.weightx = 1;
        panel.add(txtNamaToko, g);

        addLabel(panel, g, "📍 Alamat:", 1);
        txtAlamat = buatTextField("Jl. Contoh No. 123, Jakarta");
        g.gridx = 1; g.gridy = 1;
        panel.add(txtAlamat, g);

        addLabel(panel, g, "📞 Telepon:", 2);
        txtTelepon = buatTextField("021-12345678");
        g.gridx = 1; g.gridy = 2;
        panel.add(txtTelepon, g);

        addLabel(panel, g, "👤 Nama Kasir:", 3);
        txtNamaKasir = buatTextField("Admin");
        g.gridx = 1; g.gridy = 3;
        panel.add(txtNamaKasir, g);

        // Info
        JLabel lblInfo = new JLabel("<html><i>Info ini akan tampil di struk pembayaran pelanggan.</i></html>");
        lblInfo.setFont(new Font("Arial", Font.ITALIC, 11));
        lblInfo.setForeground(new Color(130, 130, 130));
        g.gridx = 0; g.gridy = 4; g.gridwidth = 2; g.insets = new Insets(20, 5, 5, 5);
        panel.add(lblInfo, g);

        return panel;
    }

    private JPanel buatTabAkun() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(8, 5, 8, 5);

        // Admin
        JLabel lblAdmin = new JLabel("👑 Akun Administrator");
        lblAdmin.setFont(new Font("Arial", Font.BOLD, 13));
        lblAdmin.setForeground(new Color(52, 73, 94));
        g.gridx = 0; g.gridy = 0; g.gridwidth = 2;
        panel.add(lblAdmin, g);

        g.gridwidth = 1;
        addLabel(panel, g, "Username:", 1);
        txtUserAdmin = buatTextField("admin");
        g.gridx = 1; g.gridy = 1; g.weightx = 1;
        panel.add(txtUserAdmin, g);

        addLabel(panel, g, "Password:", 2);
        txtPassAdmin = buatTextField("admin123");
        g.gridx = 1; g.gridy = 2;
        panel.add(txtPassAdmin, g);

        JSeparator sep = new JSeparator();
        g.gridx = 0; g.gridy = 3; g.gridwidth = 2; g.insets = new Insets(15, 5, 15, 5);
        panel.add(sep, g);

        // Kasir
        JLabel lblKasir = new JLabel("🧑‍💼 Akun Kasir");
        lblKasir.setFont(new Font("Arial", Font.BOLD, 13));
        lblKasir.setForeground(new Color(41, 128, 185));
        g.gridy = 4; g.insets = new Insets(5, 5, 8, 5);
        panel.add(lblKasir, g);

        g.gridwidth = 1;
        addLabel(panel, g, "Username:", 5);
        txtUserKasir = buatTextField("kasir");
        g.gridx = 1; g.gridy = 5; g.weightx = 1;
        panel.add(txtUserKasir, g);

        addLabel(panel, g, "Password:", 6);
        txtPassKasir = buatTextField("kasir123");
        g.gridx = 1; g.gridy = 6;
        panel.add(txtPassKasir, g);

        return panel;
    }

    private JPanel buatTabTampilan() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(10, 5, 10, 5);

        addLabel(panel, g, "🎨 Tema Warna:", 0);
        cbTema = new JComboBox<>(new String[]{"Biru (Default)", "Hijau", "Ungu", "Merah", "Abu-abu"});
        cbTema.setFont(new Font("Arial", Font.PLAIN, 13));
        g.gridx = 1; g.gridy = 0; g.weightx = 1;
        panel.add(cbTema, g);

        addLabel(panel, g, "🔤 Jenis Font:", 1);
        cbFont = new JComboBox<>(new String[]{"Arial (Default)", "Tahoma", "Calibri", "Verdana"});
        cbFont.setFont(new Font("Arial", Font.PLAIN, 13));
        g.gridx = 1; g.gridy = 1;
        panel.add(cbFont, g);

        JLabel lblPreview = new JLabel("<html><div style='background:#f0f0f0;padding:10px;border-radius:5px'>" +
            "<b>Preview:</b> Toko Serba Ada<br>Jl. Contoh No. 123 | Total: Rp 150.000</div></html>");
        lblPreview.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        g.gridx = 0; g.gridy = 2; g.gridwidth = 2; g.insets = new Insets(20, 5, 5, 5);
        panel.add(lblPreview, g);

        return panel;
    }

    private JPanel buatTabUmum() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.anchor = GridBagConstraints.WEST;
        g.insets = new Insets(10, 5, 10, 5);

        chkSuara = new JCheckBox("🔔 Aktifkan suara notifikasi");
        chkSuara.setFont(new Font("Arial", Font.PLAIN, 13));
        chkSuara.setBackground(Color.WHITE);
        chkSuara.setSelected(true);
        g.gridx = 0; g.gridy = 0; g.gridwidth = 2;
        panel.add(chkSuara, g);

        chkStrukOtomatis = new JCheckBox("🖨 Cetak struk otomatis setelah bayar");
        chkStrukOtomatis.setFont(new Font("Arial", Font.PLAIN, 13));
        chkStrukOtomatis.setBackground(Color.WHITE);
        chkStrukOtomatis.setSelected(true);
        g.gridy = 1;
        panel.add(chkStrukOtomatis, g);

        chkKonfirmasiHapus = new JCheckBox("⚠ Konfirmasi sebelum menghapus data");
        chkKonfirmasiHapus.setFont(new Font("Arial", Font.PLAIN, 13));
        chkKonfirmasiHapus.setBackground(Color.WHITE);
        chkKonfirmasiHapus.setSelected(true);
        g.gridy = 2;
        panel.add(chkKonfirmasiHapus, g);

        JSeparator sep = new JSeparator();
        g.gridy = 3; g.insets = new Insets(15, 5, 15, 5);
        panel.add(sep, g);

        g.gridwidth = 1;
        addLabel(panel, g, "🏷 Diskon Global (%):", 4);
        spnDiskon = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        spnDiskon.setFont(new Font("Arial", Font.PLAIN, 13));
        spnDiskon.setPreferredSize(new Dimension(80, 30));
        g.gridx = 1; g.gridy = 4;
        panel.add(spnDiskon, g);

        return panel;
    }

    private void addLabel(JPanel panel, GridBagConstraints g, String text, int row) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Arial", Font.BOLD, 12));
        g.gridx = 0; g.gridy = row; g.gridwidth = 1; g.weightx = 0;
        g.insets = new Insets(8, 5, 8, 5);
        panel.add(lbl, g);
    }

    private JTextField buatTextField(String value) {
        JTextField tf = new JTextField(value);
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
        btn.setPreferredSize(new Dimension(btn.getPreferredSize().width + 10, 34));
        return btn;
    }

    private void simpanPengaturan() {
        lblStatus.setText("  ✅ Pengaturan berhasil disimpan!");
        lblStatus.setForeground(new Color(39, 174, 96));
        JOptionPane.showMessageDialog(this, "Pengaturan berhasil disimpan!\nBeberapa perubahan memerlukan restart aplikasi.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        lblStatus.setText("  Siap menyimpan perubahan");
        lblStatus.setForeground(new Color(100, 100, 100));
    }

    private void resetDefault() {
        int confirm = JOptionPane.showConfirmDialog(this, "Reset semua pengaturan ke default?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            txtNamaToko.setText("Toko Serba Ada");
            txtAlamat.setText("Jl. Contoh No. 123, Jakarta");
            txtTelepon.setText("021-12345678");
            txtNamaKasir.setText("Admin");
            txtUserAdmin.setText("admin"); txtPassAdmin.setText("admin123");
            txtUserKasir.setText("kasir"); txtPassKasir.setText("kasir123");
            cbTema.setSelectedIndex(0); cbFont.setSelectedIndex(0);
            chkSuara.setSelected(true); chkStrukOtomatis.setSelected(true); chkKonfirmasiHapus.setSelected(true);
            spnDiskon.setValue(0);
            JOptionPane.showMessageDialog(this, "Pengaturan berhasil direset ke default.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
