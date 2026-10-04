package timemanagement;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {
    private final JTextField nameField = new JTextField();
    private final JTextField emailField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();

    public RegisterFrame() {
        setTitle("Register - Time Management Tool");
        setSize(420, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Name:"));
        panel.add(nameField);

        panel.add(new JLabel("Email:"));
        panel.add(emailField);

        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        JButton register = new JButton("Register");
        JButton cancel = new JButton("Cancel");

        panel.add(register);
        panel.add(cancel);

        register.addActionListener(e -> register());
        cancel.addActionListener(e -> dispose());

        add(panel);
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
            JOptionPaneUtil.info(this, "Registration successful. Please login.");
            dispose();

        } catch (InvalidTaskException e) {
            JOptionPaneUtil.error(this, e.getMessage());
        } catch (Exception e) {
            JOptionPaneUtil.error(this, "Registration failed:\n" + e.getMessage());
        }
    }
}
