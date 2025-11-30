package database;

import models.Student;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Database {
    private static Database instance;
    private final java.util.List<String> loginLogs = new ArrayList<>();
    private final Map<String, Student> students = new HashMap<>();
    private final String adminId = "ADMIN123";

    private Database() {}

    public static Database getInstance() {
        if (instance == null) instance = new Database();
        return instance;
    }

    public void addStudent(Student s) { students.put(s.getId(), s); }
    public Student getStudent(String id) { return students.get(id); }
    public void logLogin(String info) { loginLogs.add(info); }
    public java.util.List<String> getLogs() { return loginLogs; }

    public String getAdminId() { return adminId; }

    public void exportAnalyticsTxt() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("analytics.txt", true))) {
            writer.println("=== Library Login Analytics ===");
            for (String log : loginLogs) writer.println(log);
            writer.println("Total Logins: " + loginLogs.size());
            writer.println("===============================\n");
            JOptionPane.showMessageDialog(null, "Analytics exported to analytics.txt");
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void exportAnalyticsCsv() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("analytics.csv", true))) {
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
        } catch (IOException e) { e.printStackTrace(); }
    }
}
