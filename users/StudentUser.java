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

            String time = LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            );

            db.logLogin(s.getId(), s.getName(), s.getCourse(), s.getYearLevel(), time);

            JOptionPane.showMessageDialog(null,
                    "Welcome " + s.getName() + "!\nLogged at: " + time);
        } else {
            JOptionPane.showMessageDialog(null, "Student not found!");
        }
    }
}
