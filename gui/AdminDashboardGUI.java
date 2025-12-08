package gui;

import database.Database;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdminDashboardGUI extends JFrame {

    private final JTextArea logsArea;
    private List<String> logs;
    private final JComboBox<String> sortBox;

    public AdminDashboardGUI() {
        setTitle("Admin Dashboard");
        setSize(800, 550);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(true);

        Database db = Database.getInstance();
        logs = new ArrayList<>(db.getLogs());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        sortBox = new JComboBox<>(new String[]{
            "All Logs", 
            "Newest First", 
            "Oldest First", 
            "A - Z", 
            "Z - A",
            "By Department",
            "By Semester",
            "By Department & Semester"
        });
        JButton sortBtn = new JButton("Sort");
        sortBtn.setBackground(new Color(70, 130, 180));
        sortBtn.setForeground(Color.WHITE);
        sortBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));

        JButton exportTxtBtn = new JButton("Export TXT");
        exportTxtBtn.setBackground(new Color(60, 179, 113));
        exportTxtBtn.setForeground(Color.WHITE);
        exportTxtBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));

        JButton exportCsvBtn = new JButton("Export CSV");
        exportCsvBtn.setBackground(new Color(255, 140, 0));
        exportCsvBtn.setForeground(Color.WHITE);
        exportCsvBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));

        topPanel.add(new JLabel("Sort: "));
        topPanel.add(sortBox);
        topPanel.add(sortBtn);
        topPanel.add(exportTxtBtn);
        topPanel.add(exportCsvBtn);

        logsArea = new JTextArea();
        logsArea.setEditable(false);
        logsArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        refreshLogs();

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(logsArea), BorderLayout.CENTER);

        sortBtn.addActionListener(e -> applySort());
        exportTxtBtn.addActionListener(e -> db.exportAnalyticsTxt());
        exportCsvBtn.addActionListener(e -> db.exportAnalyticsCsv());

        setVisible(true);
    }

    private void refreshLogs() {
        logsArea.setText("");
        for (String log : logs) logsArea.append(log + "\n");
    }

    private void applySort() {
        String option = (String) sortBox.getSelectedItem();
        Database db = Database.getInstance();
        logs = new ArrayList<>();

        switch(option) {
            case "All Logs" -> logs.addAll(db.getLogs());
            case "Newest First" -> {
                logs.addAll(db.getLogs());
                Collections.reverse(logs);
            }
            case "Oldest First" -> logs.addAll(db.getLogs());
            case "A - Z" -> {
                logs.addAll(db.getLogs());
                Collections.sort(logs);
            }
            case "Z - A" -> {
                logs.addAll(db.getLogs());
                logs.sort(Collections.reverseOrder());
            }
            case "By Department" -> logs.addAll(db.getLoginCountByDepartment());
            case "By Semester" -> logs.addAll(db.getLoginCountBySemester());
            case "By Department & Semester" -> logs.addAll(db.getLoginCountByDepartmentAndSemester());
        }
        refreshLogs();
    }
        }
        refreshLogs();
    }
}
