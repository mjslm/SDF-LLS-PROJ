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

    // ================= Get Login Count by Department =================
    public List<String> getLoginCountByDepartment() {
        List<String> stats = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT course, COUNT(*) as login_count FROM login_logs GROUP BY course ORDER BY login_count DESC";
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                stats.add("========== LOGIN COUNT BY DEPARTMENT ==========");
                while (rs.next()) {
                    String department = rs.getString("course");
                    int count = rs.getInt("login_count");
                    stats.add("Department: " + department + " | Total Logins: " + count);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stats;
    }

    // ================= Get Login Count by Semester =================
    public List<String> getLoginCountBySemester() {
        List<String> stats = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT semester, COUNT(*) as login_count FROM login_logs GROUP BY semester ORDER BY login_count DESC";
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                stats.add("========== LOGIN COUNT BY SEMESTER ==========");
                while (rs.next()) {
                    String semester = rs.getString("semester");
                    int count = rs.getInt("login_count");
                    stats.add("Semester: " + semester + " | Total Logins: " + count);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stats;
    }

    // ================= Get Login Count by Department and Semester =================
    public List<String> getLoginCountByDepartmentAndSemester() {
        List<String> stats = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT course, semester, COUNT(*) as login_count FROM login_logs GROUP BY course, semester ORDER BY course ASC, login_count DESC";
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                stats.add("========== LOGIN COUNT BY DEPARTMENT & SEMESTER ==========");
                while (rs.next()) {
                    String department = rs.getString("course");
                    String semester = rs.getString("semester");
                    int count = rs.getInt("login_count");
                    stats.add("Dept: " + department + " | Semester: " + semester + " | Logins: " + count);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stats;
    }

    // ================= Update Analytics in Database =================
    public void updateAnalyticsByDepartment() {
        try (Connection conn = DBUtil.getConnection()) {
            // Get all departments and their login counts
            String selectSql = "SELECT course, COUNT(*) as login_count FROM login_logs GROUP BY course";
            try (PreparedStatement selectPs = conn.prepareStatement(selectSql);
                 ResultSet rs = selectPs.executeQuery()) {
                while (rs.next()) {
                    String department = rs.getString("course");
                    int count = rs.getInt("login_count");
                    
                    // Insert or update analytics
                    String upsertSql = "INSERT INTO analytics_by_department (department, login_count) " +
                                     "VALUES (?, ?) " +
                                     "ON DUPLICATE KEY UPDATE login_count = ?, last_updated = CURRENT_TIMESTAMP";
                    try (PreparedStatement upsertPs = conn.prepareStatement(upsertSql)) {
                        upsertPs.setString(1, department);
                        upsertPs.setInt(2, count);
                        upsertPs.setInt(3, count);
                        upsertPs.executeUpdate();
                    }
                }
            }
            System.out.println("✓ Analytics by Department updated in database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ================= Update Analytics by Semester in Database =================
    public void updateAnalyticsBySemester() {
        try (Connection conn = DBUtil.getConnection()) {
            String selectSql = "SELECT semester, COUNT(*) as login_count FROM login_logs GROUP BY semester";
            try (PreparedStatement selectPs = conn.prepareStatement(selectSql);
                 ResultSet rs = selectPs.executeQuery()) {
                while (rs.next()) {
                    String semester = rs.getString("semester");
                    int count = rs.getInt("login_count");
                    
                    String upsertSql = "INSERT INTO analytics_by_semester (semester, login_count) " +
                                     "VALUES (?, ?) " +
                                     "ON DUPLICATE KEY UPDATE login_count = ?, last_updated = CURRENT_TIMESTAMP";
                    try (PreparedStatement upsertPs = conn.prepareStatement(upsertSql)) {
                        upsertPs.setString(1, semester);
                        upsertPs.setInt(2, count);
                        upsertPs.setInt(3, count);
                        upsertPs.executeUpdate();
                    }
                }
            }
            System.out.println("✓ Analytics by Semester updated in database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ================= Update Analytics by Department & Semester in Database =================
    public void updateAnalyticsByDeptAndSemester() {
        try (Connection conn = DBUtil.getConnection()) {
            String selectSql = "SELECT course, semester, COUNT(*) as login_count FROM login_logs GROUP BY course, semester";
            try (PreparedStatement selectPs = conn.prepareStatement(selectSql);
                 ResultSet rs = selectPs.executeQuery()) {
                while (rs.next()) {
                    String department = rs.getString("course");
                    String semester = rs.getString("semester");
                    int count = rs.getInt("login_count");
                    
                    String upsertSql = "INSERT INTO analytics_by_dept_semester (department, semester, login_count) " +
                                     "VALUES (?, ?, ?) " +
                                     "ON DUPLICATE KEY UPDATE login_count = ?, last_updated = CURRENT_TIMESTAMP";
                    try (PreparedStatement upsertPs = conn.prepareStatement(upsertSql)) {
                        upsertPs.setString(1, department);
                        upsertPs.setString(2, semester);
                        upsertPs.setInt(3, count);
                        upsertPs.setInt(4, count);
                        upsertPs.executeUpdate();
                    }
                }
            }
            System.out.println("✓ Analytics by Department & Semester updated in database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ================= Update All Analytics =================
    public void updateAllAnalytics() {
        updateAnalyticsByDepartment();
        updateAnalyticsBySemester();
        updateAnalyticsByDeptAndSemester();
        System.out.println("✓ All analytics have been saved to database!");
    }

    // ================= Export TXT =================
    public void exportAnalyticsTxt() {
        updateAllAnalytics();  // Save to database before exporting
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
        updateAllAnalytics();  // Save to database before exporting
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
