package gui;

import javax.swing.*;
import database.Database;
import models.Student;

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

        loginBtn.addActionListener(e -> {
            // Trim any leading/trailing spaces from the entered ID
            String enteredId = idField.getText().trim();

            // Call getStudent method from Database to check if student exists
            Student s = Database.getInstance().getStudent(enteredId);
            if (s != null) {
                JOptionPane.showMessageDialog(null, "Welcome " + s.getName() + "!");
            } else {
                JOptionPane.showMessageDialog(null, "Student not found!");
            }
        });

        setVisible(true);
    }
}
