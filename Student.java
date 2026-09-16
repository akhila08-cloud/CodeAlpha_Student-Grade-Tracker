/*
 * Student.java
 * -------------------------------------------------
 * This class represents a single Student record.
 * It stores the student's ID, name and marks, and
 * provides a method to calculate the letter grade
 * based on the marks.
 * -------------------------------------------------
 */
public class Student {

    // ----- Fields (data of one student) -----
    private String studentId;
    private String studentName;
    private double marks;

    // ----- Constructor -----
    // This runs when we create a new Student object.
    public Student(String studentId, String studentName, double marks) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.marks = marks;
    }

    // ----- Getters (methods to read the data) -----
    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public double getMarks() {
        return marks;
    }

    // ----- Grade calculation method -----
    // Converts numeric marks into a letter grade.
    public String getGrade() {
        if (marks >= 90) {
            return "A";
        } else if (marks >= 80) {
            return "B";
        } else if (marks >= 70) {
            return "C";
        } else if (marks >= 60) {
            return "D";
        } else if (marks >= 50) {
            return "E";
        } else {
            return "F";
        }
    }

    // A student is considered "passed" if marks are 50 or above.
    // (Change this rule here if your internship defines pass/fail differently.)
    public boolean isPassed() {
        return marks >= 50;
    }
}
