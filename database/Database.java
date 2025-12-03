package database;

import models.Student;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        try (Connection conn = DBUtil.getConnection()) {

            String sql = "INSERT INTO students (student_id, student_name, course, year_level) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, s.getId().trim());
            ps.setString(2, s.getName().trim());
            ps.setString(3, s.getCourse().trim());
            ps.setString(4, s.getYearLevel().trim());

            int rows = ps.executeUpdate();
            System.out.println("Student Inserted: " + rows);

        } catch (SQLException e) {
            System.out.println("❌ ERROR INSERTING STUDENT!");
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saving student.");
        }
    }

    // ================= Get Student =================
    public Student getStudent(String id) {
        try (Connection conn = DBUtil.getConnection()) {

            String sql = "SELECT * FROM students WHERE student_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, id.trim());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Student(
                        rs.getString("student_id"),
                        rs.getString("student_name"),
                        rs.getString("course"),
                        rs.getString("year_level")
                );
            }

        } catch (SQLException e) {
            System.out.println("❌ ERROR FETCHING STUDENT!");
            e.printStackTrace();
        }
        return null;
    }

    // ================= Log Login =================
    public void logLogin(String studentId, String studentName, String course, String yearLevel, String loginTime) {
        try (Connection conn = DBUtil.getConnection()) {

            String sql = "INSERT INTO login_logs (student_id, student_name, course, year_level, login_datetime, semester) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, studentId);
            ps.setString(2, studentName);
            ps.setString(3, course);
            ps.setString(4, yearLevel);
            ps.setString(5, loginTime);
            ps.setString(6, "1st");  // FIXED

            int rows = ps.executeUpdate();
            System.out.println("Login Logged: " + rows);

        } catch (SQLException e) {
            System.out.println("❌ ERROR INSERTING LOGIN LOG!");
            e.printStackTrace();
        }
    }

    // ================= Read Logs (Admin Dashboard) =================
    public List<String> getLogs() {
        List<String> logs = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection()) {

            String sql = "SELECT * FROM login_logs ORDER BY log_id DESC";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String log = "ID: " + rs.getInt("log_id")
                        + " | Student: " + rs.getString("student_name")
                        + " | Course: " + rs.getString("course")
                        + " | Year: " + rs.getString("year_level")
                        + " | Time: " + rs.getString("login_datetime")
                        + " | Semester: " + rs.getString("semester");

                logs.add(log);
            }

        } catch (SQLException e) {
            System.out.println("❌ ERROR READING LOGS!");
            e.printStackTrace();
        }

        return logs;
    }

    // (Optional export methods can stay empty for now)
    public void exportAnalyticsTxt() {}
    public void exportAnalyticsCsv() {}
}
