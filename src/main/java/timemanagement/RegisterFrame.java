package timemanagement;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RegisterFrame extends JFrame {
    private final JTextField nameField = new JTextField();
    private final JTextField emailField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();

    public RegisterFrame() {
        AppTheme.install();
        setTitle("Create account | FocusFlow");
        setSize(470, 480);
        setMinimumSize(new Dimension(440, 440));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(AppTheme.BACKGROUND);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(28, 34, 28, 34));
        card.add(AppTheme.heading("Create your account", 23));
        card.add(Box.createVerticalStrut(7));
        card.add(AppTheme.muted("A calmer way to plan your day."));
        card.add(Box.createVerticalStrut(25));
        addField(card, "FULL NAME", nameField);
        card.add(Box.createVerticalStrut(15));
        addField(card, "EMAIL ADDRESS", emailField);
        card.add(Box.createVerticalStrut(15));
        addField(card, "PASSWORD", passwordField);
        card.add(Box.createVerticalStrut(24));
        JButton register = new JButton("Create account");
        AppTheme.styleButton(register, true);
        register.setAlignmentX(Component.LEFT_ALIGNMENT);
        register.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        register.addActionListener(e -> register());
        card.add(register);
        card.add(Box.createVerticalStrut(10));
        JButton cancel = new JButton("Cancel");
        AppTheme.styleButton(cancel, false);
        cancel.setAlignmentX(Component.LEFT_ALIGNMENT);
        cancel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        cancel.addActionListener(e -> dispose());
        card.add(cancel);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(AppTheme.BACKGROUND);
        wrapper.setBorder(new EmptyBorder(22, 22, 22, 22));
        wrapper.add(card, BorderLayout.CENTER);
        add(wrapper);
        getRootPane().setDefaultButton(register);
    }

    private void addField(JPanel parent, String labelText, JTextField field) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 10));
        label.setForeground(AppTheme.MUTED);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        AppTheme.styleInput(field);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        parent.add(label);
        parent.add(Box.createVerticalStrut(6));
        parent.add(field);
    }

    private void register() {
        try {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());
            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                throw new InvalidTaskException("All fields are required.");
            }
            new UserDAO().register(name, email, password);
            JOptionPaneUtil.info(this, "Registration successful. Please sign in.");
            dispose();
        } catch (InvalidTaskException e) {
            JOptionPaneUtil.error(this, e.getMessage());
        } catch (Exception e) {
            JOptionPaneUtil.error(this, "Registration failed:\n" + e.getMessage());
        }
    }
}
