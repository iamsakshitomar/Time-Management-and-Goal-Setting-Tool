package timemanagement;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/** Shared visual styling for a clean, modern desktop interface. */
public final class AppTheme {
    public static final Color NAVY = new Color(24, 35, 63);
    public static final Color BLUE = new Color(65, 105, 225);
    public static final Color TEAL = new Color(35, 166, 153);
    public static final Color BACKGROUND = new Color(245, 247, 251);
    public static final Color CARD = Color.WHITE;
    public static final Color TEXT = new Color(37, 46, 66);
    public static final Color MUTED = new Color(112, 122, 143);
    public static final Color BORDER = new Color(225, 230, 239);

    private AppTheme() { }

    public static void install() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) { }
        UIManager.put("TabbedPane.selected", CARD);
        UIManager.put("TabbedPane.contentAreaColor", BACKGROUND);
        UIManager.put("TabbedPane.focus", new Color(0, 0, 0, 0));
        UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 13));
        UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.BOLD, 12));
    }

    public static void styleButton(JButton button, boolean primary) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(primary ? Color.WHITE : NAVY);
        button.setBackground(primary ? BLUE : CARD);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(primary ? BLUE : BORDER),
                new EmptyBorder(9, 15, 9, 15)));
        button.setOpaque(true);
    }

    public static void styleInput(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setForeground(TEXT);
        field.setBackground(CARD);
        field.setCaretColor(BLUE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER), new EmptyBorder(9, 10, 9, 10)));
        field.setPreferredSize(new Dimension(220, 38));
    }

    public static void styleList(JList<?> list) {
        list.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        list.setForeground(TEXT);
        list.setBackground(CARD);
        list.setSelectionBackground(new Color(224, 233, 255));
        list.setSelectionForeground(NAVY);
        list.setFixedCellHeight(42);
        list.setBorder(new EmptyBorder(8, 8, 8, 8));
    }

    public static JPanel cardLayoutPanel(LayoutManager layout) {
        JPanel panel = new JPanel(layout);
        panel.setBackground(CARD);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER), new EmptyBorder(16, 16, 16, 16)));
        return panel;
    }

    public static JLabel heading(String text, int size) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, size));
        label.setForeground(TEXT);
        return label;
    }

    public static JLabel muted(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        label.setForeground(MUTED);
        return label;
    }
}
