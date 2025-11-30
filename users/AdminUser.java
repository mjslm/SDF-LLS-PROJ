package users;

import database.Database;
import gui.AdminDashboardGUI;

import javax.swing.*;

public class AdminUser extends User {
    public AdminUser(String id) { super(id); }

    @Override
    public void login() {
        Database db = Database.getInstance();
        if (id.equals(db.getAdminId())) new AdminDashboardGUI();
        else JOptionPane.showMessageDialog(null, "Invalid Admin ID!");
    }
}
