package gui;

import database.Database;
import models.Student;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StudentLoginGUI extends JFrame {
    public StudentLoginGUI() {

        setTitle("Student Login");
        setSize(320, 200);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1));
        setResizable(false);

        JTextField idField = new JTextField("e.g. 2024-00001");
        idField.setForeground(Color.GRAY);

        idField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (idField.getText().contains("e.g.")) {
                    idField.setText("");
                    idField.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent e) {
                if (idField.getText().isEmpty()) {
                    idField.setForeground(Color.GRAY);
                    idField.setText("e.g. 2024-00001");
                }
            }
        });

        JButton loginBtn = new JButton("Login");

        add(new JLabel("Enter Student ID:", SwingConstants.CENTER));
        add(idField);
        add(loginBtn);

        loginBtn.addActionListener(e -> {
            String id = idField.getText().trim();
            if (id.isEmpty() || id.contains("e.g.")) {
                JOptionPane.showMessageDialog(null, "Please enter Student ID.");
                return;
            }

            Student s = Database.getInstance().getStudent(id);

            if (s == null) {
                JOptionPane.showMessageDialog(null, "Student not found!");
                return;
            }

            String time = LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            );

            Database.getInstance().logLogin(
                    s.getId(), s.getName(), s.getCourse(), s.getYearLevel(), time
            );

            JOptionPane.showMessageDialog(null, "Welcome " + s.getName());
        });

        setVisible(true);
    }
}
