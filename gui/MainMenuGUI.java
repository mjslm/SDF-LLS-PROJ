package gui;

import javax.swing.*;
import java.awt.*;
import users.StudentUser;
import users.AdminUser;

public class MainMenuGUI extends JFrame {

    public MainMenuGUI() {
        setTitle("📚 Library Login System");
        setSize(380, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Load and stretch logo (wider, readable)
        ImageIcon originalIcon = new ImageIcon("spc-logo.png");
        int newWidth = 180;  // wider
        int newHeight = 120;
        Image stretchedImage = originalIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        JLabel logo = new JLabel(new ImageIcon(stretchedImage));
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("Library Login System", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(new Color(25, 25, 112));

        JButton studentLogin = createStyledButton("🎓 Student Login", new Color(70, 130, 180));
        JButton studentRegister = createStyledButton("📝 Register Student", new Color(60, 179, 113));
        JButton adminLogin = createStyledButton("🔑 Admin Login", new Color(255, 140, 0));

        studentLogin.addActionListener(e -> new StudentLoginGUI());
        studentRegister.addActionListener(e -> new StudentRegisterGUI());
        adminLogin.addActionListener(e -> new AdminLoginGUI());

        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(logo);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(studentLogin);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(studentRegister);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(adminLogin);

        add(panel);
        setVisible(true);
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMaximumSize(new Dimension(240, 45));
        return button;
    }
}
