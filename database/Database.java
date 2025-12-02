package database;

import models.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class Database {
    private static Database instance;
    private final List<String> loginLogs = new ArrayList<>();
    private final String adminId = "ADMIN123";

    private Database() {}

    public static Database getInstance() {
        if (instance == null) instance = new Database();
        return instance;
    }

    public String getAdminId() { return adminId; }

    public void addStudent(Student s) {
    System.out.println("Adding student: " + s.getId() + " - " + s.getName());  // Debugging line
    try (Connection conn = DBUtil.getConnection()) {
        String sql = "INSERT INTO students (student_id, student_name, course, year_level) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getId().trim());
            ps.setString(2, s.getName().trim());
            ps.setString(3, s.getCourse().trim());
            ps.setString(4, s.getYearLevel().trim());
            int rowsAffected = ps.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);  // Debugging line
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    public Student getStudent(String id) {
    System.out.println("Looking for student with ID: " + id);  // Debugging line
    try (Connection conn = DBUtil.getConnection()) {
        String sql = "SELECT * FROM students WHERE student_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id.trim());  // Ensure that the ID is correctly trimmed
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Student(
                            rs.getString("student_id"),
                            rs.getString("student_name"),
                            rs.getString("course"),
                            rs.getString("year_level")
                    );
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}


    // Logging methods
    public void logLogin(String log) { loginLogs.add(log); }
    public List<String> getLogs() { return new ArrayList<>(loginLogs); }

    public void exportAnalyticsTxt() {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter("analytics.txt", true))) {
            writer.println("=== Library Login Analytics ===");
            for (String log : loginLogs) writer.println(log);
            writer.println("Total Logins: " + loginLogs.size());
            writer.println("===============================\n");
            JOptionPane.showMessageDialog(null, "Analytics exported to analytics.txt");
        } catch (java.io.IOException e) { e.printStackTrace(); }
    }

    public void exportAnalyticsCsv() {
        try (java.io.PrintWriter writer = new java.io.FileWriter("analytics.csv", true)) {
            writer.println("Name,Course,Year,Login Time,Semester");
            for (String log : loginLogs) {
                String[] parts = log.split("\\|");
                if(parts.length == 5) {
                    String name = parts[0].replace("Student:", "").trim();
                    String course = parts[1].replace("Course:", "").trim();
                    String year = parts[2].replace("Year:", "").trim();
                    String time = parts[3].replace("Logged in at", "").trim();
                    String semester = parts[4].replace("Semester:", "").trim();
                    writer.printf("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"%n", name, course, year, time, semester);
                }
            }
            JOptionPane.showMessageDialog(null, "Analytics exported to analytics.csv");
        } catch (java.io.IOException e) { e.printStackTrace(); }
    }
}
