package kasirapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FormLogin extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnKeluar;
    private JCheckBox chkShowPass;
    private int attempts = 0;

    // Data user (bisa diganti database)
    private String[][] users = {
        {"admin", "admin123", "Administrator"},
        {"kasir", "kasir123", "Kasir"},
        {"manager", "manager123", "Manager"}
    };

    public FormLogin() {
        initComponents();
        setTitle("Login - Aplikasi Kasir");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 520);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(41, 128, 185));

        // === TOP PANEL (Logo) ===
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBackground(new Color(41, 128, 185));
        topPanel.setPreferredSize(new Dimension(0, 180));

        JLabel lblIcon = new JLabel("🏪");
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 60));

        JLabel lblApp = new JLabel("TOKO SERBA ADA");
        lblApp.setFont(new Font("Arial", Font.BOLD, 20));
        lblApp.setForeground(Color.WHITE);

        JLabel lblSub = new JLabel("Sistem Kasir Modern");
        lblSub.setFont(new Font("Arial", Font.PLAIN, 13));
        lblSub.setForeground(new Color(200, 230, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; gbc.insets = new Insets(5,5,2,5);
        topPanel.add(lblIcon, gbc);
        gbc.gridy = 1;
        topPanel.add(lblApp, gbc);
        gbc.gridy = 2;
        topPanel.add(lblSub, gbc);

        // === FORM PANEL ===
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(6, 0, 6, 0);
        g.gridx = 0; g.gridwidth = 2;

        JLabel lblLogin = new JLabel("Silakan Login");
        lblLogin.setFont(new Font("Arial", Font.BOLD, 16));
        lblLogin.setForeground(new Color(41, 128, 185));
        g.gridy = 0;
        formPanel.add(lblLogin, g);

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(220,220,220));
        g.gridy = 1; g.insets = new Insets(2, 0, 15, 0);
        formPanel.add(sep, g);

        // Username
        g.insets = new Insets(5, 0, 3, 0);
        JLabel lblUser = new JLabel("👤  Username");
        lblUser.setFont(new Font("Arial", Font.BOLD, 12));
        g.gridy = 2;
        formPanel.add(lblUser, g);

        txtUsername = new JTextField();
        txtUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        txtUsername.setPreferredSize(new Dimension(0, 38));
        txtUsername.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        g.gridy = 3; g.insets = new Insets(0, 0, 10, 0);
        formPanel.add(txtUsername, g);

        // Password
        g.insets = new Insets(5, 0, 3, 0);
        JLabel lblPass = new JLabel("🔒  Password");
        lblPass.setFont(new Font("Arial", Font.BOLD, 12));
        g.gridy = 4;
        formPanel.add(lblPass, g);

        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        txtPassword.setPreferredSize(new Dimension(0, 38));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        txtPassword.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) prosesLogin();
            }
        });
        g.gridy = 5; g.insets = new Insets(0, 0, 5, 0);
        formPanel.add(txtPassword, g);

        // Show password
        chkShowPass = new JCheckBox("Tampilkan Password");
        chkShowPass.setFont(new Font("Arial", Font.PLAIN, 11));
        chkShowPass.setBackground(Color.WHITE);
        chkShowPass.addActionListener(e -> {
            txtPassword.setEchoChar(chkShowPass.isSelected() ? (char) 0 : '●');
        });
        g.gridy = 6; g.insets = new Insets(0, 0, 15, 0);
        formPanel.add(chkShowPass, g);

        // Tombol Login
        btnLogin = new JButton("  MASUK");
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setBackground(new Color(41, 128, 185));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setPreferredSize(new Dimension(0, 42));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.addActionListener(e -> prosesLogin());
        g.gridy = 7; g.insets = new Insets(5, 0, 8, 0);
        formPanel.add(btnLogin, g);

        btnKeluar = new JButton("Keluar");
        btnKeluar.setFont(new Font("Arial", Font.PLAIN, 12));
        btnKeluar.setBackground(new Color(236, 240, 241));
        btnKeluar.setForeground(new Color(100, 100, 100));
        btnKeluar.setFocusPainted(false);
        btnKeluar.setBorderPainted(false);
        btnKeluar.setPreferredSize(new Dimension(0, 35));
        btnKeluar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnKeluar.addActionListener(e -> System.exit(0));
        g.gridy = 8; g.insets = new Insets(0, 0, 0, 0);
        formPanel.add(btnKeluar, g);

        // Info akun
        JLabel lblInfo = new JLabel("<html><center><font color='gray' size='2'>Default: admin / admin123</font></center></html>");
        g.gridy = 9; g.insets = new Insets(15, 0, 0, 0);
        formPanel.add(lblInfo, g);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    private void prosesLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username dan password tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        for (String[] user : users) {
            if (user[0].equals(username) && user[1].equals(password)) {
                JOptionPane.showMessageDialog(this, "Selamat datang, " + user[2] + "! ✅", "Login Berhasil", JOptionPane.INFORMATION_MESSAGE);
                new FormKasir().setVisible(true);
                this.dispose();
                return;
            }
        }

        attempts++;
        if (attempts >= 3) {
            JOptionPane.showMessageDialog(this, "Terlalu banyak percobaan gagal!\nAplikasi akan ditutup.", "Akses Ditolak", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        JOptionPane.showMessageDialog(this, "Username atau password salah!\nPercobaan ke-" + attempts + " dari 3.", "Login Gagal", JOptionPane.ERROR_MESSAGE);
        txtPassword.setText("");
        txtPassword.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormLogin().setVisible(true));
    }
}

