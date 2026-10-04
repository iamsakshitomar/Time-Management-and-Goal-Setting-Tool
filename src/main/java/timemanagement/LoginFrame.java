package timemanagement;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private final JTextField emailField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();

    public LoginFrame() {
        setTitle("Time Management and Goal Setting Tool");
        setSize(450, 280);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("TIME MANAGEMENT & GOAL SETTING TOOL");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 18));

        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));

        form.add(new JLabel("Email:"));
        form.add(emailField);
        form.add(new JLabel("Password:"));
        form.add(passwordField);

        JButton login = new JButton("Login");
        JButton register = new JButton("Register");

        form.add(login);
        form.add(register);

        login.addActionListener(e -> login());
        register.addActionListener(e -> new RegisterFrame().setVisible(true));

        setLayout(new BorderLayout(10, 10));
        add(title, BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);
    }

    private void login() {
        try {
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (email.isEmpty() || password.isEmpty()) {
                throw new InvalidTaskException("Enter email and password.");
            }

            Integer userId = new UserDAO().login(email, password);

            if (userId == null) {
                throw new InvalidTaskException("Invalid email or password.");
            }

            new DashboardFrame(userId).setVisible(true);
            dispose();

        } catch (InvalidTaskException e) {
            JOptionPaneUtil.error(this, e.getMessage());
        } catch (Exception e) {
            JOptionPaneUtil.error(this, "Login failed:\n" + e.getMessage());
        }
    }
}
