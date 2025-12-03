package database;

import models.Student;
import java.sql.*;
import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

public class Database {
    private static Database instance;
    private final String adminId = "ADMIN123";

    private Database() {}

    public static Database getInstance() {
        if (instance == null) instance = new Database();
        return instance;
    }

    public String getAdminId() { return adminId; }

    // ================= Add Student =================
    public void addStudent(Student s) {
        System.out.println("Adding student: '" + s.getId() + "' - " + s.getName());
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "INSERT INTO students (student_id, student_name, course, year_level) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, s.getId().trim());
                ps.setString(2, s.getName().trim());
                ps.setString(3, s.getCourse().trim());
                ps.setString(4, s.getYearLevel().trim());
                int rows = ps.executeUpdate();
                System.out.println("Rows affected during student registration: " + rows);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saving student! Check console for details.");
        }
    }

    // ================= Get Student =================
    public Student getStudent(String id) {
        String trimmedId = id.trim();
        System.out.println("Looking for student with ID: '" + trimmedId + "'");
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT * FROM students WHERE TRIM(student_id) = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, trimmedId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new Student(
                                rs.getString("student_id").trim(),
                                rs.getString("student_name").trim(),
                                rs.getString("course").trim(),
                                rs.getString("year_level").trim()
                        );
                    } else {
                        System.out.println("No matching student found in DB!");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching student! Check console for details.");
        }
        return null;
    }

    // ================= Log Login =================
    public void logLogin(String studentId, String studentName, String course, String yearLevel, String loginTime, String semester) {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "INSERT INTO login_logs (student_id, student_name, course, year_level, login_datetime, semester) " +
                         "VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, studentId);
                ps.setString(2, studentName);
                ps.setString(3, course);
                ps.setString(4, yearLevel);
                ps.setString(5, loginTime);
                ps.setString(6, semester);
                int rows = ps.executeUpdate();
                System.out.println("Login logged into DB: " + rows + " row(s) affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saving login log to database! Check console for details.");
        }
    }

    // ================= Get Logs =================
    public List<String> getLogs() {
        List<String> logs = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT * FROM login_logs ORDER BY log_id ASC";
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String log = "ID: " + rs.getInt("log_id") +
                                 " | Student: " + rs.getString("student_name") +
                                 " | Course: " + rs.getString("course") +
                                 " | Year: " + rs.getString("year_level") +
                                 " | Semester: " + rs.getString("semester") +
                                 " | Logged at: " + rs.getString("login_datetime");
                    logs.add(log);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }

    // ================= Export TXT =================
    public void exportAnalyticsTxt() {
        try {
            File dir = new File("exported");
            if (!dir.exists()) dir.mkdir();
            File file = new File(dir, "analytics.txt");
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                for (String log : getLogs()) bw.write(log + "\n");
            }
            JOptionPane.showMessageDialog(null, "Analytics exported to exported/analytics.txt");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error exporting TXT! Check console.");
        }
    }

    // ================= Export CSV =================
    public void exportAnalyticsCsv() {
        try {
            File dir = new File("exported");
            if (!dir.exists()) dir.mkdir();
            File file = new File(dir, "analytics.csv");
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                bw.write("Log ID,Student ID,Student Name,Course,Year Level,Semester,Login DateTime\n");
                try (Connection conn = DBUtil.getConnection()) {
                    String sql = "SELECT * FROM login_logs ORDER BY log_id ASC";
                    try (PreparedStatement ps = conn.prepareStatement(sql);
                         ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            bw.write(rs.getInt("log_id") + "," +
                                     rs.getString("student_id") + "," +
                                     rs.getString("student_name") + "," +
                                     rs.getString("course") + "," +
                                     rs.getString("year_level") + "," +
                                     rs.getString("semester") + "," +
                                     rs.getString("login_datetime") + "\n");
                        }
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Analytics exported to exported/analytics.csv");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error exporting CSV! Check console.");
        }
    }
}
