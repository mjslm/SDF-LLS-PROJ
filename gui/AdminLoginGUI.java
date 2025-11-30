package gui;

import users.AdminUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class AdminLoginGUI extends JFrame {
    public AdminLoginGUI() {
        setTitle("Admin Login");
        setSize(320, 200);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1, 10, 10));
        setResizable(false);

        JTextField idField = new JTextField("e.g. ADMIN123");
        idField.setForeground(Color.GRAY);

        idField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (idField.getText().equals("e.g. ADMIN123")) {
                    idField.setText("");
                    idField.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent e) {
                if (idField.getText().isEmpty()) {
                    idField.setForeground(Color.GRAY);
                    idField.setText("e.g. ADMIN123");
                }
            }
        });

        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(255, 140, 0));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));

        add(new JLabel("Enter Admin ID:", SwingConstants.CENTER));
        add(idField);
        add(loginBtn);

        loginBtn.addActionListener(e -> new AdminUser(idField.getText()).login());
        setVisible(true);
    }
}
