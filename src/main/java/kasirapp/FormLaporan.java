package kasirapp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class FormLaporan extends JFrame {

    private JTable tabelLaporan;
    private DefaultTableModel modelTabel;
    private JLabel lblTotalPendapatan, lblTotalTransaksi, lblRataRata, lblPeriode;
    private JComboBox<String> cbFilter;
    private JButton btnCetak, btnRefresh, btnExport;
    private NumberFormat rupiahFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    // Data dummy transaksi
    private List<String[]> dataTransaksi = new ArrayList<>();

    public FormLaporan() {
        initDummyData();
        initComponents();
        setTitle("📊 Laporan Penjualan");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 620);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initDummyData() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Calendar cal = Calendar.getInstance();

        // Data hari ini
        dataTransaksi.add(new String[]{"#001", sdf.format(cal.getTime()), "Admin", "Beras 5kg x2, Minyak Goreng x1", "148000", "200000", "52000"});
        cal.add(Calendar.HOUR, -1);
        dataTransaksi.add(new String[]{"#002", sdf.format(cal.getTime()), "Kasir", "Indomie Goreng x5, Aqua x3", "29500", "50000", "20500"});
        cal.add(Calendar.HOUR, -2);
        dataTransaksi.add(new String[]{"#003", sdf.format(cal.getTime()), "Admin", "Gula Pasir 1kg x3, Tepung x2", "66000", "100000", "34000"});

        // Kemarin
        cal.add(Calendar.DAY_OF_MONTH, -1);
        dataTransaksi.add(new String[]{"#004", sdf.format(cal.getTime()), "Kasir", "Susu Ultra x2, Roti Tawar x1", "53000", "100000", "47000"});
        cal.add(Calendar.HOUR, -3);
        dataTransaksi.add(new String[]{"#005", sdf.format(cal.getTime()), "Admin", "Telur Ayam x2, Sabun x3", "71000", "100000", "29000"});

        // Minggu lalu
        cal.add(Calendar.DAY_OF_MONTH, -5);
        dataTransaksi.add(new String[]{"#006", sdf.format(cal.getTime()), "Kasir", "Shampo Pantene x2, Kopi x1", "57500", "100000", "42500"});
        cal.add(Calendar.HOUR, -2);
        dataTransaksi.add(new String[]{"#007", sdf.format(cal.getTime()), "Admin", "Beras 5kg x1, Telur x1", "93000", "100000", "7000"});
        cal.add(Calendar.HOUR, -5);
        dataTransaksi.add(new String[]{"#008", sdf.format(cal.getTime()), "Kasir", "Aqua x10, Indomie x8", "68000", "100000", "32000"});
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(245, 247, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // === HEADER ===
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(142, 68, 173));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        JLabel lblJudul = new JLabel("📊 LAPORAN PENJUALAN");
        lblJudul.setFont(new Font("Arial", Font.BOLD, 18));
        lblJudul.setForeground(Color.WHITE);
        lblPeriode = new JLabel(new SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("id","ID")).format(new Date()));
        lblPeriode.setFont(new Font("Arial", Font.PLAIN, 12));
        lblPeriode.setForeground(new Color(220, 200, 255));
        headerPanel.add(lblJudul, BorderLayout.WEST);
        headerPanel.add(lblPeriode, BorderLayout.EAST);

        // === SUMMARY CARDS ===
        JPanel cardsPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        cardsPanel.setBackground(new Color(245, 247, 250));

        lblTotalPendapatan = new JLabel("Rp 0");
        lblTotalTransaksi = new JLabel("0");
        lblRataRata = new JLabel("Rp 0");

        cardsPanel.add(buatCard("💰 Total Pendapatan", lblTotalPendapatan, new Color(39, 174, 96)));
        cardsPanel.add(buatCard("🧾 Total Transaksi", lblTotalTransaksi, new Color(41, 128, 185)));
        cardsPanel.add(buatCard("📈 Rata-rata/Transaksi", lblRataRata, new Color(142, 68, 173)));

        // === FILTER & TOMBOL ===
        JPanel filterPanel = new JPanel(new BorderLayout());
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        JPanel filterKiri = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        filterKiri.setBackground(Color.WHITE);
        JLabel lblFilter = new JLabel("Filter Periode:");
        lblFilter.setFont(new Font("Arial", Font.BOLD, 12));
        cbFilter = new JComboBox<>(new String[]{"Semua Data", "Hari Ini", "Kemarin", "7 Hari Terakhir", "Bulan Ini"});
        cbFilter.setFont(new Font("Arial", Font.PLAIN, 13));
        cbFilter.setPreferredSize(new Dimension(180, 30));
        cbFilter.addActionListener(e -> filterData());
        filterKiri.add(lblFilter);
        filterKiri.add(cbFilter);

        JPanel filterKanan = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        filterKanan.setBackground(Color.WHITE);
        btnRefresh = buatTombol("🔄 Refresh", new Color(52, 152, 219));
        btnRefresh.addActionListener(e -> filterData());
        btnCetak = buatTombol("🖨 Cetak Laporan", new Color(142, 68, 173));
        btnCetak.addActionListener(e -> cetakLaporan());
        btnExport = buatTombol("📁 Export", new Color(39, 174, 96));
        btnExport.addActionListener(e -> JOptionPane.showMessageDialog(this, "Fitur export sedang dikembangkan.", "Info", JOptionPane.INFORMATION_MESSAGE));
        filterKanan.add(btnRefresh);
        filterKanan.add(btnExport);
        filterKanan.add(btnCetak);

        filterPanel.add(filterKiri, BorderLayout.WEST);
        filterPanel.add(filterKanan, BorderLayout.EAST);

        // === TABEL ===
        String[] kolom = {"No. Struk", "Tanggal & Jam", "Kasir", "Item Terjual", "Total", "Dibayar", "Kembalian"};
        modelTabel = new DefaultTableModel(kolom, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        tabelLaporan = new JTable(modelTabel);
        tabelLaporan.setFont(new Font("Arial", Font.PLAIN, 12));
        tabelLaporan.setRowHeight(26);
        tabelLaporan.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tabelLaporan.getTableHeader().setBackground(new Color(142, 68, 173));
        tabelLaporan.getTableHeader().setForeground(Color.WHITE);
        tabelLaporan.setSelectionBackground(new Color(225, 200, 245));
        tabelLaporan.setGridColor(new Color(230, 230, 230));

        int[] widths = {70, 140, 80, 250, 110, 100, 100};
        for (int i = 0; i < widths.length; i++)
            tabelLaporan.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);

        JScrollPane scroll = new JScrollPane(tabelLaporan);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        JPanel tabelWrapper = new JPanel(new BorderLayout());
        tabelWrapper.setBackground(Color.WHITE);
        tabelWrapper.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        tabelWrapper.add(scroll);

        JPanel centerPanel = new JPanel(new BorderLayout(0, 10));
        centerPanel.setBackground(new Color(245, 247, 250));
        centerPanel.add(filterPanel, BorderLayout.NORTH);
        centerPanel.add(tabelWrapper, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(cardsPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(0, 10));
        bottomPanel.setBackground(new Color(245, 247, 250));
        bottomPanel.add(cardsPanel, BorderLayout.NORTH);
        bottomPanel.add(centerPanel, BorderLayout.CENTER);

        mainPanel.add(bottomPanel, BorderLayout.CENTER);
        add(mainPanel);

        filterData();
    }

    private JPanel buatCard(String judul, JLabel lblNilai, Color warna) {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 4, 0, 0, warna),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0; g.gridy = 0; g.anchor = GridBagConstraints.WEST;

        JLabel lbl = new JLabel(judul);
        lbl.setFont(new Font("Arial", Font.BOLD, 12));
        lbl.setForeground(new Color(100, 100, 100));
        card.add(lbl, g);

        lblNilai.setFont(new Font("Arial", Font.BOLD, 20));
        lblNilai.setForeground(warna);
        g.gridy = 1; g.insets = new Insets(5, 0, 0, 0);
        card.add(lblNilai, g);

        return card;
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

    private void filterData() {
        String filter = (String) cbFilter.getSelectedItem();
        modelTabel.setRowCount(0);

        double totalPendapatan = 0;
        int jumlahTransaksi = 0;

        for (int i = 0; i < dataTransaksi.size(); i++) {
            // Semua ditampilkan (filter sederhana - bisa dikembangkan)
            String[] row = dataTransaksi.get(i);
            modelTabel.addRow(new Object[]{
                row[0], row[1], row[2], row[3],
                rupiahFormat.format(Double.parseDouble(row[4])),
                rupiahFormat.format(Double.parseDouble(row[5])),
                rupiahFormat.format(Double.parseDouble(row[6]))
            });
            totalPendapatan += Double.parseDouble(row[4]);
            jumlahTransaksi++;
        }

        lblTotalPendapatan.setText(rupiahFormat.format(totalPendapatan));
        lblTotalTransaksi.setText(jumlahTransaksi + " Transaksi");
        lblRataRata.setText(jumlahTransaksi > 0 ? rupiahFormat.format(totalPendapatan / jumlahTransaksi) : "Rp 0");
    }

    private void cetakLaporan() {
        StringBuilder sb = new StringBuilder();
        sb.append("╔══════════════════════════════════════════════╗\n");
        sb.append("║         LAPORAN PENJUALAN - TOKO SERBA ADA  ║\n");
        sb.append("║  Dicetak: ").append(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date())).append("                    ║\n");
        sb.append("╠══════════════════════════════════════════════╣\n");

        double total = 0;
        for (String[] t : dataTransaksi) {
            sb.append(String.format("  %-6s | %-18s | Rp %,10.0f\n", t[0], t[1], Double.parseDouble(t[4])));
            total += Double.parseDouble(t[4]);
        }

        sb.append("══════════════════════════════════════════════\n");
        sb.append(String.format("  TOTAL PENDAPATAN      : %s\n", rupiahFormat.format(total)));
        sb.append(String.format("  JUMLAH TRANSAKSI      : %d transaksi\n", dataTransaksi.size()));
        sb.append(String.format("  RATA-RATA / TRANSAKSI : %s\n", rupiahFormat.format(total / dataTransaksi.size())));
        sb.append("╚══════════════════════════════════════════════╝");

        JTextArea area = new JTextArea(sb.toString());
        area.setFont(new Font("Monospaced", Font.PLAIN, 11));
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(520, 350));
        JOptionPane.showMessageDialog(this, scroll, "🖨 Laporan Penjualan", JOptionPane.INFORMATION_MESSAGE);
    }
}
