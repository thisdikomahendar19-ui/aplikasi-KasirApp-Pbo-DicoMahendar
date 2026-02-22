package kasirapp;

public class KasirApp {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new FormLogin().setVisible(true);
        });
    }
}

