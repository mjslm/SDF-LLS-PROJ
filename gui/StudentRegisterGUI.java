package gui;

import models.Student;
import database.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class StudentRegisterGUI extends JFrame {
    public StudentRegisterGUI() {
        setTitle("Register Student");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 10));
        setResizable(false);

        JTextField idField = new JTextField("e.g. 2025-00001");
        JTextField nameField = new JTextField("e.g. Miro M. Valerio");
        JTextField courseField = new JTextField("e.g. BSIT");
        JTextField yearField = new JTextField("e.g. 2");

        Color gray = Color.GRAY;
        idField.setForeground(gray);
        nameField.setForeground(gray);
        courseField.setForeground(gray);
        yearField.setForeground(gray);

        FocusAdapter placeholderListener = new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                JTextField tf = (JTextField) e.getComponent();
                if (tf.getText().startsWith("e.g.")) {
                    tf.setText("");
                    tf.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent e) {
                JTextField tf = (JTextField) e.getComponent();
                if (tf.getText().isEmpty()) {
                    tf.setForeground(gray);
                    if (tf == idField) tf.setText("e.g. 2025-00001");
                    if (tf == nameField) tf.setText("e.g. Miro M. Valerio");
                    if (tf == courseField) tf.setText("e.g. BSIT");
                    if (tf == yearField) tf.setText("e.g. 2");
                }
            }
        };

        idField.addFocusListener(placeholderListener);
        nameField.addFocusListener(placeholderListener);
        courseField.addFocusListener(placeholderListener);
        yearField.addFocusListener(placeholderListener);

        JButton registerBtn = new JButton("Register");
        registerBtn.setBackground(new Color(34, 139, 34));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));

        add(new JLabel("Student ID:"));
        add(idField);
        add(new JLabel("Full Name:"));
        add(nameField);
        add(new JLabel("Course:"));
        add(courseField);
        add(new JLabel("Year Level:"));
        add(yearField);
        add(registerBtn);

        registerBtn.addActionListener(e -> {
            Database db = Database.getInstance();
            db.addStudent(new Student(idField.getText(), nameField.getText(),
                    courseField.getText(), yearField.getText()));
            JOptionPane.showMessageDialog(null, "Student Registered Successfully!");
            dispose();
        });

        setVisible(true);
    }
}
