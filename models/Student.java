package models;

public class Student {
    private final String id, name, course, yearLevel;

    public Student(String id, String name, String course, String yearLevel) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.yearLevel = yearLevel;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getCourse() { return course; }
    public String getYearLevel() { return yearLevel; }
}
