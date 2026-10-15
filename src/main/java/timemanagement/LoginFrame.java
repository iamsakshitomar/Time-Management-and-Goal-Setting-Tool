package timemanagement;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginFrame extends JFrame {
    private final JTextField emailField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();

    public LoginFrame() {
        AppTheme.install();
        setTitle("FocusFlow | Time Management & Goals");
        setSize(760, 500);
        setMinimumSize(new Dimension(700, 460));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(AppTheme.BACKGROUND);

        JPanel shell = new JPanel(new GridLayout(1, 2, 0, 0));
        shell.setBackground(AppTheme.BACKGROUND);
        shell.setBorder(new EmptyBorder(22, 22, 22, 22));

        JPanel brand = new JPanel();
        brand.setLayout(new BoxLayout(brand, BoxLayout.Y_AXIS));
        brand.setBackground(AppTheme.NAVY);
        brand.setBorder(new EmptyBorder(34, 28, 30, 28));
        JLabel mark = new JLabel("◈  FOCUSFLOW");
        mark.setForeground(new Color(125, 210, 202));
        mark.setFont(new Font("Segoe UI", Font.BOLD, 17));
        JLabel hero = new JLabel("Make time\nfor what matters.");
        hero.setForeground(Color.WHITE);
        hero.setFont(new Font("Segoe UI", Font.BOLD, 27));
        JLabel description = new JLabel("<html>Plan your goals, organise daily tasks,<br>and see your progress—one step at a time.</html>");
        description.setForeground(new Color(213, 220, 236));
        description.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JLabel footer = new JLabel("PLAN  •  FOCUS  •  GROW");
        footer.setForeground(new Color(125, 210, 202));
        footer.setFont(new Font("Segoe UI", Font.BOLD, 10));
        brand.add(mark);
        brand.add(Box.createVerticalStrut(54));
        brand.add(hero);
        brand.add(Box.createVerticalStrut(16));
        brand.add(description);
        brand.add(Box.createVerticalGlue());
        brand.add(footer);

        JPanel formCard = new JPanel();
        formCard.setLayout(new BoxLayout(formCard, BoxLayout.Y_AXIS));
        formCard.setBackground(Color.WHITE);
        formCard.setBorder(new EmptyBorder(34, 30, 28, 30));
        JLabel welcome = AppTheme.heading("Welcome back", 24);
        JLabel hint = AppTheme.muted("Sign in to continue to your workspace.");
        formCard.add(welcome);
        formCard.add(Box.createVerticalStrut(7));
        formCard.add(hint);
        formCard.add(Box.createVerticalStrut(30));
        addField(formCard, "EMAIL ADDRESS", emailField);
        formCard.add(Box.createVerticalStrut(17));
        addField(formCard, "PASSWORD", passwordField);
        formCard.add(Box.createVerticalStrut(24));
        JButton login = new JButton("Sign in  →");
        AppTheme.styleButton(login, true);
        login.setAlignmentX(Component.LEFT_ALIGNMENT);
        login.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        login.addActionListener(e -> login());
        formCard.add(login);
        formCard.add(Box.createVerticalStrut(18));
        JPanel registerRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        registerRow.setBackground(Color.WHITE);
        registerRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        registerRow.add(AppTheme.muted("New here?  "));
        JButton register = new JButton("Create an account");
        register.setFont(new Font("Segoe UI", Font.BOLD, 12));
        register.setForeground(AppTheme.BLUE);
        register.setBorderPainted(false);
        register.setContentAreaFilled(false);
        register.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        register.addActionListener(e -> new RegisterFrame().setVisible(true));
        registerRow.add(register);
        formCard.add(registerRow);

        shell.add(brand);
        shell.add(formCard);
        add(shell);
        getRootPane().setDefaultButton(login);
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
        parent.add(Box.createVerticalStrut(7));
        parent.add(field);
    }

    private void login() {
        try {
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());
            if (email.isEmpty() || password.isEmpty()) {
                throw new InvalidTaskException("Enter email and password.");
            }
            Integer userId = new UserDAO().login(email, password);
            if (userId == null) throw new InvalidTaskException("Invalid email or password.");
            new DashboardFrame(userId).setVisible(true);
            dispose();
        } catch (InvalidTaskException e) {
            JOptionPaneUtil.error(this, e.getMessage());
        } catch (Exception e) {
            JOptionPaneUtil.error(this, "Login failed:\n" + e.getMessage());
        }
    }
}
