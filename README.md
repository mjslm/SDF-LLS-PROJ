# Library Login System (LLS) - Student & Admin Management System

## 📋 Project Overview
A comprehensive Java-based Library Login System that manages student registrations, logins, and provides detailed analytics for administrators. The system includes a user-friendly GUI and a robust MySQL database backend.

## 🎯 Features

### Student Features
- ✅ Student Registration with ID, Name, Course, and Year Level
- ✅ Student Login Authentication
- ✅ Login History Tracking
- ✅ Semester Selection

### Admin Features
- ✅ Admin Login Authentication
- ✅ View All Student Logins
- ✅ **Advanced Analytics & Sorting:**
  - Sort by Department (Course)
  - Sort by Semester
  - Sort by Department & Semester Combined
  - View Total Login Counts
  - Oldest/Newest First Sorting
  - Alphabetical Sorting (A-Z, Z-A)
- ✅ Export Data to CSV and TXT formats
- ✅ Real-time Data Persistence

### Database Features
- ✅ Automatic Analytics Update
- ✅ Department Login Statistics
- ✅ Semester Login Statistics
- ✅ Combined Department & Semester Analytics
- ✅ Persistent Data Storage

## 🗄️ Database Structure

### Tables
1. **students** - Student information
   - student_id (Primary Key)
   - student_name
   - course (department)
   - year_level

2. **login_logs** - Login history
   - log_id (Primary Key, Auto-increment)
   - student_id (Foreign Key)
   - student_name
   - course
   - year_level
   - semester
   - login_datetime

3. **admin_info** - Admin credentials
   - id
   - admin_id

4. **analytics_by_department** - Department analytics (NEW)
   - analytics_id
   - department
   - login_count
   - last_updated

5. **analytics_by_semester** - Semester analytics (NEW)
   - analytics_id
   - semester
   - login_count
   - last_updated

6. **analytics_by_dept_semester** - Combined analytics (NEW)
   - analytics_id
   - department
   - semester
   - login_count
   - last_updated

## 🚀 Getting Started

### Prerequisites
- Java JDK 11 or higher
- MySQL Server 10.4 or MariaDB
- MySQL Connector/J JDBC Driver (included in lib/)

### Installation Steps

1. **Clone the Repository**
   ```bash
   git clone https://github.com/mjslm/SDF-LLS-PROJ.git
   cd SDF-LLS-PROJ
   ```

2. **Create Database**
   ```bash
   mysql -u root -p < library_login_system_database.sql
   ```
   This will create:
   - Database: `library_login_system_database`
   - All required tables
   - Sample data

3. **Configure Database Connection** (if needed)
   Edit `database/DBUtil.java`:
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/library_login_system_database";
   private static final String USER = "root";
   private static final String PASSWORD = "your_password";
   ```

4. **Compile and Run**
   ```bash
   ./run.bat
   ```
   Or manually:
   ```bash
   javac -d . $(find . -name "*.java")
   java Main
   ```

## 📊 Admin Dashboard Usage

### Sorting & Analytics
1. Open Admin Dashboard after logging in with ID: `ADMIN123`
2. Use the Sort dropdown to view:
   - **All Logs** - Complete login history
   - **Newest/Oldest First** - Time-based sorting
   - **A-Z / Z-A** - Alphabetical sorting
   - **By Department** - Total logins per course/department
   - **By Semester** - Total logins per semester
   - **By Department & Semester** - Detailed breakdown

### Example Output
```
========== LOGIN COUNT BY DEPARTMENT ==========
Department: BSIT | Total Logins: 28
Department: BSCrim | Total Logins: 12

========== LOGIN COUNT BY SEMESTER ==========
Semester: 1st Semester | Total Logins: 25
Semester: 2nd Semester | Total Logins: 15
```

## 📈 Data Persistence

All analytics are **automatically saved to the database** when:
- Exporting to CSV
- Exporting to TXT
- Using sorting features (triggered by admin dashboard)

The system uses **INSERT ... ON DUPLICATE KEY UPDATE** to ensure:
- ✅ No duplicate entries
- ✅ Always up-to-date statistics
- ✅ Automatic timestamp tracking

## 📁 Project Structure
```
SDF-LLS-PROJ/
├── Main.java                              # Application entry point
├── run.bat                                # Batch file to compile & run
├── library_login_system_database.sql      # Database schema
├── README.md                              # This file
│
├── database/
│   ├── Database.java                      # Database operations & analytics
│   └── DBUtil.java                        # JDBC connection utility
│
├── gui/
│   ├── MainMenuGUI.java                   # Main menu interface
│   ├── StudentLoginGUI.java               # Student login form
│   ├── StudentRegisterGUI.java            # Student registration form
│   ├── AdminLoginGUI.java                 # Admin login form
│   └── AdminDashboardGUI.java             # Admin dashboard with analytics
│
├── models/
│   └── Student.java                       # Student data model
│
├── users/
│   ├── User.java                          # Base user class
│   ├── StudentUser.java                   # Student user implementation
│   └── AdminUser.java                     # Admin user implementation
│
├── lib/
│   └── (MySQL JDBC Driver)
│
└── exported/
    ├── analytics.csv                      # Exported CSV data
    └── analytics.txt                      # Exported TXT data
```

## 🔐 Test Credentials

### Admin Account
- **ID**: `ADMIN123`
- **Password**: (configured in Database.java)

### Sample Student Accounts
- **ID**: `2023-00194`
- **Name**: Zita Miro M. Valerio
- **Course**: BSIT
- **Year**: 2

## 📊 Key Methods

### Analytics Methods (Database.java)
```java
// Update analytics in database
updateAnalyticsByDepartment()           // Save department stats
updateAnalyticsBySemester()             // Save semester stats
updateAnalyticsByDeptAndSemester()      // Save combined stats
updateAllAnalytics()                     // Update all analytics

// Retrieve analytics for display
getLoginCountByDepartment()             // Get department breakdown
getLoginCountBySemester()               // Get semester breakdown
getLoginCountByDepartmentAndSemester()  // Get combined breakdown
```

## 🔄 Data Flow

```
Student Login 
    ↓
[logLogin() called]
    ↓
[Data saved to login_logs table]
    ↓
[Admin Dashboard displays]
    ↓
[Admin clicks Sort/Export]
    ↓
[updateAllAnalytics() triggered]
    ↓
[Analytics computed and saved to:
 - analytics_by_department
 - analytics_by_semester
 - analytics_by_dept_semester]
    ↓
[Data exported to CSV/TXT]
```

## 🐛 Troubleshooting

### "Unknown database" error
```
✗ Error: Unknown database 'library_login_system_database'
```
**Solution**: Import the SQL file to create the database:
```bash
mysql -u root -p < library_login_system_database.sql
```

### Connection refused error
```
✗ Error: Communications link failure
```
**Solutions**:
1. Verify MySQL is running
2. Check DBUtil.java database credentials
3. Verify localhost:3306 is correct

### Compilation errors
```bash
# Clean and recompile
rm *.class
rm */*.class
javac -d . $(find . -name "*.java")
```

## 📝 Change Log

### v2.0 (Current)
- ✨ Added analytics storage to database
- ✨ New analytics tables for department and semester tracking
- ✨ Automatic data persistence on export
- ✨ Enhanced Admin Dashboard with 8 sorting options
- 🐛 Fixed GUI syntax errors
- 📚 Added comprehensive README

### v1.0
- Initial release with basic login system
- Student registration and login
- Admin dashboard with basic log viewing
- CSV/TXT export functionality

## 👥 Contributors
- **Marchael Shesh** (mjslm)
- **Miro Valerio**

## 📧 Contact & Support
For issues or feature requests, please contact the development team.

## 📄 License
This project is provided as-is for educational purposes.

---

**Last Updated**: December 8, 2025
