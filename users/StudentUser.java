package users;

import database.Database;
import models.Student;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StudentUser extends User {
    public StudentUser(String id) { super(id); }

    @Override
    public void login() {
        Database db = Database.getInstance();
        Student s = db.getStudent(id);
        if (s != null) {
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMMM dd yyyy, hh:mm a"));
            String log = "Student: " + s.getName() + " | Course: " + s.getCourse() +
                    " | Year: " + s.getYearLevel() + " | Logged in at " + time + " | Semester: 1st";
            db.logLogin(log);
            JOptionPane.showMessageDialog(null, "Welcome " + s.getName() + "!\nLogged at: " + time);
        } else {
            System.out.println("Student not found!"); // Debugging line
            JOptionPane.showMessageDialog(null, "Student not found!");
        }
    }
}
