package gui;

import users.StudentUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class StudentLoginGUI extends JFrame {
    public StudentLoginGUI() {
        setTitle("Student Login");
        setSize(320, 200);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1, 10, 10));
        setResizable(false);

        JTextField idField = new JTextField("e.g. 2025-00001");
        idField.setForeground(Color.GRAY);

        idField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (idField.getText().equals("e.g. 2025-00001")) {
                    idField.setText("");
                    idField.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent e) {
                if (idField.getText().isEmpty()) {
                    idField.setForeground(Color.GRAY);
                    idField.setText("e.g. 2025-00001");
                }
            }
        });

        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(65, 105, 225));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));

        add(new JLabel("Enter Student ID:", SwingConstants.CENTER));
        add(idField);
        add(loginBtn);

        loginBtn.addActionListener(e -> new StudentUser(idField.getText()).login());
        setVisible(true);
    }
}
