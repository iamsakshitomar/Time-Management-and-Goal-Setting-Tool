package timemanagement;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        Database.initialize();

        SwingUtilities.invokeLater(() -> {
            LoginFrame frame = new LoginFrame();
            frame.setVisible(true);
        });
    }
}
